import kuchen.Mode;

import java.util.Date;

public class InspizierenListener implements InputEventListener{
    private Automat automat;
    public InspizierenListener(Automat automat){
        this.automat = automat;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if (automat.getCurrentMode() == Mode.Aendern) {
            String[] input = event.getText().split(" ");

            if (input.length == 1) {
                String fachnummerStr = input[0];

                if (fachnummerStr.matches("[0-9]+")) {
                    int fachnummer = Integer.parseInt(fachnummerStr);
                    try {
                        automat.aendern(fachnummer);
                        System.out.println("Inspektionsdatum erfolgreich aktualisiert.");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Fehler: Fachnummer nicht vorhanden.");
                    }
                } else {
                    System.out.println("Fehler: Ungültige Eingabe. Geben Sie eine Fachnummer ein, um das Inspektionsdatum zu aktualisieren.");
                }
            } else {
                System.out.println("Fehler: Ungültige Eingabelaenge. Geben Sie nur die Fachnummer ein.");
            }
        }
    }
}
