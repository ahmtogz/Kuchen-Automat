

import static org.junit.jupiter.api.Assertions.*;

import kuchen.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

class InspizierenListenerTest {
    InspizierenListener inspizierenListener;
    Automat automatMock;

    @BeforeEach
    void setUp() {
        automatMock = Mockito.mock(Automat.class);
        inspizierenListener = new InspizierenListener(automatMock);

        when(automatMock.getCurrentMode()).thenReturn(Mode.Aendern);



    }


    @Test
    void testOnInputEventValidFachnummer() {
        // Arrange
        String fachnummer = "5";
        when(automatMock.aendern(anyInt())).thenReturn(true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        inspizierenListener.onInputEvent(new InputEvent(this, fachnummer));

        // Assert
        verify(automatMock).aendern(Integer.parseInt(fachnummer));
        assertEquals("Inspektionsdatum erfolgreich aktualisiert.", outContent.toString().trim());
    }

    @Test
    void testOnInputEventInvalidInput() {
        // Arrange
        String invalidInput = "invalid 123";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        inspizierenListener.onInputEvent(new InputEvent(this, invalidInput));

        // Assert
        assertEquals("Fehler: Ungültige Eingabelaenge. Geben Sie nur die Fachnummer ein.", outContent.toString().trim());
    }

    @Test
    void testOnInputEventInvalidFachnummer() {
        // Arrange
        String invalidFachnummer = "5a";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        inspizierenListener.onInputEvent(new InputEvent(this, invalidFachnummer));

        // Assert
        assertEquals("Fehler: Ungültige Eingabe. Geben Sie eine Fachnummer ein, um das Inspektionsdatum zu aktualisieren.", outContent.toString().trim());
    }
}
