package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControladorDeUsuarios {
    private  Map<String, Usuario> usuarios;

    public ControladorDeUsuarios() {
        this.usuarios = new LinkedHashMap<>();
    }

    public Map<String, Usuario> getUsuarios() {return  usuarios;}
    public void setUsuarios(Map<String, Usuario> usuarios) {this.usuarios = usuarios;}

    public void zerar() {
        this.usuarios.clear();
    }

    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty()) throw  new LoginInvalido();
        if (senha == null || senha.trim().isEmpty()) throw  new SenhaInvalida();
        if (this.usuarios.containsKey(login)) throw new ContaComNomeJaExiste();

        Usuario u = new Usuario(login, senha, nome);
        this.usuarios.put(login, u);
    }

    public String getAtributoUsuario(String login, String atributos) throws Exception {
        if (!this.usuarios.containsKey(login)) throw new UsuarioNaoCadastrado();
        Usuario u = this.usuarios.get(login);

        if (atributos.equals("nome")) {
            return u.getNome();
        }
        throw  new Exception("Atributo inválido");

    }

    public String abrirSessap(String login, String senha) throws Exception {
        if (!this.usuarios.containsKey(login)) throw new LoginSenhaInvalida();
        Usuario u = this.usuarios.get(login);
        if (!u.getSenha().equals(senha)) throw new LoginSenhaInvalida();

        return login;
    }
}
