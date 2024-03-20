import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import verwaltung.Hersteller;

import static org.junit.jupiter.api.Assertions.*;

class HerstellerImpTest {
    private Hersteller hersteller;

    @BeforeEach
    public void setUp() {
        hersteller = new HerstellerImp("TestHersteller");
    }

    @Test
    public void testGetName() {
        assertEquals("TestHersteller", hersteller.getName());
    }

    @Test
    public void testToString() {
        assertEquals("TestHersteller", hersteller.toString());
    }

}