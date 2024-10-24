package com.seu_projeto.venda.dao;

import com.seu_projeto.venda.Venda;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.cliente.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VendaDAOPostgres implements IVendaDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/lojaonline";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void registrar(Venda venda) {
        String sqlVenda = "INSERT INTO venda (numero_nota, cliente_id, valor_total) VALUES (?, ?, ?)";
        String sqlItemVenda = "INSERT INTO item_venda (venda_id, produto_id, quantidade, valor_total) VALUES (?, ?, ?, ?)";
        String sqlAtualizarEstoque = "UPDATE produto SET estoque = estoque - ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmtVenda = connection.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtItemVenda = connection.prepareStatement(sqlItemVenda);
             PreparedStatement stmtAtualizarEstoque = connection.prepareStatement(sqlAtualizarEstoque)) {

            stmtVenda.setInt(1, venda.getNumeroNotaFiscal());
            stmtVenda.setInt(2, venda.getCliente().getId());
            stmtVenda.setDouble(3, venda.getValorTotal());
            stmtVenda.executeUpdate();

            ResultSet rs = stmtVenda.getGeneratedKeys();
            if (rs.next()) {
                int vendaId = rs.getInt(1);

                for (Map.Entry<Produto, Integer> entry : venda.getProdutos().entrySet()) {
                    Produto produto = entry.getKey();
                    int quantidade = entry.getValue();
                    double valorTotalItem = produto.getValorUnitario() * quantidade;

                    stmtItemVenda.setInt(1, vendaId);
                    stmtItemVenda.setInt(2, produto.getId());
                    stmtItemVenda.setInt(3, quantidade);
                    stmtItemVenda.setDouble(4, valorTotalItem);
                    stmtItemVenda.executeUpdate();

                    stmtAtualizarEstoque.setInt(1, quantidade);
                    stmtAtualizarEstoque.setInt(2, produto.getId());
                    stmtAtualizarEstoque.executeUpdate();
                }
            }

            System.out.println("Venda cadastrada com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Venda> buscarPorNumero(String numeroNotaFiscal) {
        String sqlVenda = "SELECT * FROM venda WHERE numero_nota = ?";
        String sqlItensVenda = "SELECT iv.*, p.descricao, p.valor_unitario FROM item_venda iv JOIN produto p ON iv.produto_id = p.id WHERE iv.venda_id = ?";
        Venda venda = null;

        try (Connection connection = getConnection();
             PreparedStatement stmtVenda = connection.prepareStatement(sqlVenda);
             PreparedStatement stmtItensVenda = connection.prepareStatement(sqlItensVenda)) {

            stmtVenda.setString(1, numeroNotaFiscal);
            ResultSet rsVenda = stmtVenda.executeQuery();

            if (rsVenda.next()) {
                venda = new Venda(rsVenda.getInt("numero_nota"), new Cliente(rsVenda.getInt("cliente_id")));

                stmtItensVenda.setInt(1, rsVenda.getInt("id"));
                ResultSet rsItensVenda = stmtItensVenda.executeQuery();

                while (rsItensVenda.next()) {
                    // Usando o novo construtor de Produto que inclui o estoque como quantidade
                    Produto produto = new Produto(
                            rsItensVenda.getInt("produto_id"),
                            rsItensVenda.getString("descricao"),
                            rsItensVenda.getDouble("valor_unitario"),
                            rsItensVenda.getInt("quantidade"));

                    venda.adicionarProduto(produto, rsItensVenda.getInt("quantidade"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar venda: " + e.getMessage(), e);
        }
        return Optional.ofNullable(venda);
    }

    @Override
    public List<Venda> listarTodas() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        String sqlItensVenda = "SELECT iv.*, p.descricao, p.valor_unitario FROM item_venda iv JOIN produto p ON iv.produto_id = p.id WHERE iv.venda_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmtVenda = connection.prepareStatement(sql);
             PreparedStatement stmtItensVenda = connection.prepareStatement(sqlItensVenda);
             ResultSet rs = stmtVenda.executeQuery()) {

            while (rs.next()) {
                Venda venda = new Venda(rs.getInt("numero_nota"), new Cliente(rs.getInt("cliente_id")));

                stmtItensVenda.setInt(1, rs.getInt("id"));
                ResultSet rsItensVenda = stmtItensVenda.executeQuery();

                while (rsItensVenda.next()) {
                    // Usando o novo construtor de Produto que inclui o estoque como quantidade
                    Produto produto = new Produto(
                            rsItensVenda.getInt("produto_id"),
                            rsItensVenda.getString("descricao"),
                            rsItensVenda.getDouble("valor_unitario"),
                            rsItensVenda.getInt("quantidade"));

                    venda.adicionarProduto(produto, rsItensVenda.getInt("quantidade"));
                }
                vendas.add(venda);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage(), e);
        }
        return vendas;
    }
}
