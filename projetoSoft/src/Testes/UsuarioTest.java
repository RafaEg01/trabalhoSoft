package Testes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dados.Gasto;
import dados.TipoGasto;
import dados.Usuario;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.EnumMap;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("testeUsuario", "testeSenha", 30, "12345678900");
    }

    @Test
    void testCriarUsuario() {
        assertNotNull(usuario);
        assertEquals("testeUsuario", usuario.getLogin());
        assertEquals("testeSenha", usuario.getSenha());
        assertEquals(30, usuario.getIdade());
        assertEquals("12345678900", usuario.getCpf());
        assertNotNull(usuario.getContas());
    }

    @Test
    void testGettersSetters() {
        usuario.setLogin("testeUsuario");
        assertEquals("testeUsuario", usuario.getLogin());

        usuario.setSenha("testeSenha");
        assertEquals("testeSenha", usuario.getSenha());

        usuario.setIdade(25);
        assertEquals(25, usuario.getIdade());

        usuario.setCpf("00987654321");
        assertEquals("00987654321", usuario.getCpf());

        usuario.setId(10);
        assertEquals(10, usuario.getId());
    }

    @Test
    void testContasInicializar() {
        EnumMap<TipoGasto, LinkedList<Gasto>> contas = usuario.getContas();
        assertNotNull(contas);
        assertEquals(TipoGasto.values().length, contas.size()); // Todos os valores do enum tem que estar presentes
        for (TipoGasto tipo : TipoGasto.values()) {
            assertNotNull(contas.get(tipo));
            assertTrue(contas.get(tipo).isEmpty()); // deve ser listas vazias
        }
    }

    @Test
    void testToString() {
        String expectedToString = "\nUsuario \n[\nlogin = testeUsuario,\nsenha = testeSenha,\nidade = 30,\ncpf = 12345678900]";
        assertEquals(expectedToString, usuario.toString());
    }
}