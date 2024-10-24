package com.seu_projeto.venda;

import com.seu_projeto.cliente.Cliente;
import com.seu_projeto.produto.Produto;

import java.util.HashMap;
import java.util.Map;

public class Venda {

    private int numeroNotaFiscal;
    private Cliente cliente;
    private Map<Produto, Integer> produtos;
    private double valorTotal;

    // Construtor que aceita numeroNotaFiscal e Cliente
    public Venda(int numeroNotaFiscal, Cliente cliente) {
        this.numeroNotaFiscal = numeroNotaFiscal;
        this.cliente = cliente;
        this.produtos = new HashMap<>();
    }

    // Construtor que aceita apenas Cliente (usado ao cadastrar)
    public Venda(Cliente cliente) {
        this.cliente = cliente;
        this.produtos = new HashMap<>();
    }

    public int getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        produtos.put(produto, quantidade);
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        valorTotal = produtos.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getValorUnitario() * entry.getValue())
                .sum();
    }

    public void gerarNotaFiscal() {
        System.out.println("Nota Fiscal: " + numeroNotaFiscal);
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Produtos:");
        produtos.forEach((produto, quantidade) -> {
            double total = produto.getValorUnitario() * quantidade;
            System.out.printf("Produto: %s | Quantidade: %d | Preço: %.2f | Total: %.2f%n",
                    produto.getDescricao(), quantidade, produto.getValorUnitario(), total);
        });
        System.out.println("Valor Total: " + valorTotal);
    }
}
