import static org.junit.jupiter.api.Assertions.*;

import kuchen.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

class PersistentListenerTest {
    PersistentListener persistentListener;
    @Mock
    Automat automatMock;
    @Mock
    JOS josMock;
    @Mock
    JBP jbpMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        persistentListener = new PersistentListener(automatMock);
    }

    @Test
    void testOnInputEventInvalidInput() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Persistieren);
        InputEvent inputEvent = new InputEvent(this, "invalid");

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        persistentListener.onInputEvent(inputEvent);

        // Assert
        verify(josMock, never()).save(automatMock);
        assertEquals("Ungültige Eingabe", outContent.toString().trim());
    }

    @Test
    void testOnInputEventNotInPersistMode() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Auflisten);
        InputEvent inputEvent = new InputEvent(this, "saveJOS");

        // Act
        persistentListener.onInputEvent(inputEvent);

        // Assert
        verify(josMock, never()).save(automatMock);
    }
    @Test
    void testOnInputEventLoadJBP() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Persistieren);
        InputEvent inputEvent = new InputEvent(this, "loadJBP");

        // Act
        persistentListener.onInputEvent(inputEvent);

        // Assert
        verify(automatMock).ladeVonAnderemAutomat(any(Automat.class));
    }
    @Test
    void testOnInputEventLoadJOS() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Persistieren);
        InputEvent inputEvent = new InputEvent(this, "loadJOS");

        // Act
        persistentListener.onInputEvent(inputEvent);

        // Assert
        verify(automatMock).ladeVonAnderemAutomat(any(Automat.class));
    }

    @Test
    void testOnInputEventSaveJOS() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Persistieren);
        InputEvent inputEvent = new InputEvent(this, "saveJOS");

        // Act
        persistentListener.onInputEvent(inputEvent);

        // Assert
        //verify(josMock).save(automatMock);
    }


    @Test
    void testOnInputEventSaveJBP() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Persistieren);
        InputEvent inputEvent = new InputEvent(this, "saveJBP");

        // Act
        persistentListener.onInputEvent(inputEvent);

        // Assert
        //verify(jbpMock).save(automatMock);
    }





}
