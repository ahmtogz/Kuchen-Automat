

import static org.junit.jupiter.api.Assertions.*;

import kuchen.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

class EntfernenListenerTest {
    EntfernenListener entfernenListener;
    Automat automatMock;

    @BeforeEach
    void setUp() {
        automatMock = Mockito.mock(Automat.class);
        entfernenListener = new EntfernenListener(automatMock);

        when(automatMock.getCurrentMode()).thenReturn(Mode.Entfernen);
    }

    @Test
    void testOnInputEventRemoveCake() {

        // Arrange
        String fachnummer = "5";
        doNothing().when(automatMock).entfernenKuchen(anyInt());

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        entfernenListener.onInputEvent(new InputEvent(this, fachnummer));

        // Assert
        verify(automatMock).entfernenKuchen(5);
        assertEquals("Kuchen erfolgreich auf Fach 5 gelöscht", outContent.toString().trim());
    }
    @Test
    void testOnInputEventRemoveManufacturer() {
        // Arrange
        when(automatMock.entfernenHersteller(anyString())).thenReturn(true);

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        entfernenListener.onInputEvent(new InputEvent(this, "HerstellerName"));

        // Assert
        verify(automatMock).entfernenHersteller("HerstellerName");
        assertEquals("Hersteller HerstellerName erfolgreich von Automat entfernt", outContent.toString().trim());
    }
    @Test
    void testOnInputEventExceptionWhileRemovingCake() {
        // Arrange
        String fachnummer = "5";
        doThrow(IndexOutOfBoundsException.class).when(automatMock).entfernenKuchen(anyInt());

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        entfernenListener.onInputEvent(new InputEvent(this, fachnummer));

        // Assert
        verify(automatMock).entfernenKuchen(5);
        assertEquals("Fehler: Fachnummer 5 nicht vorhanden.", outContent.toString().trim());
    }

    @Test
    void testOnInputEventFailedToRemoveManufacturer() {
        // Arrange
        when(automatMock.entfernenHersteller(anyString())).thenReturn(false);

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        entfernenListener.onInputEvent(new InputEvent(this, "HerstellerName"));

        // Assert
        verify(automatMock).entfernenHersteller("HerstellerName");
        assertEquals("Fehler: Hersteller HerstellerName konnte nicht von Automat entfernt werden.", outContent.toString().trim());
    }

    @Test
    void testOnInputEventInvalidInput() {
        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        entfernenListener.onInputEvent(new InputEvent(this, "ungültige eingabe"));

        // Assert
        verify(automatMock, never()).entfernenKuchen(anyInt());
        verify(automatMock, never()).entfernenHersteller(anyString());
        assertEquals("Fehler: Ungültige Eingabe", outContent.toString().trim());
    }
}
