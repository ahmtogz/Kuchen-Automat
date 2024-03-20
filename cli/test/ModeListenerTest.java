

import static org.junit.jupiter.api.Assertions.*;

import kuchen.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;

class ModeListenerTest {
    ModeListener modeListener;
    Automat automatMock;

    private static final String MODE_EINFUEGEN = ":c";
    private static final String MODE_AUFLISTEN = ":r";
    private static final String MODE_AENDERN = ":u";
    private static final String MODE_ENTFERNEN = ":d";
    private static final String MODE_PERSISTIEREN = ":p";

    @BeforeEach
    void setUp() {
        automatMock = Mockito.mock(Automat.class);
        modeListener = new ModeListener(automatMock);
    }

    @Test
    void testOnModeChangeEinfuegenMode() {
        // Arrange
        ModeEvent modeEvent = new ModeEvent(this, MODE_EINFUEGEN);

        // Act
        modeListener.onModeChange(modeEvent);

        // Assert
        verify(automatMock).setCurrentMode(Mode.Einfuegen);

    }

    @Test
    void testOnModeChangeAuflistenMode() {
        // Arrange
        ModeEvent modeEvent = new ModeEvent(this, MODE_AUFLISTEN);

        // Act
        modeListener.onModeChange(modeEvent);

        // Assert
        verify(automatMock).setCurrentMode(Mode.Auflisten);
    }

    @Test
    void testOnModeChangeAendernMode() {
        // Arrange
        ModeEvent modeEvent = new ModeEvent(this, MODE_AENDERN);

        // Act
        modeListener.onModeChange(modeEvent);

        // Assert
        verify(automatMock).setCurrentMode(Mode.Aendern);
    }

    @Test
    void testOnModeChangeEntfernenMode() {
        // Arrange
        ModeEvent modeEvent = new ModeEvent(this, MODE_ENTFERNEN);

        // Act
        modeListener.onModeChange(modeEvent);

        // Assert
        verify(automatMock).setCurrentMode(Mode.Entfernen);
    }

    @Test
    void testOnModeChangePersistierenMode() {
        // Arrange
        ModeEvent modeEvent = new ModeEvent(this, MODE_PERSISTIEREN);

        // Act
        modeListener.onModeChange(modeEvent);

        // Assert
        verify(automatMock).setCurrentMode(Mode.Persistieren);
    }
}