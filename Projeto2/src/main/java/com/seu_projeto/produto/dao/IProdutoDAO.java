package com.seu_projeto.produto.dao;

import com.seu_projeto.produto.Produto;

import java.util.List;

public interface IProdutoDAO {
    void cadastrar(Produto produto);

    Produto buscarPorDescricao(String descricao);

    void alterar(Produto produto);

    void excluir(String descricao);

    List<Produto> listarTodos();
}
