import kuchen.Allergen;
import kuchen.Kuchentyp;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.random.RandomGenerator;

public class Producer extends Thread{

    private Automat automat;
    private RandomGenerator random;
    HerstellerImp hersteller= new HerstellerImp("Alice");

    public Producer(Automat automat, RandomGenerator random) {
        this.random = random;
        this.automat = automat;
    }

    @Override
    public void run() {
        automat.einfuegenHersteller("Alice");

        while (true) {
            try {

                einfuegenKuchenSimulation();

                Thread.sleep(0);

            } catch (InterruptedException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

    }
    public void einfuegenKuchenSimulation(){
        Kuchentyp[] values = Kuchentyp.values();
        int randomIndex = random.nextInt(values.length);
        Kuchentyp randomKuchentyp = values[randomIndex];

        List<Allergen> allergens = new LinkedList<>();
        allergens.add(Allergen.Erdnuss);
        allergens.add(Allergen.Haselnuss);

        int randomNaehrwert = random.nextInt(10) + 1;

        int stunden = random.nextInt(15) + 1;
        Duration haltbarkeit = Duration.ofHours(stunden);

        double preis = random.nextDouble() * 10 + 1;
        BigDecimal randomPrice = BigDecimal.valueOf(preis);

        automat.einfuegenKuchen(randomKuchentyp, hersteller, randomPrice, randomNaehrwert, haltbarkeit, allergens, "Apfel","Sahne");
        System.out.println(Thread.currentThread().getName() + " [Kuchen erstellt] ");
    }
}
