package com.seu_projeto.produto.dao;

import com.seu_projeto.produto.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOPostgres implements IProdutoDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/lojaonline";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produto (descricao, valor_unitario, codigo_produto, estoque) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValorUnitario());
            stmt.setString(3, produto.getCodigoProduto());
            stmt.setInt(4, produto.getEstoque());
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public Produto buscarPorDescricao(String descricao) {
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
                        rs.getString("codigo_produto"),
                        rs.getInt("estoque")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: " + e.getMessage(), e);
        }

        return produto;
    }

    @Override
    public void alterar(Produto produto) {
        String sql = "UPDATE produto SET descricao = ?, valor_unitario = ?, estoque = ? WHERE codigo_produto = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValorUnitario());
            stmt.setInt(3, produto.getEstoque());
            stmt.setString(4, produto.getCodigoProduto());
            stmt.executeUpdate();
            System.out.println("Produto alterado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar produto: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(String codigoProduto) {
        String sql = "DELETE FROM produto WHERE codigo_produto = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, codigoProduto);
            stmt.executeUpdate();
            System.out.println("Produto excluído com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getDouble("valor_unitario"),
                        rs.getString("codigo_produto"),
                        rs.getInt("estoque")
                );
                produtos.add(produto);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        }

        return produtos;
    }
}
