

import static org.junit.jupiter.api.Assertions.*;
import kuchen.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

class EinfuegenListenerTest {
    EinfuegenListener einfuegenListener;
    Automat automatMock;

    @BeforeEach
    void setUp() {
        automatMock = Mockito.mock(Automat.class);
        einfuegenListener = new EinfuegenListener(automatMock);
        when(automatMock.getCurrentMode()).thenReturn(Mode.Einfuegen);
    }
    /**
     * Testet das Verhalten, wenn der Automat nicht im Einfuegen-Modus ist.
     */
    @Test
    void testOnInputEventNotInAuflistenMode() {
        String hersteller = "Alice";
        // Arrange
        when(automatMock.getCurrentMode()).thenReturn(Mode.Auflisten);

        // Act
        einfuegenListener.onInputEvent(new InputEvent(this, hersteller));

        // Assert
        verify(automatMock, never()).einfuegenHersteller(hersteller);
    }
    @Test
    void testOnInputEventEinfuegenHersteller() {
        // Arrange
        String hersteller = "Alice";
        when(automatMock.einfuegenHersteller(hersteller)).thenReturn(true);
        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        einfuegenListener.onInputEvent(new InputEvent(this, hersteller));

        // Assert
        verify(automatMock).einfuegenHersteller(hersteller);
        assertEquals("Hersteller Alice erfolgreich in den Automat hinzugefügt", outContent.toString().trim());
    }

    @Test
    void testOnInputEventHerstellerFailed() {
        // Arrange
        String hersteller = "Alice";
        when(automatMock.einfuegenHersteller(hersteller)).thenReturn(false);

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        einfuegenListener.onInputEvent(new InputEvent(this, hersteller));

        // Assert
        verify(automatMock).einfuegenHersteller(hersteller);
        assertEquals("Hersteller konnte nicht in den Automat hinzugefügt werden", outContent.toString().trim());
    }

    @Test
    void testOnInputEventValidCake() {
        // Arrange
        String[] input = {"Kremkuchen", "Alice", "10.5", "100", "10", "Gluten,Sesamsamen", "Vanilie"};

        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        einfuegenListener.onInputEvent(new InputEvent(this, String.join(" ", input)));

        // Assert
        // Überprüfen Sie, ob die richtigen Methoden aufgerufen werden
        verify(automatMock).einfuegenKuchen(
                eq(Kuchentyp.Kremkuchen),
                any(HerstellerImp.class),
                eq(new BigDecimal("10.5")),
                eq(100),
                eq(Duration.ofHours(10)),
                argThat((Collection<Allergen> allergene) -> allergene.containsAll(Arrays.asList(Allergen.Gluten, Allergen.Sesamsamen))),
                eq("Vanilie")
        );
        assertEquals("Kremkuchen erfolgreich zur Automat hinzugefügt", outContent.toString().trim());
    }

    @Test
    void testInvalidInput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Act
        einfuegenListener.onInputEvent(new InputEvent(this, "Kremkuchen Alice"));

        // Assert
        assertTrue(outContent.toString().contains("Ungültige Eingabelänge"));
    }

    @Test
    void testInvalidCake() {
        // Arrange
        String[] input = {"UngültigerTyp", "Hersteller", "10.5", "100", "10", "Gluten,Sesamsamen", "Apfel"};

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        einfuegenListener.onInputEvent(new InputEvent(this, String.join(" ", input)));

        // Assert
        assertTrue(outContent.toString().contains("Fehler beim Einfügen des Kuchens: No enum constant kuchen.Kuchentyp.UngültigerTyp"));
    }
    @Test
    void testInvalidAllergen() {
        // Arrange
        String[] input = {"Kremkuchen", "Hersteller", "10.5", "100", "10", "InvalidAllergen", "Apfel"};

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Act
        einfuegenListener.onInputEvent(new InputEvent(this, String.join(" ", input)));

        // Assert
        // Erwartet: Fehlermeldung wegen ungültigem Allergen
        assertTrue(outputStreamCaptor.toString().contains("Fehler beim Einfügen des Kuchens: Allergen konnte nicht erkannt werden"));
    }
    @Test
    void testValidObsttorteInput() {
        // Arrange
        String[] input = {"Obsttorte", "Alice", "10.5", "100", "10", "Gluten,Sesamsamen", "Apfel", "Vanilie"};
        // Act
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        einfuegenListener.onInputEvent(new InputEvent(this, String.join(" ", input)));

        // Assert
        verify(automatMock).einfuegenKuchen(
                eq(Kuchentyp.Obsttorte),
                any(HerstellerImp.class),
                eq(new BigDecimal("10.5")),
                eq(100),
                eq(Duration.ofHours(10)),
                argThat((Collection<Allergen> allergene) -> allergene.containsAll(Arrays.asList(Allergen.Gluten, Allergen.Sesamsamen))),
                eq("Apfel"),
                eq("Vanilie")
        );
    }

}
