import kuchen.Allergen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.*;

public class ConsumerTest {

    /**
     * Überprüft, ob die Methode entfernenKuchenSimulation nichts tut, wenn der Automat leer ist.
     */
    @Test
    void testEntfernenKuchenSimulationWhenAutomatIsEmpty() {
        // Arrange
        Automat automatMock = mock(Automat.class);
        Consumer consumer = new Consumer(automatMock);
        when(automatMock.auflistenKuchen()).thenReturn(Collections.emptyList());
        // Act
        consumer.entfernenKuchenSimulation();
        // Assert
        verify(automatMock, never()).entfernenKuchen(anyInt());
    }
    /**
     * Überprüft, ob die Methode entfernenKuchenSimulation einen Kuchen entfernt, wenn der Automat nicht leer ist.
     */
    @Test
    void testEntfernenKuchenSimulationWhenAutomatIsNotEmpty() {
        // Arrange
        Automat automatMock = mock(Automat.class);
        Consumer consumer = new Consumer(automatMock);
        List<KuchenImp> kuchenList = new ArrayList<>();
        KuchenImp kuchen = new KuchenImp(Kuchentyp.Obstkuchen, new HerstellerImp("Aldi"), BigDecimal.valueOf(3.0),350, Duration.ofHours(24),Collections.singleton(Allergen.Haselnuss),new Date(),1);
        kuchenList.add(kuchen);
        when(automatMock.auflistenKuchen()).thenReturn(kuchenList);

        // Act
        consumer.entfernenKuchenSimulation();

        // Assert
        verify(automatMock, times(1)).entfernenKuchen(anyInt());
    }

}