package com.seu_projeto.cliente;

import java.util.Objects;

public class Cliente {
    private Integer id; // Alterado para Integer
    private String nome;
    private String cpf;
    private String cidade;
    private String endereco;
    private String estado;
    private String telefone;

    // Construtor completo
    public Cliente(Integer id, String nome, String cpf, String cidade, String endereco, String estado, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.endereco = endereco;
        this.estado = estado;
        this.telefone = telefone;
    }

    // Construtor com apenas o ID
    public Cliente(Integer id) {
        this.id = id;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cidade='" + cidade + '\'' +
                ", endereco='" + endereco + '\'' +
                ", estado='" + estado + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf); // Considerando CPF como único
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
