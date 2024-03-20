import kuchen.Allergen;
import kuchen.Kuchentyp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JBPTest {
    private Kuchentyp kuchentyp;
    private String herstellerName;
    private HerstellerImp hersteller;
    private BigDecimal preis;
    private Duration haltbarkeit;
    private int naehrwert;
    private String obstsorte;
    private Collection<Allergen> allergene;
    private Automat automat;
    @Mock
    private Automat automatMock;
    private JBP jbp;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        kuchentyp = Kuchentyp.Obstkuchen;
        herstellerName = "Alice";
        hersteller = new HerstellerImp(herstellerName);
        allergene = Collections.singleton(Allergen.Erdnuss);
        naehrwert = 100;
        haltbarkeit = Duration.ofHours(24);
        preis = new BigDecimal("1.99");
        obstsorte = "Apfel";
        jbp = new JBP();
        automat = new Automat(100);

    }
    @Test
    public void testSaveUndLoadEmptyAutomatJBP() {
        Automat emptyAutomat = new Automat(100);

        jbp.save(emptyAutomat);
        Automat loadedAutomat = jbp.load();

        Assertions.assertNotNull(loadedAutomat);
        assertEquals(0, loadedAutomat.auflistenKuchen().size());
    }
    @Test
    public void testSaveUndLoadJBP() {
        automat.einfuegenHersteller("Alice");
        automat.einfuegenKuchen(kuchentyp,hersteller,preis,naehrwert,haltbarkeit,allergene,obstsorte);

        jbp.save(automat);

        Automat loadedAutomat = jbp.load();

        Assertions.assertNotNull(loadedAutomat);
        assertEquals(1, loadedAutomat.auflistenKuchen().size());

        KuchenImp loadedKuchen = loadedAutomat.auflistenKuchen().get(0);
        assertEquals(kuchentyp, loadedKuchen.getKuchentyp());
        assertEquals(herstellerName, loadedKuchen.getHersteller().getName());
        assertEquals(preis, loadedKuchen.getPreis());
        assertEquals(naehrwert, loadedKuchen.getNaehrwert());
        assertEquals(allergene, loadedKuchen.getAllergene());
        Assertions.assertTrue(loadedKuchen instanceof ObstkuchenImp);
        assertEquals(obstsorte, ((ObstkuchenImp) loadedKuchen).getObstsorte());
    }

    @Test
    public void testSaveUndLoadWithMultipleKuchenTypesJBP() {
        automat.einfuegenHersteller("Alice");
        automat.einfuegenKuchen(kuchentyp, hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte);
        automat.einfuegenKuchen(Kuchentyp.Kremkuchen, hersteller, preis, naehrwert, haltbarkeit, allergene, "Schokolade");

        jbp.save(automat);

        Automat loadedAutomat = jbp.load();

        assertNotNull(loadedAutomat);
        assertEquals(2, loadedAutomat.auflistenKuchen().size());
    }

    @Test
    void testSaveMitMock() {
        // Act
        jbp.save(automatMock);

        // Assert
        // Verify that save method is called once
        verify(automatMock, times(1)).auflistenKuchen();
        verify(automatMock, times(1)).auflistenHersteller();
    }












}