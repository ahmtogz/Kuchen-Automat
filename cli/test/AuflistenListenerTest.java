

import kuchen.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AuflistenListenerTest {
    AuflistenListener auflistenListener;
    Automat automatMock;

    @BeforeEach
    void setUp() {
        automatMock = Mockito.mock(Automat.class);
        auflistenListener = new AuflistenListener(automatMock);

        when(automatMock.getCurrentMode()).thenReturn(Mode.Auflisten);
    }

    /**
     * Testet das Verhalten, wenn der Automat nicht im Auflisten-Modus ist.
     */
    @Test
    void testOnInputEventNotInAuflistenMode() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Einfuegen);

        // Act
        auflistenListener.onInputEvent(new InputEvent(this, "hersteller"));

        // Assert
        verify(automatMock, never()).getHerstellerMitKuchenAnzahl();
    }

    /**
     * Testet das Verhalten, wenn eine ungültige Eingabe erfolgt.
     */
    @Test
    void testOnInputEventWithInvalidInput() {

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        auflistenListener.onInputEvent(new InputEvent(this, "ungültigeEingabe"));

        // Assert
        assertEquals("Ungültiger Eingabe", outContent.toString().trim());
    }

    /**
     * Testet das Listieren der Hersteller mit der Anzahl der Kuchen.
     */
    @Test
    void testListHerstellerWithKuchenCount() {
        // Arrange
        Map<String, Integer> herstellerMitKuchenAnzahl = new HashMap<>();
        herstellerMitKuchenAnzahl.put("Hersteller1", 5);
        herstellerMitKuchenAnzahl.put("Hersteller2", 3);

        when(automatMock.getHerstellerMitKuchenAnzahl()).thenReturn(herstellerMitKuchenAnzahl);

        // Act
        auflistenListener.onInputEvent(new InputEvent(this, "hersteller"));

        // Assert
        verify(automatMock).getHerstellerMitKuchenAnzahl();

    }

    /**
     * Testet das Verhalten bei verschiedenen Eingaben.
     */
    @Test
    void testOnInputEventWithDifferentCommands() {
        // Act
        auflistenListener.onInputEvent(new InputEvent(this, "kuchen Kremkuchen"));
        auflistenListener.onInputEvent(new InputEvent(this, "allergene i"));
        auflistenListener.onInputEvent(new InputEvent(this, "allergene e"));

        // Assert
        verify(automatMock).getKuchenNachTyp(any());
        verify(automatMock).getAlleVorhandenenAllergene();
    }

    /**
     * Testet das Verhalten, wenn ein ungültiger Kuchentyp angefordert wird.
     */
    @Test
    void testOnInputEventWithInvalidCakeType() {
        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        auflistenListener.onInputEvent(new InputEvent(this, "kuchen invaliderKuchentyp"));

        assertEquals("Ungültiger Kuchentyp: invaliderKuchentyp", outContent.toString().trim());
    }

    /**
     * Testet das Verhalten, wenn der Modus geändert wird.
     */
    @Test
    void testOnInputEventModeChanged() {
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Einfuegen).thenReturn(Mode.Auflisten);

        // Act
        auflistenListener.onInputEvent(new InputEvent(this, "hersteller"));
        auflistenListener.onInputEvent(new InputEvent(this, "hersteller"));

        // Assert
        verify(automatMock, times(2)).getCurrentMode();
        verify(automatMock).getHerstellerMitKuchenAnzahl();
    }
}
