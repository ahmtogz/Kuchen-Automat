import java.util.Scanner;

public class CLI {
    private Automat automat;
    private InputEventHandler inputEventHandler;
    private ModeEventHandler modeEventHandler;

    public CLI(Automat automat) {
        this.automat = automat;

    }

    public void setInputEventHandler(InputEventHandler inputEventHandler) {
        this.inputEventHandler = inputEventHandler;
    }

    public void setModeEventHandler(ModeEventHandler modeEventHandler) {
        this.modeEventHandler = modeEventHandler;
    }

    public void start() {
        printCommands();
        try (Scanner scanner = new Scanner(System.in)) {
            do {

                System.out.println("Geben Sie den Befehl ein:");

                String userInput = scanner.nextLine().trim();

                if (!userInput.isEmpty()) {
                    if (userInput.startsWith(":")) {
                        processModeCommand(userInput);
                    } else {
                        processInputCommand(userInput);
                    }
                    automat.notifyObservers();
                } else {
                    System.out.println("Die Eingabe darf nicht leer sein");
                }
            } while (true);
        }
    }

    private void printCommands() {
        System.out.println(":c Wechsel in den Einfügemodus");
        System.out.println(":r Wechsel in den Anzeigemodus");
        System.out.println(":u Wechsel in den Änderungsmodus");
        System.out.println(":d Wechsel in den Löschmodus");
        System.out.println(":p Wechsel in den Persistenzmodus");
        System.out.println(":q Beenden");
    }

    private void processModeCommand(String userInput) {
        ModeEvent modeEvent = new ModeEvent(this, userInput);
        modeEventHandler.handle(modeEvent);
    }

    private void processInputCommand(String userInput) {
        InputEvent inputEvent = new InputEvent(this, userInput);
        inputEventHandler.handle(inputEvent);
    }
}

