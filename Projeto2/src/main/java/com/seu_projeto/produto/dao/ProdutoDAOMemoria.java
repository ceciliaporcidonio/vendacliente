package com.seu_projeto.produto.dao;

import com.seu_projeto.produto.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoDAOMemoria implements IProdutoDAO {
    private Map<String, Produto> bancoDeDados = new HashMap<>();

    @Override
    public void cadastrar(Produto produto) {
        bancoDeDados.put(produto.getDescricao(), produto);
    }

    @Override
    public Produto buscarPorDescricao(String descricao) {
        return bancoDeDados.get(descricao);
    }

    @Override
    public void alterar(Produto produto) {
        bancoDeDados.put(produto.getDescricao(), produto);
    }

    @Override
    public void excluir(String descricao) {
        bancoDeDados.remove(descricao);
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(bancoDeDados.values());
    }
}
