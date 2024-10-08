package com.seu_projeto.cliente.dao;

import com.seu_projeto.cliente.Cliente;

import java.util.List;

public interface IClienteDAO {
    void cadastrar(Cliente cliente);

    Cliente consultar(String cpf); // Método para consultar

    void alterar(Cliente cliente);

    void excluir(String cpf);

    List<Cliente> listarTodos();
}
