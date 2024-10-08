package com.seu_projeto.cliente;

import java.util.Objects;

public class Cliente {
    private String nome;
    private String cpf;
    private String cidade;
    private String endereco;
    private String estado;

    // Construtor completo
    public Cliente(String nome, String cpf, String cidade, String endereco, String estado) {
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.endereco = endereco;
        this.estado = estado;
    }

    // Construtor vazio
    public Cliente() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cidade='" + cidade + '\'' +
                ", endereco='" + endereco + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf); // Considerando CPF como identificador único
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
