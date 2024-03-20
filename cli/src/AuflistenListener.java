import kuchen.Kuchentyp;
import kuchen.Mode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AuflistenListener implements InputEventListener{
    private Automat automat;

    public AuflistenListener(Automat automat) {
        this.automat = automat;
    }

    @Override
    public void onInputEvent(InputEvent event) {
        if (Mode.Auflisten.equals(automat.getCurrentMode())) {
            String[] input = event.getText().split(" ");

            if (event.getText().equalsIgnoreCase("hersteller")) {
                listHerstellerWithKuchenCount();
            } else if (input[0].equalsIgnoreCase("kuchen")) {
                listKuchenByType(input);
            } else if (input[0].equalsIgnoreCase("allergene")) {
                listAllergene(input);
            } else {
                System.out.println("Ungültiger Eingabe");
            }
        }
    }

    private void listHerstellerWithKuchenCount() {
        Map<String, Integer> herstellerMitKuchenAnzahl = automat.getHerstellerMitKuchenAnzahl();

        for (Map.Entry<String, Integer> entry : herstellerMitKuchenAnzahl.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " Kuchen");
        }
    }

    private void listKuchenByType(String[] input) {
        if (input.length == 1){
            for (KuchenImp kuchen : automat.auflistenKuchen()) {
                System.out.println(kuchen);
            }
        }
        else if (input.length == 2) {
            try {
                Kuchentyp kuchentyp = Kuchentyp.valueOf(input[1]);
                System.out.println(automat.getKuchenNachTyp(kuchentyp));
            } catch (IllegalArgumentException e) {
                System.out.println("Ungültiger Kuchentyp: " + input[1]);
            }
        } else {
            System.out.println("Ungültige Eingabe");
        }
    }

    private void listAllergene(String[] input) {
        if (input.length > 1) {
            switch (input[1]) {
                case "i":
                    System.out.println(automat.getAlleVorhandenenAllergene());
                    break;
                case "e":
                    System.out.println(automat.getAlleNichtVorhandenenAllergene());
                    break;
                default:
                    System.out.println("Ungültiger Eingabe. Geben Sie „allergene i“ für vorhandene Allergene und „allergene e“ für nicht vorhandene Allergene ein");
            }
        } else {
            System.out.println("Ungültige Eingabelänge. Geben Sie „allergene i“ für vorhandene Allergene und „allergene e“ für nicht vorhandene Allergene ein");
        }
    }


}

