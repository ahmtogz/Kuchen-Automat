import java.util.ArrayList;
import java.util.List;

public class ModeEventHandler {
    private List<ModeEventListener> listeners = new ArrayList<>();

    public void add(ModeEventListener listener) {
        listeners.add(listener);
    }

    public void remove(ModeEventListener listener) {
        listeners.remove(listener);
    }

    public void handle(ModeEvent event) {
        for (ModeEventListener listener : listeners) {
            listener.onModeChange(event);
        }
    }
}