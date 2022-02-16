package com.example.storagee;

public class Model {
    String id,titulo,descricao,valor,quantidade;

    public Model(){

    }

    public Model(String id,String titulo,String descricao,String valor,String quantidade){
        this.id = id;
        this.descricao = descricao;
        this.titulo = titulo;
        this.quantidade = quantidade;
        this.valor = valor;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}
