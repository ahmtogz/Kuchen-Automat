import java.beans.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class JBP {

    public JBP() {
    }

    public void save(Automat automat){
        ArrayList<Automat> items = new ArrayList<>();
        items.add(automat);
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("itemJbp.xml")));) {

            encoder.setPersistenceDelegate(Automat.class, new CustomPersistenceDelegate());
            //encoder.setPersistenceDelegate(Automat.class, new DefaultPersistenceDelegate(new String[]{ "automatCapacity","kuchenList","herstellerList"}));
            encoder.setPersistenceDelegate(HerstellerImp.class, new DefaultPersistenceDelegate(new String[]{ "name" }));
            encoder.setPersistenceDelegate(KuchenImp.class, new DefaultPersistenceDelegate(new String[]{"kuchentyp", "hersteller", "preis", "naehrwert", "haltbarkeit", "allergene", "inspektionsdatum", "fachnummer"}));
            encoder.setPersistenceDelegate(KremkuchenImp.class, new DefaultPersistenceDelegate(new String[]{"kuchentyp", "hersteller", "preis", "naehrwert", "haltbarkeit", "allergene", "inspektionsdatum", "fachnummer","kremsorte"}));
            encoder.setPersistenceDelegate(ObstkuchenImp.class, new DefaultPersistenceDelegate(new String[]{"kuchentyp", "hersteller", "preis", "naehrwert", "haltbarkeit", "allergene", "inspektionsdatum", "fachnummer","obstsorte"}));
            encoder.setPersistenceDelegate(ObsttorteImp.class, new DefaultPersistenceDelegate(new String[]{"kuchentyp", "hersteller", "preis", "naehrwert", "haltbarkeit", "allergene", "inspektionsdatum", "fachnummer","obstsorte","kremsorte"}));

            encoder.setPersistenceDelegate(Duration.class, new DefaultPersistenceDelegate() {
                protected Expression instantiate(Object oldInstance, Encoder out) {
                    Duration duration = (Duration) oldInstance;
                    return new Expression(oldInstance, oldInstance.getClass(), "parse", new Object[] {
                            duration.toString()
                    });
                }protected boolean mutatesTo(Object oldInstance, Object newInstance) { return oldInstance.equals(newInstance); }
            });

            encoder.setPersistenceDelegate(BigDecimal.class, new DefaultPersistenceDelegate() {
                protected Expression instantiate(Object oldInstance, Encoder out) {
                    BigDecimal bigDecimal = (BigDecimal) oldInstance;
                    return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[] {
                            bigDecimal.toString()
                    });
                }protected boolean mutatesTo(Object oldInstance, Object newInstance) { return oldInstance.equals(newInstance); }
            });

            encoder.writeObject(items);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static class CustomPersistenceDelegate extends PersistenceDelegate {
        @Override
        protected Expression instantiate(Object oldInstance, Encoder out) {
            Automat oldAutomat = (Automat) oldInstance;
            return new Expression(oldInstance, oldInstance.getClass(), "new", new Object[]{});
        }

        @Override
        protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
            super.initialize(type, oldInstance, newInstance, out);
            Automat automat = (Automat) oldInstance;
            try {
                out.writeStatement(new Statement(oldInstance, "setKuchenList", new Object[]{automat.auflistenKuchen()}));
                out.writeStatement(new Statement(oldInstance, "setHerstellerList", new Object[]{automat.auflistenHersteller()}));
            } catch (Exception e) {
                out.getExceptionListener().exceptionThrown(e);
            }
        }
    }

    public Automat load() {
        Automat automat = null;
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("itemJbp.xml")));) {
            List<Automat> loadedItems = (List<Automat>) decoder.readObject();
            if (loadedItems != null) {
                for (Automat i : loadedItems) {
                    automat = i;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return automat;
    }

}