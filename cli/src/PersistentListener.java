import kuchen.Mode;

import java.io.IOException;

public class PersistentListener implements InputEventListener{
    private Automat automat;
    private JOS jos = new JOS();
    private JBP jbp = new JBP();
    public PersistentListener(Automat automat) {
        this.automat = automat;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if (Mode.Persistieren.equals(automat.getCurrentMode())) {
            switch (event.getText()) {
                case "saveJOS":
                    jos.save(automat);
                    System.out.println("Erfolgreich mit JOS gespeichert");
                    break;
                case "loadJOS":
                    Automat geladenerAutomatJos = jos.load();
                    automat.ladeVonAnderemAutomat(geladenerAutomatJos);
                    System.out.println("Automat erfolgreich mit JOS geladen");
                    break;
                case "saveJBP":
                    jbp.save(automat);
                    System.out.println("Erfolgreich mit JBP gespeichert");
                    break;
                case "loadJBP":
                    Automat geladenerAutomatJbp = jbp.load();
                    automat.ladeVonAnderemAutomat(geladenerAutomatJbp);
                    System.out.println("Automat erfolgreich mit JBP geladen");
                    break;
                default:
                    System.out.println("Ungültige Eingabe");
            }
        }
    }
}
