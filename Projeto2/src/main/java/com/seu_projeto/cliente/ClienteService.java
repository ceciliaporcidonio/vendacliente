package com.seu_projeto.cliente;

import com.seu_projeto.cliente.dao.IClienteDAO;

import java.util.List;
import java.util.Optional;

public class ClienteService {
    private IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteDAO.cadastrar(cliente);
    }

    // Modificado para retornar Optional<Cliente>
    public Optional<Cliente> consultarPorCpf(String cpf) {
        return Optional.ofNullable(clienteDAO.consultar(cpf));  // Mantém Optional para lidar com ausência do cliente
    }

    public void alterarCliente(Cliente cliente) {
        clienteDAO.alterar(cliente);
    }

    public void excluirCliente(String cpf) {
        clienteDAO.excluir(cpf);
    }

    public List<Cliente> listarTodosClientes() {
        return clienteDAO.listarTodos();
    }
}
