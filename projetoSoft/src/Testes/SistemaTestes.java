package Testes;

import dados.Gasto;
import dados.TipoGasto;
import dados.Usuario;
import sistema.Sistema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

public class SistemaTestes {

    private Sistema sistema;

    // reseta o singleton
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field instanceField = Sistema.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null); // Coloca nulo, para forçar criação de uma nova
        sistema = Sistema.getInstance();
    }

    @Test
    void testSingletonInstance() {
        Sistema anotherSistema = Sistema.getInstance();
        assertSame(sistema, anotherSistema);
    }

    @Test
    void testCadastrarUsuarioSuccess() {
        Usuario u1 = new Usuario("Usuario", "senha1", "20", "111");
        assertTrue(sistema.cadastrarUsuario(u1));
        sistema.setUsuarios(u1); 
        assertTrue(sistema.getUsuarios().contains(u1));
        assertEquals(1, sistema.getUsuarios().size());
    }

    @Test
    void testCadastrarUsuarioDuplicateLogin() {
        Usuario u1 = new Usuario("NomeIgual", "senha1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        Usuario u2 = new Usuario("NomeIgual", "senha2", "25", "222");
        assertFalse(sistema.cadastrarUsuario(u2)); // Login duplicado
        assertEquals(1, sistema.getUsuarios().size()); // Existir só 1 usuario
    }

    @Test
    void testCadastrarUsuarioNullFalha() {
        assertFalse(sistema.cadastrarUsuario(null));
        assertTrue(sistema.getUsuarios().isEmpty());
    }

    @Test
    void testLoginUsuarioSuccess() {
        Usuario u1 = new Usuario("NomeLogin", "senha1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        Usuario loggedInUser = sistema.loginUsuario("NomeLogin", "senha1");
        assertNotNull(loggedInUser);
        assertEquals(u1, loggedInUser);
        assertSame(u1, sistema.getLogadoAtual());
    }

    @Test
    void testLoginUsuarioSenhaErrada() {
        Usuario u1 = new Usuario("NomeLogin", "pass1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        assertNull(sistema.loginUsuario("NomeLogin", "senha"));
        assertNull(sistema.getLogadoAtual());
    }

    @Test
    void testLoginUsuarioNonExisteUsuario() {
        assertNull(sistema.loginUsuario("naoexisto", "senha"));
        assertNull(sistema.getLogadoAtual());
    }

    @Test
    void testLoginUsuarioNull() {
        assertNull(sistema.loginUsuario(null, "senha"));
        assertNull(sistema.loginUsuario("usuario", null));
        assertNull(sistema.loginUsuario(null, null));
        assertNull(sistema.getLogadoAtual());
    }

    @Test
    void testLogout() {
        Usuario u1 = new Usuario("Usuario", "senha1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);
        sistema.loginUsuario("Usuario", "senha1");
        assertNotNull(sistema.getLogadoAtual());

        sistema.logout();
        assertNull(sistema.getLogadoAtual());
    }

    @Test
    void testCadastrarGasto() {
        Usuario u1 = new Usuario("Usuario", "senha1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1); 

        Gasto g1 = new Gasto("Cinema", LocalDate.now(), 50.0f, TipoGasto.Lazer, u1.getId());
        assertTrue(sistema.cadastrarGasto(u1, g1));
        assertTrue(u1.getContas().get(TipoGasto.Lazer).contains(g1));
        assertEquals(1, u1.getContas().get(TipoGasto.Lazer).size());
    }

    @Test
    void testCadastrarGastoNullUser() {
        Gasto g1 = new Gasto("Cinema", LocalDate.now(), 50.0f, TipoGasto.Lazer, 0);
        assertFalse(sistema.cadastrarGasto(null, g1));
    }

    @Test
    void testCadastrarGastoNullGasto() {
        Usuario u1 = new Usuario("Usuario", "senha1", "20", "111");
        assertFalse(sistema.cadastrarGasto(u1, null));
    }

    @Test
    void testVisualizarGastoMultiploTipo() {
        Usuario u1 = new Usuario("Usuario", "senha1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        Gasto g1 = new Gasto("Almoço", LocalDate.now(), 30.0f, TipoGasto.Comida, u1.getId());
        Gasto g2 = new Gasto("Show", LocalDate.now(), 100.0f, TipoGasto.Lazer, u1.getId());
        Gasto g3 = new Gasto("Livro", LocalDate.now(), 75.0f, TipoGasto.Educação, u1.getId());

        sistema.cadastrarGasto(u1, g1);
        sistema.cadastrarGasto(u1, g2);
        sistema.cadastrarGasto(u1, g3);

        LinkedList<Gasto> allGastos = sistema.visualizarGasto(u1);
        assertNotNull(allGastos);
        assertEquals(3, allGastos.size());
        assertTrue(allGastos.contains(g1));
        assertTrue(allGastos.contains(g2));
        assertTrue(allGastos.contains(g3));
    }

    @Test
    void testVisualizarGastoNoGastos() {
        Usuario u1 = new Usuario("Usuario", "senha1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        LinkedList<Gasto> allGastos = sistema.visualizarGasto(u1);
        assertNotNull(allGastos);
        assertTrue(allGastos.isEmpty());
    }

    @Test
    void testRemoveGastoSuccess() {
        Usuario u1 = new Usuario("user1", "pass1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        Gasto g1 = new Gasto("Remover este", LocalDate.now(), 50.0f, TipoGasto.Comida, u1.getId());
        sistema.cadastrarGasto(u1, g1);
        assertEquals(1, u1.getContas().get(TipoGasto.Comida).size());

        sistema.removeGasto(u1, g1);
        assertTrue(u1.getContas().get(TipoGasto.Comida).isEmpty());
    }

    @Test
    void testRemoveGastoNonExistentGasto() {
        Usuario u1 = new Usuario("user1", "pass1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        Gasto g1 = new Gasto("Existente", LocalDate.now(), 50.0f, TipoGasto.Comida, u1.getId());
        sistema.cadastrarGasto(u1, g1);

        Gasto g2 = new Gasto("Nao existe", LocalDate.now(), 60.0f, TipoGasto.Lazer, u1.getId());

        assertEquals(1, u1.getContas().get(TipoGasto.Comida).size());
        assertTrue(u1.getContas().get(TipoGasto.Lazer).isEmpty());

        sistema.removeGasto(u1, g2); 
        assertEquals(1, u1.getContas().get(TipoGasto.Comida).size());
    }

    @Test
    void testBuscarGastoPorIdFound() {
        Usuario u1 = new Usuario("user1", "pass1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        Gasto g1 = new Gasto("Gasto A", LocalDate.now(), 10.0f, TipoGasto.Comida, u1.getId());
        Gasto g2 = new Gasto("Gasto B", LocalDate.now(), 20.0f, TipoGasto.Lazer, u1.getId());
        sistema.cadastrarGasto(u1, g1);
        sistema.cadastrarGasto(u1, g2);

        Gasto foundGasto = sistema.buscarGastoPorId(u1.getContas(), g1.getId());
        assertNotNull(foundGasto);
        assertEquals(g1.getId(), foundGasto.getId());
        assertEquals(g1.getNome(), foundGasto.getNome());
    }

    @Test
    void testBuscarGastoPorIdNotFound() {
        Usuario u1 = new Usuario("user1", "pass1", "20", "111");
        sistema.cadastrarUsuario(u1);
        sistema.setUsuarios(u1);

        Gasto g1 = new Gasto("Gasto A", LocalDate.now(), 10.0f, TipoGasto.Comida, u1.getId());
        sistema.cadastrarGasto(u1, g1);

        assertNull(sistema.buscarGastoPorId(u1.getContas(), 999)); 
    }
}