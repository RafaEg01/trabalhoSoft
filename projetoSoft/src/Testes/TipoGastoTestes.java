package Testes;

import org.junit.jupiter.api.Test;

import dados.TipoGasto;

import static org.junit.jupiter.api.Assertions.*;

public class TipoGastoTestes {

    @Test
    void testEnumValoresComInts() {
        assertEquals(1, TipoGasto.Comida.getValor());
        assertEquals(2, TipoGasto.Lazer.getValor());
        assertEquals(3, TipoGasto.Educação.getValor());
        assertEquals(4, TipoGasto.Saúde.getValor());
        assertEquals(5, TipoGasto.Transporte.getValor());
        assertEquals(6, TipoGasto.Outros.getValor());
    }

    @Test
    void testInnValoresComEnum() {
        assertEquals(TipoGasto.Comida, TipoGasto.fromInt(1));
        assertEquals(TipoGasto.Lazer, TipoGasto.fromInt(2));
        assertEquals(TipoGasto.Educação, TipoGasto.fromInt(3));
        assertEquals(TipoGasto.Saúde, TipoGasto.fromInt(4));
        assertEquals(TipoGasto.Transporte, TipoGasto.fromInt(5));
        assertEquals(TipoGasto.Outros, TipoGasto.fromInt(6));
    }

  
}