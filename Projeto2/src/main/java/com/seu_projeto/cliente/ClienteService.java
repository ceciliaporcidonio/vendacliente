package com.seu_projeto.cliente;

import com.seu_projeto.cliente.dao.IClienteDAO;

import java.util.List;

public class ClienteService {
    private IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteDAO.cadastrar(cliente);
    }

    public Cliente consultarPorCpf(String cpf) {
        return clienteDAO.consultar(cpf); // Usando 'consultar'
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
