import kuchen.Allergen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;


class JOSTest {
    private Kuchentyp kuchentyp;
    private String herstellerName;
    private HerstellerImp hersteller;
    private BigDecimal preis;
    private Duration haltbarkeit;
    private int naehrwert;
    private String obstsorte;
    private Collection<Allergen> allergene;
    private Automat automat;
    private JOS jos;

    @BeforeEach
    public void setUp() {

        kuchentyp = Kuchentyp.Obstkuchen;
        herstellerName = "Alice";
        hersteller = new HerstellerImp(herstellerName);
        allergene = Collections.singleton(Allergen.Erdnuss);
        naehrwert = 100;
        haltbarkeit = Duration.ofHours(24);
        preis = new BigDecimal("1.99");
        obstsorte = "Apfel";
        jos = new JOS();
        automat = new Automat(100);
    }


    @Test
    public void testSaveUndLoadEmptyAutomat() {
        Automat emptyAutomat = new Automat(100);

        jos.save(emptyAutomat);
        Automat loadedAutomat = jos.load();

        Assertions.assertNotNull(loadedAutomat);
        assertEquals(0, loadedAutomat.auflistenKuchen().size());
    }
    @Test
    public void testSaveUndLoadMitJOS() {
        automat.einfuegenHersteller("Alice");
        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);

        jos.save(automat);

        Automat loadedAutomat = jos.load();

        Assertions.assertNotNull(loadedAutomat);
        assertEquals(1, loadedAutomat.auflistenKuchen().size());

        KuchenImp loadedKuchen = loadedAutomat.auflistenKuchen().get(0);
        assertEquals(kuchentyp, loadedKuchen.getKuchentyp());
        assertEquals(herstellerName, loadedKuchen.getHersteller().getName());
        assertEquals(preis, loadedKuchen.getPreis());
        assertEquals(naehrwert, loadedKuchen.getNaehrwert());
        assertEquals(allergene, loadedKuchen.getAllergene());
        Assertions.assertTrue(loadedKuchen instanceof ObstkuchenImp);
        assertEquals(obstsorte, ((ObstkuchenImp) loadedKuchen).getObstsorte());
    }
    @Test
    public void testSaveUndLoadWithMultipleKuchenJOS() {
        automat.einfuegenHersteller("Alice");
        automat.einfuegenKuchen(kuchentyp, hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte);
        automat.einfuegenKuchen(Kuchentyp.Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");

        jos.save(automat);

        Automat loadedAutomat = jos.load();

        assertNotNull(loadedAutomat);
        assertEquals(2, loadedAutomat.auflistenKuchen().size());
    }

    @Test
    public void testSerializeJOSMitMockSpy() throws IOException {
        ObjectOutputStream objectOutputStream = spy(ObjectOutputStream.class);
        Automat mockAutomat = mock(Automat.class);

        jos.serialize(objectOutputStream, mockAutomat);

        verify(objectOutputStream).writeObject(any());

    }

    @Test
    public void testDeserializeJOSMitSpy() throws Exception {

        ObjectInputStream objectInputStream = spy(ObjectInputStream.class);
        jos.deserialize(objectInputStream);

        verify(objectInputStream).readObject();
    }
}