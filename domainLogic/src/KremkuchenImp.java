

import kuchen.Allergen;
import kuchen.Kremkuchen;
import kuchen.Kuchentyp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KremkuchenImp extends KuchenImp implements Kremkuchen, Serializable {
    static final long serialVersionUID = 2L;
    private String kremsorte;

    public KremkuchenImp(Kuchentyp kuchentyp, HerstellerImp hersteller, BigDecimal preis,  int naehrwert, Duration haltbarkeit, Collection<Allergen> allergene,Date inspektionsdatum, int fachnummer, String kremsorte) {
        super(kuchentyp,hersteller,preis, naehrwert, haltbarkeit, allergene, inspektionsdatum, fachnummer);
        this.kremsorte = kremsorte;
    }


    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }

}



