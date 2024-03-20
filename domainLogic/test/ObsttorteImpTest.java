import kuchen.Allergen;
import kuchen.Kuchentyp;
import kuchen.Obsttorte;
import verwaltung.Hersteller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;


public class ObsttorteImpTest {

    private Obsttorte obsttorte;


    @BeforeEach
    public void setUp() {
        Hersteller hersteller = new HerstellerImp("TestHersteller");
        obsttorte = new ObsttorteImp(
                Kuchentyp.Obsttorte,
                (HerstellerImp) hersteller,
                BigDecimal.valueOf(15.99),
                250,
                Duration.ofHours(24),
                Arrays.asList(Allergen.Gluten, Allergen.Erdnuss),
                new Date(),
                2,
                "Erdbeere",
                "Vanille"
        );
    }


    @Test
    public void testGetKremsorte() {
        assertEquals("Vanille", obsttorte.getKremsorte());
    }


    @Test
    public void testGetObstsorte() {
        assertEquals("Erdbeere", obsttorte.getObstsorte());
    }
}