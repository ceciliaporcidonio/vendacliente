package com.seu_projeto.cliente.dao;

import com.seu_projeto.cliente.Cliente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClienteDAOMemoria implements IClienteDAO {

    private Map<String, Cliente> clienteMap = new HashMap<>();

    @Override
    public void cadastrar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        clienteMap.put(cliente.getCpf(), cliente); // Adiciona ou atualiza o cliente no mapa
    }

    @Override
    public Cliente consultar(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        Cliente cliente = clienteMap.get(cpf);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado para o CPF: " + cpf);
        }
        return cliente; // Retorna o cliente com o CPF fornecido
    }

    @Override
    public void alterar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        if (!clienteMap.containsKey(cliente.getCpf())) {
            throw new RuntimeException("Cliente não encontrado para o CPF: " + cliente.getCpf());
        }
        clienteMap.put(cliente.getCpf(), cliente); // Substitui o cliente existente no mapa
    }

    @Override
    public void excluir(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
        if (!clienteMap.containsKey(cpf)) {
            throw new RuntimeException("Cliente não encontrado para o CPF: " + cpf);
        }
        clienteMap.remove(cpf); // Remove o cliente pelo CPF
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteMap.values().stream().collect(Collectors.toList()); // Retorna uma lista de todos os clientes
    }
}
