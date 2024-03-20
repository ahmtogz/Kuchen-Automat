import kuchen.Allergen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class KuchenImpTest {

    private KuchenImp kuchen;

    @BeforeEach
    public void setUp() {
        Hersteller hersteller = new HerstellerImp("TestHersteller");
        Collection<Allergen> allergene = Arrays.asList(Allergen.Gluten, Allergen.Sesamsamen);
        Duration haltbarkeit = Duration.ofHours(24);
        Date inspektionsdatum = new Date();

        kuchen = new KuchenImp(
                Kuchentyp.Obsttorte,
                (HerstellerImp) hersteller,
                BigDecimal.valueOf(15.99),
                250,
                haltbarkeit,
                allergene,
                inspektionsdatum,
                2
        );
    }
    @Test
    public void testGetHersteller() {
        Hersteller hersteller = kuchen.getHersteller();
        assertNotNull(hersteller);
        assertEquals("TestHersteller", hersteller.getName());
    }
    @Test
    public void testGetKuchentyp() {
        assertEquals(Kuchentyp.Obsttorte, kuchen.getKuchentyp());
    }
    @Test
    public void testGetPreis() {
        assertEquals(BigDecimal.valueOf(15.99), kuchen.getPreis());
    }
    @Test
    public void testGetAllergene() {
        assertTrue(kuchen.getAllergene().contains(Allergen.Gluten));
        assertTrue(kuchen.getAllergene().contains(Allergen.Sesamsamen));
        assertFalse(kuchen.getAllergene().contains(Allergen.Erdnuss));
    }
    @Test
    public void testSetAndGetInspektionsdatum() {
        Date newInspektionsdatum = new Date();
        kuchen.setInspektionsdatum(newInspektionsdatum);
        assertEquals(newInspektionsdatum, kuchen.getInspektionsdatum());
    }
    @Test
    public void testGetNaehrwert() {
        assertEquals(250, kuchen.getNaehrwert());
    }

    @Test
    public void testGetHaltbarkeit() {
        Duration expectedDuration = Duration.ofHours(24);
        Duration actualDuration = kuchen.getHaltbarkeit();
        long differenceInSeconds = Math.abs(expectedDuration.getSeconds() - actualDuration.getSeconds());

        // Allow a difference of up to 1 second
        assertTrue(differenceInSeconds <= 1, "Expected and actual durations differ by more than 1 second");
    }
    @Test
    public void testGetFachnummer() {
        assertEquals(2, kuchen.getFachnummer());
    }
    @Test
    public void testToString() {
        String expectedString = "KuchenClass { kuchentyp=Obsttorte, hersteller=TestHersteller, preis=15.99, naehrwert=250, haltbarkeit=PT24H, allergens=[Gluten, Sesamsamen], inspektionsdatum=" + kuchen.getInspektionsdatum() + ", fachnummer=2 }";
        assertEquals(expectedString, kuchen.toString());
    }
}