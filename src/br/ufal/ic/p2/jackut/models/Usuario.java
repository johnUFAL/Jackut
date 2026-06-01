package br.ufal.ic.p2.jackut.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class Usuario {
    private String login;
    private String senha;
    private String nome;
    private Map<String, String> perfil;
    private List<String> amigos;
    private List<String> convitesEnviados;

    public Usuario() {
        this.perfil = new LinkedHashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
    }

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.perfil = new LinkedHashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
    }

    public String getLogin() {return login;}
    public String getSenha() {return senha;}
    public String getNome() {return nome;}
    public Map<String, String> getPerfil() {return perfil;}
    public List<String> getAmigos() {return amigos;}
    public List<String> getConvitesEnviados() {return convitesEnviados;}

    public void setLogin(String login) {this.login = login;}
    public void setSenha(String senha) {this.senha = senha;}
    public void setNome(String nome) {this.nome = nome;}
    public void setPerfil(Map<String, String> perfil) {this.perfil = perfil;}
    public void setAmigos(List<String> amigos) {this.amigos = amigos;}
    public void setConvitesEnviados(List<String> convitesEnviados) {this.convitesEnviados = convitesEnviados;}
}
