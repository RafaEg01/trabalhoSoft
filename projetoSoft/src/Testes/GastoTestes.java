package Testes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dados.Gasto;
import dados.TipoGasto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class GastoTestes {

    private Gasto gasto;
    private LocalDate testeData;

    @BeforeEach
    void setUp() {
        testeData = LocalDate.of(2023, 10, 26);
        gasto = new Gasto("Aluguel", testeData, 1500.0f, TipoGasto.Outros, 1);
    }

    @Test
    void testCriarGasto() {
        assertNotNull(gasto);
        assertTrue(gasto.getId() >= 0); // Id deve estar automatico
        assertEquals("Aluguel", gasto.getNome());
        assertEquals(testeData, gasto.getData());
        assertEquals(1500.0f, gasto.getValor(), 0.001f); // Delta for float comparison
        assertEquals(TipoGasto.Outros, gasto.getTipogasto());
        assertEquals(1, gasto.getId_usuario());
    }

    @Test
    void testIdIncrementar() {
        int initialId = gasto.getId();
        Gasto outrogasto = new Gasto("Comida", LocalDate.now(), 50.0f, TipoGasto.Comida, 1);
        assertEquals(initialId + 1, outrogasto.getId());
    }

    @Test
    void testGettersAndSetters() {
        gasto.setNome("Conta de Luz");
        assertEquals("Conta de Luz", gasto.getNome());

        LocalDate novaData = LocalDate.of(2023, 11, 15);
        gasto.setData(novaData);
        assertEquals(novaData, gasto.getData());

        gasto.setValor(200.50f);
        assertEquals(200.50f, gasto.getValor(), 0.001f);

        gasto.setId_usuario(2);
        assertEquals(2, gasto.getId_usuario());
    }

    @Test
    void testToString() {
        String expectedToString = "\nGasto \nid = " + gasto.getId() + "\nnome = Aluguel,\ndata = 2023-10-26,\nvalor = 1500.0,\ntipogasto = Outros\n";
        assertEquals(expectedToString, gasto.toString());
    }
}