package com.example.testefirebase.Modelo;

public class Familia {
    private Integer _id;
    private String nome;
    private String cpf;
    private String endereco;
    public  Familia(){

    }

    @Override
    public String toString() {
        return "Familia" +
                "Id: " + _id +
                "Nome: " + nome + '\'' +
                "CPF: " + cpf + '\'' +
                "Endereco: " + endereco;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


}
