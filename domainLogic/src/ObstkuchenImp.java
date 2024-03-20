
import kuchen.Allergen;
import kuchen.Kuchentyp;
import kuchen.Obstkuchen;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;


public class ObstkuchenImp extends KuchenImp implements Obstkuchen, Serializable {
    static final long serialVersionUID = 4L;
    private String obstsorte;

    public ObstkuchenImp(Kuchentyp kuchentyp, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, Collection<Allergen> allergene, Date inspektionsdatum, int fachnummer, String obstsorte) {
        super(kuchentyp,hersteller,preis, naehrwert, haltbarkeit, allergene, inspektionsdatum, fachnummer);
        this.obstsorte = obstsorte;
    }


    @Override
    public String getObstsorte() {
        return this.obstsorte;
    }

}
