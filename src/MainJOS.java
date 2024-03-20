import kuchen.Allergen;
import kuchen.Kuchentyp;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class MainJOS {

    public static void main(String[] args) {
        final int AUTOMAT_KAPAZITAET = 100;
        Automat automat = new Automat(AUTOMAT_KAPAZITAET);
        JOS jos = new JOS();

        automat.einfuegenHersteller("Aldi");
        automat.einfuegenHersteller("Kaufland");
        automat.einfuegenKuchen(Kuchentyp.Obstkuchen,new HerstellerImp("Aldi"),BigDecimal.valueOf(3.0),350,Duration.ofHours(24),Collections.singleton(Allergen.Haselnuss),"Apfel");
        automat.einfuegenKuchen(Kuchentyp.Obsttorte,new HerstellerImp("Aldi"),BigDecimal.valueOf(4.0),450,Duration.ofHours(24),Collections.singleton(Allergen.Erdnuss),"Apfel","Sahne");
        automat.einfuegenKuchen(Kuchentyp.Kremkuchen,new HerstellerImp("Kaufland"),BigDecimal.valueOf(3.5),400,Duration.ofHours(24),Collections.singleton(Allergen.Gluten),"Sahne");
        jos.save(automat);

        automat = jos.load();
        System.out.println(automat.auflistenKuchen());

    }



}
