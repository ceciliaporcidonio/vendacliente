package com.seu_projeto.produto;

public class Produto {
    private Integer id; // ID pode ser nulo
    private String descricao;
    private double valorUnitario;
    private String codigoProduto;
    private int estoque; // Pode representar a quantidade

    // Construtor padrão
    public Produto() {
    }

    // Construtor que inclui todos os parâmetros
    public Produto(Integer id, String descricao, double valorUnitario, String codigoProduto, int estoque) {
        this.id = id;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.codigoProduto = codigoProduto;
        this.estoque = estoque;
    }

    // Construtor simplificado para operações de venda, onde estoque é a quantidade do item vendido
    public Produto(Integer id, String descricao, double valorUnitario, int quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.estoque = quantidade; // Estoque pode ser interpretado como quantidade nesse caso
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valorUnitario=" + valorUnitario +
                ", codigoProduto='" + codigoProduto + '\'' +
                ", estoque=" + estoque +
                '}';
    }
}
