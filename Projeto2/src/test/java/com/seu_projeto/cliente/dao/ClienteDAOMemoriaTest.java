package com.seu_projeto.cliente.dao;

import com.seu_projeto.cliente.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDAOMemoriaTest {

    private ClienteDAOMemoria clienteDAO;

    @BeforeEach
    void setUp() {
        clienteDAO = new ClienteDAOMemoria(); // Instância concreta da DAO
    }

    private Cliente criarClientePadrao() {
        return new Cliente(1, "Ana", "12345678900", "São Paulo", "Rua A", "SP"); // Ajustado para ordem correta
    }

    @Test
    void testCadastrarCliente() {
        Cliente cliente = criarClientePadrao();
        clienteDAO.cadastrar(cliente);

        Cliente encontrado = clienteDAO.consultar("12345678900");
        assertNotNull(encontrado, "O cliente deveria ter sido cadastrado e encontrado.");
        assertEquals(cliente.getNome(), encontrado.getNome(), "Os nomes devem ser iguais.");
    }

    @Test
    void testConsultarPorCpf() { // Renomeado para clareza
        Cliente cliente = criarClientePadrao();
        clienteDAO.cadastrar(cliente);

        Cliente encontrado = clienteDAO.consultar("12345678900");
        assertNotNull(encontrado, "O cliente deveria ser encontrado pelo CPF.");
        assertEquals(cliente.getNome(), encontrado.getNome(), "Os nomes devem ser iguais.");
    }

    @Test
    void testAlterarCliente() {
        Cliente cliente = criarClientePadrao();
        clienteDAO.cadastrar(cliente);

        // Usando o mesmo CPF do cliente existente
        Cliente novoCliente = new Cliente(1,"Ana Maria", "12345678900", "Rio de Janeiro", "Rua B", "RJ");
        clienteDAO.alterar(novoCliente);

        Cliente encontrado = clienteDAO.consultar("12345678900");
        assertEquals(novoCliente.getNome(), encontrado.getNome(), "O nome do cliente deveria ter sido alterado.");
        assertEquals(novoCliente.getEndereco(), encontrado.getEndereco(), "O endereço do cliente deveria ter sido alterado.");
    }

    @Test
    void testExcluirCliente() {
        Cliente cliente = criarClientePadrao();
        clienteDAO.cadastrar(cliente); // Certifique-se de adicionar o cliente antes de tentar excluí-lo

        clienteDAO.excluir("12345678900"); // Tente excluir o cliente

        // Aqui, você pode verificar se o cliente foi realmente excluído
        assertThrows(RuntimeException.class, () -> clienteDAO.consultar("12345678900"));
    }

    @Test
    void testListarTodos() {
        Cliente cliente1 = criarClientePadrao();
        Cliente cliente2 = new Cliente(2, "Bruno", "09876543211", "São Paulo", "Rua B", "SP");

        clienteDAO.cadastrar(cliente1);
        clienteDAO.cadastrar(cliente2);

        List<Cliente> clientes = clienteDAO.listarTodos();
        assertEquals(2, clientes.size(), "Deveriam existir 2 clientes cadastrados.");
        assertTrue(clientes.contains(cliente1), "A lista de clientes deveria conter o cliente 1.");
        assertTrue(clientes.contains(cliente2), "A lista de clientes deveria conter o cliente 2.");
    }
}
