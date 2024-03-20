

import kuchen.Allergen;
import kuchen.Kuchentyp;
import kuchen.Obsttorte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObsttorteImp extends KuchenImp implements Obsttorte, Serializable {
    static final long serialVersionUID = 5L;
    private String obstsorte;
    private String kremsorte;


    public ObsttorteImp(Kuchentyp kuchentyp, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, Collection<Allergen> allergene, Date inspektionsdatum, int fachnummer, String obstsorte, String kremsorte) {
        super(kuchentyp, hersteller, preis, naehrwert, haltbarkeit, allergene,inspektionsdatum, fachnummer);
        this.kremsorte = kremsorte;
        this.obstsorte = obstsorte;
    }

    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }

    @Override
    public String getObstsorte() {
        return this.obstsorte;
    }

}
