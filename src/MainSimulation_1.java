import java.util.Random;

public class MainSimulation_1 {


    public static void main(String[] args) {
        final int AUTOMAT_KAPAZITAET = 50;
        Automat automat = new Automat(AUTOMAT_KAPAZITAET);
        Random random = new Random();
        Producer thread1 = new Producer(automat,random);
        Consumer thread2 = new Consumer(automat);

        thread1.start();
        thread2.start();

    }
}
