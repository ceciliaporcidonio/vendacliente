package com.seu_projeto.venda.dao;

import com.seu_projeto.venda.Venda;
import com.seu_projeto.venda.ItemVenda;
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

    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void registrar(Venda venda) {
        String sqlVenda = "INSERT INTO venda (numero_nota, cliente_id, valor_total) VALUES (?, ?, ?)";
        String sqlItemVenda = "INSERT INTO item_venda (venda_id, produto_id, quantidade) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmtVenda = connection.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtItemVenda = connection.prepareStatement(sqlItemVenda)) {

            // Cadastrar a venda
            stmtVenda.setInt(1, Integer.parseInt(venda.getNumeroNotaFiscal())); // Conversão para int
            stmtVenda.setInt(2, venda.getCliente().getId()); // Supondo que Cliente tenha um campo ID
            stmtVenda.setDouble(3, venda.calcularTotal());
            stmtVenda.executeUpdate();

            // Obter o ID da venda gerado
            ResultSet rs = stmtVenda.getGeneratedKeys();
            int vendaId = 0;
            if (rs.next()) {
                vendaId = rs.getInt(1);
            }

            // Cadastrar os itens da venda
            for (Map.Entry<Produto, Integer> entry : venda.getProdutos().entrySet()) {
                Produto produto = entry.getKey();
                int quantidade = entry.getValue();

                stmtItemVenda.setInt(1, vendaId);
                stmtItemVenda.setInt(2, produto.getId()); // Supondo que Produto tenha um campo ID
                stmtItemVenda.setInt(3, quantidade);
                stmtItemVenda.executeUpdate();
            }

            System.out.println("Venda cadastrada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar venda: " + e.getMessage());
        }
    }

    @Override
    public Optional<Venda> buscarPorNumero(String numeroNotaFiscal) {
        String sqlVenda = "SELECT * FROM venda WHERE numero_nota = ?";
        String sqlItensVenda = "SELECT * FROM item_venda WHERE venda_id = ?";
        Venda venda = null;

        try (Connection connection = getConnection();
             PreparedStatement stmtVenda = connection.prepareStatement(sqlVenda);
             PreparedStatement stmtItensVenda = connection.prepareStatement(sqlItensVenda)) {

            // Converter número da nota fiscal para int
            int numeroNotaFiscalInt = Integer.parseInt(numeroNotaFiscal);

            // Consultar a venda
            stmtVenda.setInt(1, numeroNotaFiscalInt);
            ResultSet rsVenda = stmtVenda.executeQuery();

            if (rsVenda.next()) {
                int vendaId = rsVenda.getInt("numero_nota");
                int clienteId = rsVenda.getInt("cliente_id");
                Cliente cliente = new Cliente(); // Criar o objeto Cliente
                cliente.setId(clienteId); // Supondo que Cliente tenha um método setId

                venda = new Venda(numeroNotaFiscal, cliente);

                // Consultar os itens da venda
                stmtItensVenda.setInt(1, vendaId);
                ResultSet rsItens = stmtItensVenda.executeQuery();

                while (rsItens.next()) {
                    int produtoId = rsItens.getInt("produto_id");
                    Produto produto = new Produto(); // Criar o objeto Produto
                    produto.setId(produtoId); // Supondo que Produto tenha um método setId

                    int quantidade = rsItens.getInt("quantidade");
                    venda.adicionarProduto(produto, quantidade);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar venda: " + e.getMessage());
        }

        return Optional.ofNullable(venda);
    }

    @Override
    public void excluir(String numeroNotaFiscal) {
        String sqlVenda = "DELETE FROM venda WHERE numero_nota = ?";
        String sqlItensVenda = "DELETE FROM item_venda WHERE venda_id = (SELECT numero_nota FROM venda WHERE numero_nota = ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmtItensVenda = connection.prepareStatement(sqlItensVenda);
             PreparedStatement stmtVenda = connection.prepareStatement(sqlVenda)) {

            // Converter número da nota fiscal para int
            int numeroNotaFiscalInt = Integer.parseInt(numeroNotaFiscal);

            // Deletar os itens da venda
            stmtItensVenda.setInt(1, numeroNotaFiscalInt);
            stmtItensVenda.executeUpdate();

            // Deletar a venda
            stmtVenda.setInt(1, numeroNotaFiscalInt);
            stmtVenda.executeUpdate();

            System.out.println("Venda excluída com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir venda: " + e.getMessage());
        }
    }

    @Override
    public List<Venda> listarTodas() {
        String sqlVenda = "SELECT * FROM venda";
        List<Venda> vendas = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement stmtVenda = connection.createStatement();
             ResultSet rsVenda = stmtVenda.executeQuery(sqlVenda)) {

            while (rsVenda.next()) {
                int numeroNota = rsVenda.getInt("numero_nota");
                int clienteId = rsVenda.getInt("cliente_id");
                Cliente cliente = new Cliente(); // Criar o objeto Cliente
                cliente.setId(clienteId); // Supondo que Cliente tenha um método setId

                Venda venda = new Venda(String.valueOf(numeroNota), cliente);
                vendas.add(venda);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        }

        return vendas;
    }
}
