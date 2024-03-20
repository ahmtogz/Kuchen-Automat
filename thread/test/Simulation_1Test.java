import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

public class Simulation_1Test {
    Random random;
    Automat automat;

    @BeforeEach
    void setUp() {
        random = new Random();
        automat = new Automat(10);
        automat.einfuegenHersteller("Alice");
    }
    @Test

    public void einfuegenKuchenSim(){
        Producer producer = new Producer(automat,random);
        producer.einfuegenKuchenSimulation();
        List<KuchenImp> kuchen = automat.auflistenKuchen();
        Assertions.assertNotNull(kuchen);
    }

    @Test
    public void EntfernenKuchenEmptySim() {
        Consumer consumer = new Consumer(automat);
        boolean result = consumer.entfernenKuchenSimulation();
        Assertions.assertFalse(result);
    }

    @Test

    public void EinfuegenundEntfernenKuchenSim() {
        Producer producer = new Producer(automat,random);
        producer.einfuegenKuchenSimulation();
        Consumer consumer = new Consumer(automat);
        boolean result = consumer.entfernenKuchenSimulation();
        Assertions.assertTrue(result);
    }
}
