package com.seu_projeto.produto;

public class Produto {
    private String descricao;
    private double valorUnitario;
    private String codigoProduto;

    public Produto(String descricao, double valorUnitario, String codigoProduto) {
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.codigoProduto = codigoProduto;
    }

    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "descricao='" + descricao + '\'' +
                ", valorUnitario=" + valorUnitario +
                ", codigoProduto='" + codigoProduto + '\'' +
                '}';
    }
}
