

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeEventTest {
    ModeEvent modeEvent;

    @BeforeEach
    void setUp() {
        modeEvent = new ModeEvent(this, "test");
    }

    @Test
    void getDataTest() {
        String actual = modeEvent.getMode();
        String expected = "test";
        Assertions.assertEquals(expected, actual);
    }
}