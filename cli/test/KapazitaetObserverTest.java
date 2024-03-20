

import static kuchen.Kuchentyp.Obstkuchen;
import static org.junit.jupiter.api.Assertions.*;

import kuchen.Allergen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class KapazitaetObserverTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private Automat automat;
    private Kuchentyp kuchentyp;
    private String herstellerName;
    private HerstellerImp hersteller;
    private BigDecimal preis;
    private Duration haltbarkeit;
    private int naehrwert;
    private Collection<Allergen> allergene;;
    private String obstsorte;

    @BeforeEach
    public void setUp() {
        kuchentyp = Obstkuchen;
        herstellerName = "Alice";
        hersteller = new HerstellerImp(herstellerName);
        naehrwert = 100;
        haltbarkeit = Duration.ofHours(24);
        preis = new BigDecimal("1.99");
        allergene = Collections.singleton(Allergen.Erdnuss);
        obstsorte = "Apfel";

        System.setOut(new PrintStream(outputStreamCaptor));
        automat = new Automat(100);
        automat.einfuegenHersteller(herstellerName);

        KapazitaetObserver kapazitaetObserver = new KapazitaetObserver(automat);

    }

    /**
     * Testet die update-Methode, wenn die Auslastung unter 90 Prozent liegt.
     */
    @Test
    public void testAuslastungUnter90Prozent() {
        for (int i = 0; i < 50; i++) {
            automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        }
        KapazitaetObserver kapazitaetObserver = new KapazitaetObserver(automat);
        kapazitaetObserver.update();

        assertEquals("", outputStreamCaptor.toString().trim());
    }

    /**
     * Testet die update-Methode, wenn die Auslastung über 90 Prozent liegt.
     */
    @Test
    public void testAuslastungUeber90Prozent() {
        for (int i = 0; i < 95; i++) {
            automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        }
        automat.notifyObservers();

        assertEquals("Achtung: Die Kapazität des Automaten liegt bei über 90 %!", outputStreamCaptor.toString().trim());
    }

    /**
     * Testet die update-Methode, wenn die Auslastung genau 90 Prozent beträgt.
     */
    @Test
    public void testAuslastungGleich90Prozent() {
        for (int i = 0; i < 90; i++) {
            automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        }
        automat.notifyObservers();

        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testEmptyAutomat() {

        automat.notifyObservers();

        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testFullAutomat() {
        for (int i = 0; i < 100; i++) {
            automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        }
        automat.notifyObservers();
        assertEquals("Achtung: Die Kapazität des Automaten liegt bei über 90 %!", outputStreamCaptor.toString().trim());
    }
    /**
     * Testet die update-Methode, wenn die Auslastung unter 90 Prozent liegt, nachdem die Schwelle überschritten wurde.
     */
    @Test
    public void testAuslastungUnter90ProzentNachSchwelleUeberschritten() {
        for (int i = 0; i < 100; i++) {
            automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);
        }
        for (int i = 1; i < 31; i++) {
            automat.entfernenKuchen(i);
        }
        automat.notifyObservers();

        assertEquals("", outputStreamCaptor.toString().trim());
    }
}


