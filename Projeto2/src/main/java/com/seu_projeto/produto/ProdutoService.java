package com.seu_projeto.produto;

import com.seu_projeto.produto.dao.IProdutoDAO;

import java.util.List;

public class ProdutoService {
    private IProdutoDAO produtoDAO;

    public ProdutoService(IProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void cadastrarProduto(Produto produto) {
        produtoDAO.cadastrar(produto);
    }

    public Produto buscarPorDescricao(String descricao) {
        return produtoDAO.buscarPorDescricao(descricao);
    }

    public void alterarProduto(Produto produto) {
        produtoDAO.alterar(produto);
    }

    public void excluirProduto(String descricao) {
        produtoDAO.excluir(descricao);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoDAO.listarTodos();
    }
}
