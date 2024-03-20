import kuchen.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

public class EinfuegenListener implements InputEventListener {
    private Automat automat;

    public EinfuegenListener(Automat automat) {
        this.automat = automat;
    }


    @Override
    public void onInputEvent(InputEvent event) {
        if (Mode.Einfuegen.equals(automat.getCurrentMode())) {
            String[] input = event.getText().split(" ");

            if (input.length == 1) {
                handleHerstellerInput(input[0]);
            } else {
                handleKuchenInput(input);
            }
        }
    }

    private void handleHerstellerInput(String hersteller) {
        if (automat.einfuegenHersteller(hersteller)) {
            System.out.println("Hersteller " + hersteller + " erfolgreich in den Automat hinzugefügt");
        } else {
            System.out.println("Hersteller konnte nicht in den Automat hinzugefügt werden");
        }
    }

    private void handleKuchenInput(String[] input) {
        if (input.length == 7 || input.length == 8) {
            try {
                validierenUndEinfüegenKuchen(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Ungültige Eingabelänge");
        }
    }

    private void validierenUndEinfüegenKuchen(String[] input) {
        Collection<Allergen> allergene = null;
        BigDecimal preis;
        int naehrwert;
        Duration haltbarkeit;

        try {
            preis = new BigDecimal(input[2].replace(',', '.'));
            naehrwert = Integer.parseInt(input[3]);
            haltbarkeit = Duration.ofHours(Long.parseLong(input[4]));

            if (!input[5].equalsIgnoreCase(",")) {
                String[] allergenArray = input[5].split(",", 4);

                if (allergenArray.length > 0) {
                    allergene = new LinkedList<>();

                    for (String allergen : allergenArray) {
                        processAllergen(allergen, allergene);
                    }
                }
            }

            Kuchentyp kuchentyp = Kuchentyp.valueOf(input[0]);
            HerstellerImp hersteller = new HerstellerImp(input[1]);

            switch (kuchentyp) {
                case Kremkuchen:
                case Obstkuchen:
                    if (input.length == 7) {
                        automat.einfuegenKuchen(kuchentyp, hersteller, preis, naehrwert, haltbarkeit, allergene, input[6]);
                        System.out.println(kuchentyp + " erfolgreich zur Automat hinzugefügt");
                    } else {
                        throw new IllegalArgumentException("Ungültige Eingabelänge für " + kuchentyp);
                    }
                    break;
                case Obsttorte:
                    if (input.length == 8) {
                        automat.einfuegenKuchen(kuchentyp, hersteller, preis, naehrwert, haltbarkeit, allergene, input[6], input[7]);
                        System.out.println(kuchentyp + " erfolgreich zur Automat hinzugefügt");
                    } else {
                        throw new IllegalArgumentException("Ungültige Eingabelänge für " + kuchentyp);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Ungültiger Kuchentyp");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Fehler beim Parsen von Zahlen");
        } catch (Exception e) {
            throw new IllegalArgumentException("Fehler beim Einfügen des Kuchens: " + e.getMessage());
        }
    }

    private void processAllergen(String allergen, Collection<Allergen> allergene) {
        switch (allergen) {
            case "Gluten":
                allergene.add(Allergen.Gluten);
                break;
            case "Sesamsamen":
                allergene.add(Allergen.Sesamsamen);
                break;
            case "Erdnuss":
                allergene.add(Allergen.Erdnuss);
                break;
            case "Haselnuss":
                allergene.add(Allergen.Haselnuss);
                break;
            default:
                throw new IllegalArgumentException("Allergen konnte nicht erkannt werden");
        }
    }
}

