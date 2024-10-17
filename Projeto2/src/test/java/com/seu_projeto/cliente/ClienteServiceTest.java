package com.seu_projeto.cliente;

import com.seu_projeto.cliente.dao.IClienteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    private ClienteService clienteService;
    private IClienteDAO clienteDAOMock;

    @BeforeEach
    public void setUp() {
        clienteDAOMock = new ClienteDAOMock() {
            @Override
            public void cadastrarCliente(Cliente cliente) {

            }

            @Override
            public Cliente consultarPorCpf(String cpf) {
                return null;
            }

            @Override
            public void alterarCliente(Cliente cliente) {

            }

            @Override
            public void excluirCliente(String cpf) {

            }

            @Override
            public List<Cliente> listarClientes() {
                return List.of();
            }
        }; // Mock implementado
        clienteService = new ClienteService(clienteDAOMock);
    }

    @Test
    public void testCadastrarCliente() {
        Cliente cliente = new Cliente(5,"Ana", "12345678901", "Cidade", "Endereço", "Estado");
        clienteService.cadastrarCliente(cliente);

        Cliente clienteConsultado = clienteService.consultarPorCpf("12345678901");
        assertNotNull(clienteConsultado, "O cliente deveria ter sido cadastrado.");
        assertEquals("Ana", clienteConsultado.getNome(), "O nome do cliente cadastrado não está correto.");
    }

    @Test
    public void testConsultarCliente() {
        Cliente cliente = new Cliente(3, "Ana", "12345678901", "Cidade", "Endereço", "Estado");
        clienteService.cadastrarCliente(cliente);

        Cliente clienteConsultado = clienteService.consultarPorCpf("12345678901");
        assertNotNull(clienteConsultado, "O cliente deveria ser encontrado pelo CPF.");
        assertEquals("Ana", clienteConsultado.getNome(), "O nome do cliente consultado não está correto.");
    }

    @Test
    public void testAlterarCliente() {
        Cliente cliente = new Cliente(4,"Ana", "12345678901", "Cidade", "Endereço", "Estado");
        clienteService.cadastrarCliente(cliente);

        cliente.setNome("Ana Maria");
        cliente.setEndereco("Novo Endereço");
        clienteService.alterarCliente(cliente);

        Cliente clienteConsultado = clienteService.consultarPorCpf("12345678901");
        assertNotNull(clienteConsultado, "O cliente deveria ser encontrado após a alteração.");
        assertEquals("Ana Maria", clienteConsultado.getNome(), "O nome do cliente não foi alterado corretamente.");
        assertEquals("Novo Endereço", clienteConsultado.getEndereco(), "O endereço do cliente não foi alterado corretamente.");
    }

    @Test
    public void testExcluirCliente() {
        Cliente cliente = new Cliente(7,"Ana", "12345678901", "Cidade", "Endereço", "Estado");
        clienteService.cadastrarCliente(cliente);

        Cliente clienteAntesDaExclusao = clienteService.consultarPorCpf("12345678901");
        assertNotNull(clienteAntesDaExclusao, "O cliente deveria estar cadastrado antes da exclusão.");

        clienteService.excluirCliente("12345678901");

        Cliente clienteConsultado = clienteService.consultarPorCpf("12345678901");
        assertNull(clienteConsultado, "O cliente deveria ter sido excluído.");
    }

    // Mock de IClienteDAO para os testes
    private static abstract class ClienteDAOMock implements IClienteDAO {
        private final Map<String, Cliente> clientes = new HashMap<>();

        @Override
        public void cadastrar(Cliente cliente) {
            clientes.put(cliente.getCpf(), cliente);
        }

        @Override
        public Cliente consultar(String cpf) {
            return clientes.get(cpf);
        }

        @Override
        public void alterar(Cliente cliente) {
            clientes.put(cliente.getCpf(), cliente);
        }

        @Override
        public void excluir(String cpf) {
            clientes.remove(cpf);
        }

        @Override
        public List<Cliente> listarTodos() {
            return new ArrayList<>(clientes.values());
        }

        public abstract void cadastrarCliente(Cliente cliente);

        public abstract Cliente consultarPorCpf(String cpf);

        public abstract void alterarCliente(Cliente cliente);

        public abstract void excluirCliente(String cpf);

        public abstract List<Cliente> listarClientes();
    }
}
