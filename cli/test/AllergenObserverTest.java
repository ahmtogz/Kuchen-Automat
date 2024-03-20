

import static kuchen.Kuchentyp.Obstkuchen;
import static org.junit.jupiter.api.Assertions.assertEquals;

import kuchen.Allergen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;



class AllergenObserverTest {
    private Automat automat;
    private Kuchentyp kuchentyp;
    private String herstellerName;
    private HerstellerImp hersteller;
    private BigDecimal preis;
    private Duration haltbarkeit;
    private int naehrwert;
    private String obstsorte;

    @BeforeEach
    public void setUp() {
        kuchentyp = Obstkuchen;
        herstellerName = "Alice";
        hersteller = new HerstellerImp(herstellerName);
        naehrwert = 100;
        haltbarkeit = Duration.ofHours(24);
        preis = new BigDecimal("1.99");
        obstsorte = "Apfel";

        automat = new Automat(50);
        automat.einfuegenHersteller(herstellerName);

        // Wir erstellen die AllergenObserver-Klasse und verknüpfen sie mit dem Automaten
        AllergenObserver allergenObserver = new AllergenObserver(automat);
    }
    @Test
    public void testAllergenObserverUpdateNormal() {
        Set<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);
        allergens.add(Allergen.Sesamsamen);

        AllergenObserver allergenObserver = new AllergenObserver(automat);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergens,obstsorte);
        // Wenn der Automat aktualisiert wird, sollte die update-Methode von AllergenObserver aufgerufen werden
        // und die Meldung "Allergens an der Automat wurden geändert" sollte ausgegeben werden

        allergenObserver.update();
        assertEquals("Allergens an der Automat wurden geändert", outContent.toString().trim());

    }
    @Test
    public void testAllergenObserverMitAktualisiertenAllergene() {
        Set<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);
        allergens.add(Allergen.Sesamsamen);

        AllergenObserver allergenObserver = new AllergenObserver(automat);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergens,obstsorte);

        // Wenn der Automat aktualisiert wird, sollte die update-Methode von AllergenObserver aufgerufen werden
        // und die Meldung "Allergens an der Automat wurden geändert" sollte ausgegeben werden

        allergenObserver.update();
        // Jetzt aktualisieren wir die Allergene mit einem anderen Set

        Set<Allergen> newAllergens = new HashSet<>();
        newAllergens.add(Allergen.Gluten);
        newAllergens.add(Allergen.Sesamsamen);
        newAllergens.add(Allergen.Erdnuss);
        // Aktualisieren wir den Automaten mit den aktualisierten Allergene

        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,newAllergens,obstsorte);
        // Führen wir die Aktualisierung erneut durch

        allergenObserver.update();
        // Die Meldung "Allergens an der Automat wurden geändert" sollte ausgegeben werden
        // da ein neues Allergen hinzugefügt wurde

    }
    @Test
    public void testAllergenObserverUpdateWhenAllergenRemoved() {
        Set<Allergen> allergens = new HashSet<>();
        allergens.add(Allergen.Gluten);
        allergens.add(Allergen.Sesamsamen);

        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergens,obstsorte);

        automat.notifyObservers();

        automat.entfernenKuchen(1);

        automat.notifyObservers();

    }

    @Test
    public void testAllergenObserverNoUpdateWhenNoAllergenAdded() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Wir erstellen die AllergenObserver-Klasse, jedoch sollte am Anfang keine Aktualisierung erfolgen,
        // da keine Allergene hinzugefügt wurden
        AllergenObserver allergenObserver = new AllergenObserver(automat);

        // Wir aktualisieren die Automat-Klasse nicht, da keine Allergene hinzugefügt wurden
        automat.notifyObservers();

        // Da keine Allergene hinzugefügt wurden, sollte die update-Methode nicht aufgerufen werden
        // und es sollte keine Meldung ausgegeben werden
        assertEquals("", outContent.toString().trim());
    }


}
