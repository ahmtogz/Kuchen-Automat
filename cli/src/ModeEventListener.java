import java.util.EventListener;

public interface ModeEventListener extends EventListener {
    void onModeChange(ModeEvent event);
}