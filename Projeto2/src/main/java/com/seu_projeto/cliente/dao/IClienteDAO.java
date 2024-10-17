package com.seu_projeto.cliente.dao;

import com.seu_projeto.cliente.Cliente;

import java.util.List;

public interface IClienteDAO {
    // Cadastra um novo cliente
    void cadastrar(Cliente cliente);

    // Consulta um cliente pelo CPF
    Cliente consultar(String cpf);

    // Altera as informações de um cliente
    void alterar(Cliente cliente);

    // Exclui um cliente pelo CPF
    void excluir(String cpf);

    // Lista todos os clientes
    List<Cliente> listarTodos();
}
