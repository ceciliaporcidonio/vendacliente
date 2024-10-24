package com.seu_projeto.produto.dao;

import com.seu_projeto.produto.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoDAOMemoria implements IProdutoDAO {
    private Map<String, Produto> produtos = new HashMap<>();

    @Override
    public void cadastrar(Produto produto) {
        produtos.put(produto.getCodigoProduto(), produto);
        System.out.println("Produto cadastrado em memória com sucesso!");
    }

    @Override
    public Produto buscarPorDescricao(String descricao) {
        return produtos.values().stream()
                .filter(produto -> produto.getDescricao().equals(descricao))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void alterar(Produto produto) {
        if (!produtos.containsKey(produto.getCodigoProduto())) {
            throw new RuntimeException("Produto não encontrado: " + produto.getCodigoProduto());
        }
        produtos.put(produto.getCodigoProduto(), produto);
        System.out.println("Produto alterado em memória com sucesso!");
    }

    @Override
    public void excluir(String codigoProduto) {
        if (produtos.remove(codigoProduto) == null) {
            throw new RuntimeException("Produto com código " + codigoProduto + " não encontrado.");
        }
        System.out.println("Produto excluído em memória com sucesso!");
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }
}
