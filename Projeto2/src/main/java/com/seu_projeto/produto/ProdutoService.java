package com.seu_projeto.produto;

import com.seu_projeto.produto.dao.IProdutoDAO;
import java.util.List;
import java.util.Optional;

public class ProdutoService {
    private IProdutoDAO produtoDAO;

    public ProdutoService(IProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void cadastrarProduto(Produto produto) {
        validarProduto(produto);
        produtoDAO.cadastrar(produto);
    }

    // Ajustado para retornar Optional<Produto>
    public Optional<Produto> buscarPorDescricao(String descricao) {
        Produto produto = produtoDAO.buscarPorDescricao(descricao);
        return Optional.ofNullable(produto);  // Retorna Optional<Produto>
    }

    public void alterarProduto(Produto produto) {
        validarProduto(produto);
        produtoDAO.alterar(produto);
    }

    public void excluirProduto(String codigoProduto) {
        produtoDAO.excluir(codigoProduto);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoDAO.listarTodos();
    }

    private void validarProduto(Produto produto) {
        if (produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do produto não pode ser vazia.");
        }
        if (produto.getValorUnitario() < 0) {
            throw new IllegalArgumentException("O valor unitário não pode ser negativo.");
        }
        if (produto.getEstoque() < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo.");
        }
    }
}
