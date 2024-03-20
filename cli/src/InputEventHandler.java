import java.util.LinkedList;
import java.util.List;

public class InputEventHandler {
    protected List<InputEventListener> listenerList = new LinkedList<>();

    public void add(InputEventListener listener) {
        this.listenerList.add(listener);
    }
    public void remove(InputEventListener listener) {
        this.listenerList.remove(listener);
    }
    public void removeAll() {
        this.listenerList.clear();
    }
    public void handle(InputEvent event){
        for (InputEventListener listener : listenerList)
            listener.onInputEvent(event);
    }
}