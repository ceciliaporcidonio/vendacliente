package com.seu_projeto.produto;

public class Produto {
    private int id; // ID do produto, geralmente gerado automaticamente no banco de dados
    private String descricao; // Descrição do produto
    private double valorUnitario; // Valor unitário do produto
    private String codigoProduto; // Código do produto (campo adicionado na tabela)

    // Construtor padrão
    public Produto() {
    }

    // Construtor com parâmetros
    public Produto(int id, String descricao, double valorUnitario, String codigoProduto) {
        this.id = id; // Inicializa o ID, pode ser opcional caso o ID seja gerado pelo banco
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.codigoProduto = codigoProduto;
    }

    // Getters e Setters
    public int getId() {
        return id; // Retorna o ID do produto
    }

    public void setId(int id) {
        this.id = id; // Define o ID do produto
    }

    public String getDescricao() {
        return descricao; // Retorna a descrição do produto
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao; // Define a descrição do produto
    }

    public double getValorUnitario() {
        return valorUnitario; // Retorna o valor unitário do produto
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario; // Define o valor unitário do produto
    }

    public String getCodigoProduto() {
        return codigoProduto; // Retorna o código do produto
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto; // Define o código do produto
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id + // Representa o ID do produto
                ", descricao='" + descricao + '\'' + // Representa a descrição do produto
                ", valorUnitario=" + valorUnitario + // Representa o valor unitário do produto
                ", codigoProduto='" + codigoProduto + '\'' + // Representa o código do produto
                '}'; // Finaliza a representação em string
    }
}
