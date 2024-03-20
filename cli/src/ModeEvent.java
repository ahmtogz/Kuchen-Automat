import java.util.EventObject;

public class ModeEvent extends EventObject {
    private String mode;

    public ModeEvent(Object source, String mode) {
        super(source);
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}