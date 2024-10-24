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
        clienteMap.put(cliente.getCpf(), cliente);
    }

    @Override
    public Cliente consultar(String cpf) {
        validarCpf(cpf);
        Cliente cliente = clienteMap.get(cpf);
        if (cliente == null) {
            throw new RuntimeException("Cliente não encontrado para o CPF: " + cpf);
        }
        return cliente;
    }

    @Override
    public void alterar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        validarCpf(cliente.getCpf());

        if (!clienteMap.containsKey(cliente.getCpf())) {
            throw new RuntimeException("Cliente não encontrado para o CPF: " + cliente.getCpf());
        }

        // Atualiza o cliente no mapa
        clienteMap.put(cliente.getCpf(), cliente);
    }

    @Override
    public void excluir(String cpf) {
        validarCpf(cpf);
        if (!clienteMap.containsKey(cpf)) {
            throw new RuntimeException("Cliente não encontrado para o CPF: " + cpf);
        }
        clienteMap.remove(cpf);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteMap.values().stream().collect(Collectors.toList());
    }

    // Método auxiliar para validar CPF
    private void validarCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio");
        }
    }
}
