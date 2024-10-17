package com.seu_projeto.venda;

import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.produto.Produto;

import java.util.HashMap;
import java.util.Map;

public class Venda {
    private String numeroNotaFiscal;
    private Cliente cliente;
    private Map<Produto, Integer> produtos;

    public Venda(String numeroNotaFiscal, Cliente cliente) {
        this.numeroNotaFiscal = numeroNotaFiscal;
        this.cliente = cliente;
        this.produtos = new HashMap<>(); // Inicializar produtos
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        // Se o produto já existir, incrementa a quantidade
        produtos.merge(produto, quantidade, Integer::sum);
    }

    public double calcularTotal() {
        return produtos.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getValorUnitario() * entry.getValue())
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nota Fiscal: ").append(numeroNotaFiscal).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("Produtos:\n");
        produtos.forEach((produto, quantidade) ->
                sb.append(produto.getDescricao()).append(" - Quantidade: ")
                        .append(quantidade).append(" - Total: ")
                        .append(produto.getValorUnitario() * quantidade).append("\n")
        );
        sb.append("Total da Venda: ").append(calcularTotal()).append("\n");
        return sb.toString();
    }
}
