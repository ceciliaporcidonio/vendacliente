package com.seu_projeto.venda;

import com.seu_projeto.produto.Produto;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private double valorTotal;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorTotal = calcularValorTotal();
    }

    private double calcularValorTotal() {
        return produto.getValorUnitario() * quantidade;
    }

    // Getters e Setters
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.valorTotal = calcularValorTotal(); // Atualiza o valor total se o produto mudar
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.valorTotal = calcularValorTotal(); // Atualiza o valor total se a quantidade mudar
    }

    public double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
