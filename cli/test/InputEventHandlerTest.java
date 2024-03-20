

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class InputEventHandlerTest {
    InputEventHandler inputEventHandler;

    @Spy
    InputEventListener inputEventListenerSpy;

    @Mock
    InputEvent inputEventMock;

    @BeforeEach
    void setUp() {
        inputEventMock =  Mockito.mock(InputEvent.class);
        when(inputEventMock.getText()).thenReturn(null);

        inputEventHandler = new InputEventHandler();
        inputEventListenerSpy = Mockito.spy(InputEventListener.class);
    }

    @Test
    void addTest() throws Exception {
        inputEventHandler.add(inputEventListenerSpy);
        inputEventHandler.handle(inputEventMock);
        Mockito.verify(inputEventListenerSpy, times(1)).onInputEvent(inputEventMock);
    }
    @Test
    void removeTest() throws Exception {
        inputEventHandler.add(inputEventListenerSpy);
        inputEventHandler.remove(inputEventListenerSpy);
        inputEventHandler.handle(inputEventMock);

        Mockito.verify(inputEventListenerSpy, times(0)).onInputEvent(inputEventMock);
    }

    @Test
    void removeAllTest() {
        inputEventHandler.add(inputEventListenerSpy);
        inputEventHandler.removeAll();
        Assertions.assertTrue(inputEventHandler.listenerList.isEmpty());
    }

}