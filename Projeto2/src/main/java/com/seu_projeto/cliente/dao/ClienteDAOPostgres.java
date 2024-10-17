package com.seu_projeto.cliente.dao;

import com.seu_projeto.cliente.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOPostgres implements IClienteDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/lojaonline";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO cliente (cpf, nome, cidade, endereco, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCidade());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getEstado());
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
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
                        rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar cliente: " + e.getMessage());
        }

        return cliente;
    }

    @Override
    public void alterar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, cidade = ?, endereco = ?, estado = ? WHERE cpf = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCidade());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getEstado());
            stmt.setString(5, cliente.getCpf());
            stmt.executeUpdate();
            System.out.println("Cliente alterado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar cliente: " + e.getMessage());
        }
    }

    @Override
    public void excluir(String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Cliente excluído com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("cidade"),
                        rs.getString("endereco"),
                        rs.getString("estado")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }

        return clientes;
    }
}
