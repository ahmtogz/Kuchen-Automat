import static org.junit.jupiter.api.Assertions.*;

import kuchen.Allergen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


class ObstkuchenImpTest {
    private ObstkuchenImp obstKuchen;
    private Collection<kuchen.Allergen> allergene;
    private HerstellerImp hersteller;
    private Kuchentyp kuchentyp;


    @BeforeEach
    void setUp() {
        kuchentyp = Kuchentyp.Obstkuchen;
        String name = "Aldi";
        hersteller = new HerstellerImp(name);
        allergene = Collections.singleton(Allergen.Erdnuss);
        int naehrwert = 100;
        Duration duration = Duration.ofHours(24);
        BigDecimal price = new BigDecimal("1.99");
        Date inspektionsDatum = new Date();
        int fachnummer = 1;
        String obstsorte = "Apfel";

        obstKuchen = new ObstkuchenImp(kuchentyp, hersteller, price, naehrwert, duration, allergene, inspektionsDatum, fachnummer,obstsorte);
    }
    @Test
    void getObstsorte() {
        String obstsorteActual = obstKuchen.getObstsorte();
        Assertions.assertEquals("Apfel", obstsorteActual);
    }


}