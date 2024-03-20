import kuchen.Allergen;
import kuchen.Kuchentyp;
import kuchen.Mode;
import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import static kuchen.Kuchentyp.*;


public class Automat implements Serializable, Observable {
    @Serial
    private static final long serialVersionUID = 6L;
    private int automatCapacity;
    private List<KuchenImp> kuchenList;
    private List<HerstellerImp> herstellerList;

    private Mode currentMode;


    public Automat(int automatCapacity) {
        this.automatCapacity = automatCapacity;
        this.kuchenList = new ArrayList<>(automatCapacity);
        this.herstellerList = new ArrayList<>();
    }

    // FUER PERSISTIERUN-JBP //
    public Automat() {}

    public void setKuchenList(List<KuchenImp> kuchenList) {
        this.kuchenList = kuchenList;
    }

    public void setHerstellerList(List<HerstellerImp> herstellerList) {
        this.herstellerList = herstellerList;
    }
    // -------------------- //

    public synchronized int getAutomatCapacity() {
        return automatCapacity;
    }


    public synchronized boolean einfuegenHersteller(String name) {
        String herstellerName = name;
        for (HerstellerImp value : herstellerList) {
            if (value.getName().equalsIgnoreCase(herstellerName)) {
                return false;
            }
        }
        herstellerList.add(new HerstellerImp(herstellerName));
        return true;
    }

    public synchronized List<HerstellerImp> auflistenHersteller() {
        return new ArrayList<>(herstellerList);
    }

    public synchronized boolean entfernenHersteller(String name) {
        HerstellerImp herstellerToRemove = null;

        for (HerstellerImp hersteller : herstellerList) {
            if (hersteller.getName().equalsIgnoreCase(name)) {
                // Prüfen, ob der Hersteller in der Kuchenliste enthalten ist
                boolean isUsedInKuchenList = kuchenList.stream()
                        .anyMatch(kuchen -> kuchen.getHersteller().getName().equals(hersteller.getName()));

                if (isUsedInKuchenList) {
                    return false;
                }

                herstellerToRemove = hersteller;
                break;
            }
        }

        if (herstellerToRemove != null) {
            herstellerList.remove(herstellerToRemove);
            return true;
        } else {
            return false;
        }
    }

    public  Map<String, Integer> getHerstellerMitKuchenAnzahl() {
        Map<String, Integer> herstellerMitKuchenAnzahl = new HashMap<>();

        for (KuchenImp kuchen : kuchenList) {
            HerstellerImp hersteller = (HerstellerImp) kuchen.getHersteller();
            String herstellerName = hersteller.getName();
            herstellerMitKuchenAnzahl.put(herstellerName, herstellerMitKuchenAnzahl.getOrDefault(herstellerName, 0) + 1);
        }

        return herstellerMitKuchenAnzahl;
    }

    public synchronized boolean einfuegenKuchen(Kuchentyp kuchentyp, HerstellerImp hersteller, BigDecimal price, int naehrwert, Duration haltbarkeit, Collection<Allergen> allergene, String... belag) {
        if (kuchenList.size() < automatCapacity) {
            boolean herstellerExists = false;

            for (HerstellerImp h : herstellerList) {
                if (hersteller.getName().equalsIgnoreCase(h.getName())) {
                    herstellerExists = true;
                    break;
                }
            }

            if (herstellerExists) {
                int fachnummer = generateUniqueFachnummer();

                KuchenImp kuchen = null;

                if (kuchentyp == Kremkuchen) {
                    kuchen = new KremkuchenImp(Kremkuchen, hersteller, price,  naehrwert, haltbarkeit, allergene, new Date(), fachnummer, belag[0]);
                } else if (kuchentyp == Obstkuchen) {
                    kuchen = new ObstkuchenImp(Obstkuchen, hersteller, price, naehrwert, haltbarkeit, allergene, new Date(), fachnummer, belag[0]);
                } else if (kuchentyp == Obsttorte) {
                    kuchen = new ObsttorteImp(Obsttorte, hersteller, price, naehrwert, haltbarkeit, allergene, new Date(), fachnummer, belag[0], belag[1]);
                }

                kuchenList.add(kuchen);
                return true;
            } else {
                throw new IllegalArgumentException("Hersteller " + hersteller.getName() + " existiert nicht in der HerstellerList.");
            }
        } else {
            throw new IndexOutOfBoundsException("Kuchenlist is out of capacity.");
        }
    }

    private synchronized int generateUniqueFachnummer() {
        int fachnummer = 1;
        while (isFachnummerTaken(fachnummer)) {
            fachnummer++;
        }
        return fachnummer;
    }

    private synchronized boolean isFachnummerTaken(int fachnummer) {
        for (KuchenImp k : kuchenList) {
            if (k.getFachnummer() == fachnummer) {
                return true;
            }
        }
        return false;
    }

    public synchronized List<KuchenImp> auflistenKuchen() {
        return new ArrayList<>(kuchenList);
    }

    public synchronized boolean aendern(int fach) throws IndexOutOfBoundsException {
        for (KuchenImp k : kuchenList) {
            if (k.getFachnummer() == fach) {
                k.setInspektionsdatum(new Date());
                return true;
            }
        }
        throw new IndexOutOfBoundsException("Kuchen mit Fachnummer (" + fach + ") ist nicht verfügbar");
    }

    public synchronized void entfernenKuchen(int fach) throws IllegalArgumentException {
        KuchenImp kuchenToRemove = null;

        for (KuchenImp k : kuchenList) {
            if (k.getFachnummer() == fach) {
                kuchenToRemove = k;
                break;
            }
        }

        if (kuchenToRemove != null) {
            kuchenList.remove(kuchenToRemove);
        } else {
            throw new IllegalArgumentException("Kuchen mit Fachnummer " + fach + " ist nicht im Automat vorhanden.");
        }
    }

    public  String getKuchenNachTyp(Kuchentyp kuchentyp) {
        StringBuilder ergebnis = new StringBuilder();

        for (KuchenImp kuchen : kuchenList) {
            boolean isTypeMatch = kuchentyp == null || kuchen.getKuchentyp() == kuchentyp;

            if (isTypeMatch) {
                ergebnis.append(helpMethodGetKuchenNachTyp(kuchen));
            }
        }
        return ergebnis.toString();
    }

    private String helpMethodGetKuchenNachTyp(KuchenImp kuchen) {
        String verbleibendeHaltbarkeit = verbleibendeHaltbarkeit(kuchen);

        return "Fach " + kuchen.getFachnummer() + ": " + kuchen.getKuchentyp() +
                ", Inspektionsdatum: " + kuchen.getInspektionsdatum() +
                ", Verbleibende Haltbarkeit: " + verbleibendeHaltbarkeit + System.lineSeparator();

    }

    public String verbleibendeHaltbarkeit(KuchenImp kuchen) {
        long remainingDurationMillis = kuchen.getHaltbarkeit().toMillis();
        long verbleibendeStunden = remainingDurationMillis / (60 * 60 * 1000);
        long verbleibendeMinuten = (remainingDurationMillis % (60 * 60 * 1000)) / (60 * 1000);

        return verbleibendeStunden + " Stunden " + verbleibendeMinuten + " Minuten";

    }

    public  Set<Allergen> getAlleVorhandenenAllergene() {
        Set<Allergen> alleVorhandenenAllergene = new HashSet<>();

        for (KuchenImp kuchen : kuchenList) {
            if (kuchen != null && kuchen.getAllergene() != null) {
                alleVorhandenenAllergene.addAll(kuchen.getAllergene());
            }
        }

        return alleVorhandenenAllergene;
    }
    public  Set<Allergen> getAlleNichtVorhandenenAllergene() {
        Set<Allergen> alleVorhandenenAllergene = getAlleVorhandenenAllergene();
        Set<Allergen> alleMoeglichenAllergene = new HashSet<>(Arrays.asList(Allergen.values()));

        alleMoeglichenAllergene.removeAll(alleVorhandenenAllergene);

        return alleMoeglichenAllergene;

    }
    public  void ladeVonAnderemAutomat(Automat andererAutomat) {
        this.kuchenList = andererAutomat.kuchenList;
        this.herstellerList = andererAutomat.herstellerList;
    }

    private final List<Observer> observerList = new LinkedList<>();

    @Override
    public void addObserver(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observerList)
            observer.update();
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }
}


