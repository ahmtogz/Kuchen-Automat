import org.junit.jupiter.api.Test;
import java.util.random.RandomGenerator;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class ProducerTest {
    @Test
    void testKuchenParameters() {
        // Arrange
        Automat automatMock = mock(Automat.class);
        RandomGenerator randomGeneratorMock = mock(RandomGenerator.class);
        Producer producer = new Producer(automatMock, randomGeneratorMock);

        // Act
        producer.einfuegenKuchenSimulation();

        // Assert
        verify(automatMock, times(1)).einfuegenKuchen(any(), eq(producer.hersteller), any(), anyInt(), any(), anyList(), eq("Apfel"), eq("Sahne"));
    }

    /**
     * Überprüft, ob die Methode einfuegenKuchenSimulation() korrekt arbeitet.
     */
    @Test
    void testEinfuegenKuchenSimulation() {
        // Arrange
        Automat automatMock = mock(Automat.class);
        RandomGenerator randomGeneratorMock = mock(RandomGenerator.class);
        Producer producer = new Producer(automatMock, randomGeneratorMock);

        when(randomGeneratorMock.nextInt(anyInt())).thenReturn(0);

        // Act
        producer.einfuegenKuchenSimulation();

        // Assert
        verify(automatMock, times(1)).einfuegenKuchen(any(), any(), any(), anyInt(), any(), any(), anyString(), anyString());
    }

}

