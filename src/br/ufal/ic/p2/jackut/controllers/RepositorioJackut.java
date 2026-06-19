package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.models.Usuario;

import java.util.LinkedHashMap;
import java.util.Map;

public class RepositorioJackut {
    private Map<String, Usuario> usuarios = new LinkedHashMap<>();

    public Map<String, Usuario> getUsuarios() {return usuarios;}
    public void setUsuarios(Map<String, Usuario> usuarios) {this.usuarios = usuarios;}

    public void zerar() {usuarios.clear();}

    public Usuario buscarUsuario(String login) {return usuarios.get(login);}
    public void salvarUsuario(Usuario u) {usuarios.put(u.getLogin(), u);}
    public boolean existeUsuario(String login) {return usuarios.containsKey(login);}
}
