import kuchen.Mode;

public class ModeListener implements ModeEventListener {


    private Automat automat;
    private static final String MODE_EINFUEGEN = ":c";
    private static final String MODE_AUFLISTEN = ":r";
    private static final String MODE_AENDERN = ":u";
    private static final String MODE_ENTFERNEN = ":d";
    private static final String MODE_PERSISTIEREN = ":p";
    private static final String MODE_EXIT = ":q";

    public ModeListener(Automat automat) {
        this.automat = automat;
    }

    @Override
    public void onModeChange(ModeEvent event) {
        String mode = event.getMode();

        switch (mode) {
            case MODE_EINFUEGEN:
                setMode(Mode.Einfuegen, "Einfuegemodus");
                break;
            case MODE_AUFLISTEN:
                setMode(Mode.Auflisten, "Anzeigemodus");
                break;
            case MODE_AENDERN:
                setMode(Mode.Aendern, "Ändernmodus");
                break;
            case MODE_ENTFERNEN:
                setMode(Mode.Entfernen, "Entfernenmodus");
                break;
            case MODE_PERSISTIEREN:
                setMode(Mode.Persistieren, "Persistentmodus");
                break;
            case MODE_EXIT:
                System.exit(0);
                break;
            default:
                System.out.println("Ungültiger Modus: " + mode);
        }
    }

    private void setMode(Mode newMode, String modeDescription) {
        automat.setCurrentMode(newMode);
        System.out.println("In den " + modeDescription + " gewechselt");
    }
}

