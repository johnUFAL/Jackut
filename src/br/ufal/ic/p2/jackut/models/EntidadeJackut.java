package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

public abstract class EntidadeJackut implements Destinatario, Serializable{
    protected String nome;

    public EntidadeJackut() {}

    public EntidadeJackut(String nome) {
        this.nome = nome;
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
}
