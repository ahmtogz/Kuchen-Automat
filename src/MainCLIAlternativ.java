public class MainCLIAlternativ {
    public static void main(String[] args) {
        final int AUTOMAT_KAPAZITAET = 100;
        Automat automat = new Automat(AUTOMAT_KAPAZITAET);

        ModeEventHandler modeHandler = new ModeEventHandler();
        ModeEventListener modeListener = new ModeListener(automat);
        modeHandler.add(modeListener);

        InputEventHandler handler = new InputEventHandler();
        InputEventListener einfuegenListener = new EinfuegenListener(automat);
        InputEventListener auflistenListener = new AuflistenListener(automat);
        InputEventListener inspizierenListener = new InspizierenListener(automat);
        InputEventListener persistentListener = new PersistentListener(automat);

        handler.add(einfuegenListener);
        handler.add(auflistenListener);
        handler.add(inspizierenListener);
        handler.add(persistentListener);

        new KapazitaetObserver(automat);

        CLI cli = new CLI(automat);
        cli.setModeEventHandler(modeHandler);
        cli.setInputEventHandler(handler);
        cli.start();
    }
}
