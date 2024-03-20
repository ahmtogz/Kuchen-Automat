import verwaltung.Hersteller;

import java.io.*;

public class HerstellerImp implements Hersteller, Serializable {
    static final long serialVersionUID = 1L;
    private String name;


    public HerstellerImp(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
