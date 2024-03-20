import java.util.List;
import java.util.Random;

public class Consumer extends Thread{

    private Automat automat;
    private final Random random = new Random();

    public Consumer(Automat automat) {
        this.automat = automat;
    }

    @Override
    public void run() {
        while (true) {
                try {
                    entfernenKuchenSimulation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
    public boolean entfernenKuchenSimulation(){
        List<KuchenImp> kuchenList = automat.auflistenKuchen();

        if (!kuchenList.isEmpty()) {
            int randomIndex = random.nextInt(kuchenList.size());
            KuchenImp kuchenToRemove = kuchenList.get(randomIndex);
            int fachnummerToRemove = kuchenToRemove.getFachnummer();

            automat.entfernenKuchen(fachnummerToRemove);
            System.out.println(Thread.currentThread().getName() + " [Kuchen entfernt]" + kuchenToRemove);

            return true;
        }
        return false;
    }


}
