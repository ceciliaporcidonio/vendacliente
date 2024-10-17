package com.seu_projeto.produto.dao;

import com.seu_projeto.produto.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOPostgres implements IProdutoDAO {

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
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produto (descricao, valor_unitario, codigo_produto) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValorUnitario());
            stmt.setString(3, produto.getCodigoProduto());
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    @Override
    public Produto buscarPorDescricao(String descricao) { // Método renomeado
        String sql = "SELECT * FROM produto WHERE descricao = ?";
        Produto produto = null;

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, descricao);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDouble("valor_unitario"),
                        rs.getString("codigo_produto")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto por descrição: " + e.getMessage());
        }

        return produto;
    }

    @Override
    public void alterar(Produto produto) {
        String sql = "UPDATE produto SET descricao = ?, valor_unitario = ? WHERE codigo_produto = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValorUnitario());
            stmt.setString(3, produto.getCodigoProduto());
            stmt.executeUpdate();
            System.out.println("Produto alterado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao alterar produto: " + e.getMessage());
        }
    }

    @Override
    public void excluir(String codigoProduto) { // Parâmetro alterado
        String sql = "DELETE FROM produto WHERE codigo_produto = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, codigoProduto);
            stmt.executeUpdate();
            System.out.println("Produto excluído com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
    }

    @Override
    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDouble("valor_unitario"),
                        rs.getString("codigo_produto")
                );
                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }
}
