import kuchen.Mode;

public class EntfernenListener implements InputEventListener{
    private Automat automat;

    public EntfernenListener(Automat automat) {
        this.automat = automat;
    }


    @Override
    public void onInputEvent(InputEvent event) {
        if (Mode.Entfernen.equals(automat.getCurrentMode())) {
            String[] input = event.getText().split(" ");

            if (input.length == 1) {
                if (input[0].matches("[0-9]+")) {
                    int fachnummer = Integer.parseInt(input[0]);
                    try {
                        automat.entfernenKuchen(fachnummer);
                        System.out.println("Kuchen erfolgreich auf Fach " + fachnummer + " gelöscht");
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Fehler: Fachnummer " + fachnummer + " nicht vorhanden.");
                    } catch (Exception e) {
                        System.out.println("Fehler beim Löschen des Kuchens auf Fach " + fachnummer);
                    }
                } else {
                    if (automat.entfernenHersteller(input[0])) {
                        System.out.println("Hersteller " + input[0] + " erfolgreich von Automat entfernt");
                    } else {
                        System.out.println("Fehler: Hersteller " + input[0] + " konnte nicht von Automat entfernt werden.");
                    }
                }
            } else {
                System.out.println("Fehler: Ungültige Eingabe");
            }
        }
    }
}
