
import static kuchen.Kuchentyp.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import kuchen.Allergen;
import kuchen.Kuchentyp;
import kuchen.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;


public class AutomatTest {
    private Automat automat;
    private Kuchentyp kuchentyp;
    private String herstellerName;
    private HerstellerImp hersteller;
    private BigDecimal preis;
    private Duration haltbarkeit;
    private int naehrwert;
    private String obstsorte;
    private Collection<Allergen> allergene;
    private // Create a mock Observer
    Observer mockObserver;



    @BeforeEach
    public void setUp() {
        kuchentyp = Obstkuchen;
        herstellerName = "Alice";
        hersteller = new HerstellerImp(herstellerName);
        allergene = Collections.singleton(Allergen.Erdnuss);
        naehrwert = 100;
        haltbarkeit = Duration.ofHours(24);
        preis = new BigDecimal("1.99");
        obstsorte = "Apfel";


        automat = new Automat(100);
        automat.einfuegenHersteller(herstellerName);

        mockObserver = mock(Observer.class);

    }


    @Test
    public void testSetKuchenList() {
        List<KuchenImp> kuchenList = new ArrayList<>();
        kuchenList.add(new KuchenImp(Obsttorte, new HerstellerImp("TestHersteller"), BigDecimal.valueOf(10), 200, Duration.ofHours(24), Collections.singleton(Allergen.Gluten), new Date(), 1));

        automat.setKuchenList(kuchenList);

        assertEquals(kuchenList, automat.auflistenKuchen());
    }

    @Test
    public void testSetHerstellerList() {
        List<HerstellerImp> herstellerList = new ArrayList<>();
        herstellerList.add(new HerstellerImp("TestHersteller"));

        automat.setHerstellerList(herstellerList);

        assertEquals(herstellerList, automat.auflistenHersteller());
    }

    @Test
    public void testGetCurrentModeAndSetCurrentMode() {
        Mode expectedMode = Mode.Einfuegen;
        automat.setCurrentMode(expectedMode);
        assertEquals(expectedMode, automat.getCurrentMode());
    }

    @Test
    public void testEinfuegenHersteller() {
        assertTrue(automat.einfuegenHersteller("Bob"));
    }
    @Test
    public void testAuflistenHerstellerAfterRemoval() {
        automat.einfuegenHersteller("Bob");
        automat.entfernenHersteller("Bob");
        List<HerstellerImp> remainingHersteller = automat.auflistenHersteller();
        assertEquals(1, remainingHersteller.size()); // Only the initially added Hersteller ("Alice") should remain
    }

    @Test
    public void testEntfernenHersteller() {
        automat.einfuegenHersteller("Bob");
        assertTrue(automat.entfernenHersteller("Bob"));
    }

    @Test
    public void testEntfernenHerstellerInUse() {
        automat.einfuegenKuchen(kuchentyp, hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte);
        assertFalse(automat.entfernenHersteller(herstellerName));
    }
    @Test
    public void testEntfernenKuchenWithInvalidFachnummer() {
        automat.einfuegenKuchen(kuchentyp, hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte);

        int invalidFachnummer = 100;

        assertThrows(IllegalArgumentException.class, () -> {
            automat.entfernenKuchen(invalidFachnummer);
        });
    }
    @Test
    public void testEntfernenHerstellerWithNullHersteller() {
        automat.einfuegenHersteller(herstellerName);

        String invalidHerstellerName = "InvalidHersteller";
        assertFalse(automat.entfernenHersteller(invalidHerstellerName));

        assertEquals(1, automat.auflistenHersteller().size());
    }



    @Test

    public void testAuflistenHerstellerAfterRemovingAllKuchen() {
        automat.einfuegenKuchen(Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");
        automat.entfernenKuchen(1);
        List<HerstellerImp> remainingHersteller = automat.auflistenHersteller();
        assertEquals(1, remainingHersteller.size()); // The initially added Hersteller should remain
    }

    @Test
    public void testGetHerstellerMitKuchenAnzahl() {
        HerstellerImp hersteller2 = new HerstellerImp("Bob");

        automat.einfuegenHersteller(herstellerName);
        automat.einfuegenHersteller(hersteller2.getName());


        automat.einfuegenKuchen(Kremkuchen, hersteller, new BigDecimal("2.50"), 120, Duration.ofHours(48), allergene, "Schokolade");
        automat.einfuegenKuchen(Obstkuchen, hersteller, new BigDecimal("3.00"), 150, Duration.ofHours(24), allergene, "Apfel");
        automat.einfuegenKuchen(Obsttorte, hersteller2, new BigDecimal("4.50"), 200, Duration.ofHours(72), allergene, "Erdbeere", "Sahne");

        // Get the Hersteller with the count of Kuchen
        Map<String, Integer> result = automat.getHerstellerMitKuchenAnzahl();

        // Check if the counts are correct
        assertEquals(2, result.size());
        assertEquals(2, result.get(hersteller.getName())); // Alice has 2 Kuchen
        assertEquals(1, result.get(hersteller2.getName())); // Bob has 1 Kuchen
    }


    @Test
    public void testGetKuchenNachTyp() {
        automat.einfuegenKuchen(Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");
        automat.einfuegenKuchen(Obstkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Apfel");
        String result = automat.getKuchenNachTyp(Kremkuchen);
        assertTrue(result.contains("Fach 1: Kremkuchen"));
    }

    @Test
    public void testGetKuchenNachNullTyp() {
        automat.einfuegenKuchen(Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");
        automat.einfuegenKuchen(Obstkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Apfel");
        String result = automat.getKuchenNachTyp(null);
        assertTrue(result.contains("Fach 1: Kremkuchen") && result.contains("Fach 2: Obstkuchen"));
    }


    //wenn Kapazitaet voll, keine weiterer einfügbar
    @Test
    public void testEinfuegenCapacityVoll() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            for (int i = 0; i < automat.getAutomatCapacity(); i++) {
                automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
            }
            automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        });

    }

    @Test
    public void testEinfuegenNormal() {
        assertTrue(automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte));
    }

    @Test
    public void testEinfuegenKuchenWithInvalidHersteller() {
        HerstellerImp invalidHersteller = new HerstellerImp("InvalidHersteller");
        assertThrows(IllegalArgumentException.class, () -> {
            automat.einfuegenKuchen(kuchentyp, invalidHersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte);
        });
    }


    //wenn 1 Kuchen rein, dann Liste Länge 1
    @Test
    public void testAuflistenWithNonEmptyAutomat() {
        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        List<KuchenImp> kuchenList = automat.auflistenKuchen();
        assertNotNull(kuchenList);
        assertEquals(1, kuchenList.size());
    }

    //wenn 2 Kuchen rein, dann ein Kuchen entfernt, is Liste Länge 1
    @Test
    public void testAuflistenMitEinfuegenUndEntfernen() {
        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        automat.entfernenKuchen(1);
        List<KuchenImp> kuchenList = automat.auflistenKuchen();
        assertEquals(1, kuchenList.size());
    }

    //Kuchen eingefügt, wenn Methode mit Fachnummer aufgerufen, dann am Kuchen Inspektionsdatum jetzt
    @Test
    public void testAendern() {
        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        automat.aendern(1);
        KuchenImp updatedKuchen = automat.auflistenKuchen().get(0);
        assertNotNull(updatedKuchen.getInspektionsdatum());

    }

    @Test
    public void testAendernNotInitialized() {
        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        assertThrows(IndexOutOfBoundsException.class,()->{
            automat.aendern(2);
        });

    }

    @Test
    public void testEntfernenKuchen() {

        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        List<KuchenImp> kuchenList = automat.auflistenKuchen();
        automat.entfernenKuchen(kuchenList.get(0).getFachnummer());
        assertEquals(0, automat.auflistenKuchen().size());
    }
    @Test
    public void testGetAlleVorhandenenAllergene() {
        automat.einfuegenKuchen(Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");
        automat.einfuegenKuchen(Obstkuchen, hersteller, preis, naehrwert, haltbarkeit, Collections.singleton(Allergen.Gluten), "Apfel");
        Set<Allergen> result = automat.getAlleVorhandenenAllergene();
        assertTrue(result.contains(Allergen.Erdnuss) && result.contains(Allergen.Gluten));
    }


    @Test
    public void testGetAlleNichtVorhandenenAllergene() {
        automat.einfuegenKuchen(Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");

        Set<Allergen> result = automat.getAlleNichtVorhandenenAllergene();

        Set<Allergen> expected = new HashSet<>(Arrays.asList(Allergen.Haselnuss, Allergen.Gluten, Allergen.Sesamsamen));

        assertEquals(expected, result);
    }


    @Test
    public void testLadeVonAnderemAutomat() {
        Automat secondAutomat = new Automat(100);
        secondAutomat.einfuegenHersteller(hersteller.getName());
        secondAutomat.einfuegenHersteller("Bob");
        secondAutomat.einfuegenKuchen(Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");

        automat.ladeVonAnderemAutomat(secondAutomat);
        List<HerstellerImp> remainingHersteller = automat.auflistenHersteller();
        List<KuchenImp> kuchenList = automat.auflistenKuchen();

        assertEquals(2, remainingHersteller.size()); // "Alice" and "Bob" should be present
        assertEquals(1, kuchenList.size()); // One Kuchen from the secondAutomat should be loaded
    }

    @Test
    public void testAutomatEncapsulationT() {
        automat.einfuegenKuchen(Kuchentyp.Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");
        automat.einfuegenKuchen(Kuchentyp.Obstkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Apfel");

        List<KuchenImp> kuchenList = automat.auflistenKuchen();
        kuchenList.clear();
        List<KuchenImp> kuchenList2 = automat.auflistenKuchen();
        assertFalse(kuchenList2.isEmpty());

    }

    @Test
    public void testNotifyObserversWithMockito() {

        // Add the mock Observer to the Automat
        automat.addObserver(mockObserver);

        // Call notifyObservers and verify that the update method is called on the mock Observer
        automat.notifyObservers();
        verify(mockObserver, times(1)).update();
    }


    @Test
    public void testDeleteObserverWithMockito() {

        // Add the mock Observer to the Automat
        automat.addObserver(mockObserver);

        // Call deleteObserver and verify that the observer is removed
        automat.deleteObserver(mockObserver);
        verify(mockObserver, times(0)).update(); // update method should not be called
    }





}
