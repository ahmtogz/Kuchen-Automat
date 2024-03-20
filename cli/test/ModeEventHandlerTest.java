

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ModeEventHandlerTest {
    ModeEventHandler modeEventHandler;

    @Spy
    ModeEventListener modeEventListenerSpy;

    @Mock
    ModeEvent modeEventMock;

    @BeforeEach
    void setUp() {
        modeEventMock =  Mockito.mock(ModeEvent.class);
        when(modeEventMock.getMode()).thenReturn(null);

        modeEventHandler = new ModeEventHandler();
        modeEventListenerSpy = Mockito.spy(ModeEventListener.class);
    }

    @Test
    void addTest() throws Exception {
        modeEventHandler.add(modeEventListenerSpy);
        modeEventHandler.handle(modeEventMock);
        Mockito.verify(modeEventListenerSpy, times(1)).onModeChange(modeEventMock);
    }
    @Test
    void removeTest() throws Exception {
        modeEventHandler.add(modeEventListenerSpy);
        modeEventHandler.remove(modeEventListenerSpy);
        modeEventHandler.handle(modeEventMock);

        Mockito.verify(modeEventListenerSpy, times(0)).onModeChange(modeEventMock);
    }
}