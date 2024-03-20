import kuchen.Allergen;
import kuchen.Kuchentyp;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;

public class MainJBP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final int AUTOMAT_KAPAZITAET = 10;
        Automat automat = new Automat(AUTOMAT_KAPAZITAET);
        JBP jbp = new JBP();


        automat.einfuegenHersteller("Aldi");
        automat.einfuegenKuchen(Kuchentyp.Obsttorte,new HerstellerImp("Aldi"),BigDecimal.valueOf(3.0),350,Duration.ofHours(24),Collections.singleton(Allergen.Haselnuss),"Apfel","Vanilie");


        jbp.save(automat);

        automat = jbp.load();
        System.out.println(automat.auflistenKuchen());

    }
}
