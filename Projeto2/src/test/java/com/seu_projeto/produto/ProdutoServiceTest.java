package com.seu_projeto.produto;

import com.seu_projeto.produto.dao.ProdutoDAOMemoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProdutoServiceTest {

    private ProdutoService produtoService;

    @BeforeEach
    public void setUp() {
        produtoService = new ProdutoService(new ProdutoDAOMemoria());
    }

    @Test
    public void testCadastrarProduto() {
        Produto produto = new Produto("Produto1", 100.0, "001");
        produtoService.cadastrarProduto(produto);

        Produto produtoBuscado = produtoService.buscarPorDescricao("Produto1");
        Assertions.assertNotNull(produtoBuscado);
        Assertions.assertEquals("Produto1", produtoBuscado.getDescricao());
        Assertions.assertEquals(100.0, produtoBuscado.getValorUnitario());
    }

    @Test
    public void testAlterarProduto() {
        Produto produto = new Produto("Produto1", 100.0, "001");
        produtoService.cadastrarProduto(produto);

        Produto produtoAlterado = new Produto("Produto1", 200.0, "001");
        produtoService.alterarProduto(produtoAlterado);

        Produto produtoBuscado = produtoService.buscarPorDescricao("Produto1");
        Assertions.assertNotNull(produtoBuscado);
        Assertions.assertEquals(200.0, produtoBuscado.getValorUnitario());
    }

    @Test
    public void testExcluirProduto() {
        Produto produto = new Produto("Produto1", 100.0, "001");
        produtoService.cadastrarProduto(produto);

        produtoService.excluirProduto("Produto1");
        Produto produtoBuscado = produtoService.buscarPorDescricao("Produto1");
        Assertions.assertNull(produtoBuscado);
    }

    @Test
    public void testListarTodosProdutos() {
        Produto produto1 = new Produto("Produto1", 100.0, "001");
        Produto produto2 = new Produto("Produto2", 200.0, "002");

        produtoService.cadastrarProduto(produto1);
        produtoService.cadastrarProduto(produto2);

        List<Produto> produtos = produtoService.listarTodosProdutos();
        Assertions.assertEquals(2, produtos.size());
    }
}
