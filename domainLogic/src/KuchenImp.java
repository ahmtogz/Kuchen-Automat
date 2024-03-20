import kuchen.Allergen;
import kuchen.Kuchen;
import kuchen.Kuchentyp;
import verwaltung.Hersteller;
import verwaltung.Verkaufsobjekt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class KuchenImp implements Kuchen, Verkaufsobjekt, Serializable {
    static final long serialVersionUID = 3L;
    private Kuchentyp kuchentyp = null;
    private Hersteller hersteller;
    private Collection<Allergen> allergene;
    private int naehrwert;
    private Duration haltbarkeit;
    private Date ablaufDatum;
    private BigDecimal preis;
    private Date inspektionsdatum;
    private int fachnummer;


    public KuchenImp(Kuchentyp kuchentyp,HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, Collection<Allergen> allergene, Date inspektionsdatum, int fachnummer){
        this.kuchentyp = kuchentyp;
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
        this.inspektionsdatum = inspektionsdatum;
        this.fachnummer = fachnummer;
        this.ablaufDatum = calcHaltbarkeit();
    }



    public Kuchentyp getKuchentyp() {
        return kuchentyp;
    }

    @Override
    public Hersteller getHersteller() {
        return this.hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return this.allergene;
    }

    @Override
    public int getNaehrwert() {
        return this.naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return Duration.between(Instant.now(),ablaufDatum.toInstant());
    }

    @Override
    public BigDecimal getPreis() {
        return this.preis;
    }
    @Override
    public Date getInspektionsdatum() {
        return this.inspektionsdatum;
    }

    public void setInspektionsdatum(Date inspektionsdatum) {
        this.inspektionsdatum = inspektionsdatum;
    }

    @Override
    public int getFachnummer() {
        return this.fachnummer;
    }

    private Date calcHaltbarkeit(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, (int)(haltbarkeit.toHours()));
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return "KuchenClass { " +
                "kuchentyp=" + kuchentyp +
                ", hersteller=" + hersteller +
                ", preis=" + preis +
                ", naehrwert=" + naehrwert +
                ", haltbarkeit=" + haltbarkeit +
                ", allergens=" + allergene +
                ", inspektionsdatum=" + inspektionsdatum +
                ", fachnummer=" + fachnummer +
                " }";
    }
}
