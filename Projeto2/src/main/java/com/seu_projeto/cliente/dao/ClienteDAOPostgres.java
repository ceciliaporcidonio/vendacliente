package com.seu_projeto.cliente.dao;

import com.seu_projeto.cliente.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOPostgres implements IClienteDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/lojaonline";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO cliente (cpf, nome, cidade, endereco, estado, telefone) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCidade());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getEstado());
            stmt.setString(6, cliente.getTelefone());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cliente", e);
        }
    }

    @Override
    public Cliente consultar(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        Cliente cliente = null;

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cidade"),
                        rs.getString("endereco"),
                        rs.getString("estado"),
                        rs.getString("telefone")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar cliente", e);
        }

        return cliente;
    }

    @Override
    public void alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, cidade = ?, endereco = ?, estado = ?, telefone = ? WHERE cpf = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            if (consultar(cliente.getCpf()) == null) {
                throw new RuntimeException("Cliente não encontrado para o CPF: " + cliente.getCpf());
            }

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCidade());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getEstado());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getCpf());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar cliente", e);
        }
    }

    @Override
    public void excluir(String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente", e);
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cidade"),
                        rs.getString("endereco"),
                        rs.getString("estado"),
                        rs.getString("telefone")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes", e);
        }

        return clientes;
    }
}
