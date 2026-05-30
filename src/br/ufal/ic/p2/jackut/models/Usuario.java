package br.ufal.ic.p2.jackut.models;

public class Usuario {
    private String login;
    private String senha;
    private String nome;

    public Usuario() {}

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
    }

    public String getLogin() {return login;}
    public String getSenha() {return senha;}
    public String getNome() {return nome;}

    public void setLogin(String login) {this.login = login;}
    public void setSenha(String senha) {this.senha = senha;}
    public void setNome(String nome) {this.nome = nome;}
}
