package br.ufal.ic.p2.jackut.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Usuario {
    private String login;
    private String senha;
    private String nome;
    private Map<String, String> perfil;

    public Usuario() {
        this.perfil = new LinkedHashMap<>();
    }

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.perfil = new LinkedHashMap<>();
    }

    public String getLogin() {return login;}
    public String getSenha() {return senha;}
    public String getNome() {return nome;}
    public Map<String, String> getPerfil() {return perfil;}

    public void setLogin(String login) {this.login = login;}
    public void setSenha(String senha) {this.senha = senha;}
    public void setNome(String nome) {this.nome = nome;}
    public void setPerfil(Map<String, String> perfil) {this.perfil = perfil;}
}
