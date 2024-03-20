import java.io.Serializable;

public class KapazitaetObserver implements Observer, Serializable {
    private static final long serialVersionUID = 2023;
    private Automat automat;

    public KapazitaetObserver(Automat automat) {
        this.automat = automat;
        this.automat.addObserver(this);

    }

    @Override
    public void update() {
        int currentSize = automat.auflistenKuchen().size();
        int capacity = automat.getAutomatCapacity();
        double utilizationPercentage = (double) currentSize / capacity * 100;

        if (utilizationPercentage > 90) {
            System.out.println("Achtung: Die Kapazität des Automaten liegt bei über 90 %!");
        }
    }
}
