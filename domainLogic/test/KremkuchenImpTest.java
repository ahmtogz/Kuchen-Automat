import kuchen.Allergen;
import kuchen.Kremkuchen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class KremkuchenImpTest {
    private Kremkuchen kremkuchen;


    @BeforeEach
    public void setUp() {
        Hersteller hersteller = new HerstellerImp("TestHersteller");
        kremkuchen = new KremkuchenImp(
                Kuchentyp.Kremkuchen,
                (HerstellerImp) hersteller,
                BigDecimal.valueOf(10.99),
                200,
                Duration.ofHours(24),
                Arrays.asList(Allergen.Gluten),
                new Date(),
                1,
                "Schokolade"
        );
    }


    @Test
    public void testGetKremsorte() {
        assertEquals("Schokolade", kremkuchen.getKremsorte());
    }
}