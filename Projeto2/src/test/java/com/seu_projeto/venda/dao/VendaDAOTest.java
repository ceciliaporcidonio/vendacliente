package com.seu_projeto.venda;

import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.produto.Produto;
import com.seu_projeto.venda.dao.VendaDAOMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VendaDAOTest {

    private VendaDAOMemoria vendaDAO;
    private Cliente cliente;
    private Produto produto;

    @BeforeEach
    void setUp() {
        vendaDAO = new VendaDAOMemoria(); // Instancia a VendaDAOMemoria diretamente
        cliente = new Cliente("Ana", "12345678900", "Rua A", "São Paulo", "SP");
        produto = new Produto("Notebook", 3500.00, "PROD001"); // Configurações reutilizadas
    }

    @Test
    void testCadastrarVendaDeveSalvarVenda() {
        // Criando um mapa para os produtos vendidos
        Map<Produto, Integer> produtosVendidos = new HashMap<>();
        produtosVendidos.put(produto, 2); // Adicionando produto e quantidade

        // Criando uma venda
        Venda venda = new Venda("NF123456", cliente, produtosVendidos); // Passando o mapa

        // Registrando a venda
        vendaDAO.registrar(venda);

        // Verificando se a venda foi cadastrada corretamente
        assertTrue(vendaDAO.buscarPorNumero(venda.getNumeroNotaFiscal()).isPresent(),
                "A venda deveria ter sido encontrada após o cadastro, mas não foi.");
    }

    @Test
    void testExcluirVendaDeveRemoverVenda() {
        // Criando um mapa para os produtos vendidos
        Map<Produto, Integer> produtosVendidos = new HashMap<>();
        produtosVendidos.put(produto, 2); // Adicionando produto e quantidade

        // Criando e registrando uma venda
        Venda venda = new Venda("NF123456", cliente, produtosVendidos);
        vendaDAO.registrar(venda);

        // Excluindo a venda
        vendaDAO.excluir(venda.getNumeroNotaFiscal());

        // Verificando se a venda foi removida corretamente
        assertFalse(vendaDAO.buscarPorNumero(venda.getNumeroNotaFiscal()).isPresent(),
                "A venda não deveria ser encontrada após a exclusão.");
    }
}
