

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputEventTest {
    InputEvent inputEvent;

    @BeforeEach
    void setUp() {
        inputEvent = new InputEvent(this, "test");
    }

    @Test
    void getDataTest() {
        String actual = inputEvent.getText();
        String expected = "test";
        Assertions.assertEquals(expected, actual);
    }

}