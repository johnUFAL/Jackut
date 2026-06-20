package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepositorioJackut {

    private Map<String, Usuario> usuarios;
    private Map<String, Comunidade> comunidades; // NOVO MAPA!

    public RepositorioJackut() {
        this.usuarios = new LinkedHashMap<>();
        this.comunidades = new LinkedHashMap<>();
    }

    public Map<String, Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Map<String, Usuario> usuarios) { this.usuarios = usuarios; }

    public Map<String, Comunidade> getComunidades() { return comunidades; }
    public void setComunidades(Map<String, Comunidade> comunidades) { this.comunidades = comunidades; }

    public void zerar() {
        this.usuarios.clear();
        this.comunidades.clear();
    }

    public boolean existeUsuario(String login) { return this.usuarios.containsKey(login); }
    public Usuario buscarUsuario(String login) { return this.usuarios.get(login); }
    public void salvarUsuario(Usuario u) { this.usuarios.put(u.getLogin(), u); }

    public boolean existeComunidade(String nome) { return this.comunidades.containsKey(nome); }
    public Comunidade buscarComunidade(String nome) { return this.comunidades.get(nome); }
    public void salvarComunidade(Comunidade c) { this.comunidades.put(c.getNome(), c); }
}
