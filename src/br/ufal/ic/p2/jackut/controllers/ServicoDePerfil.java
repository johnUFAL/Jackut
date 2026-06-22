package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;

public class ServicoDePerfil {

    private RepositorioJackut repo;

    public ServicoDePerfil(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty()) throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidaException();
        if (repo.existeUsuario(login)) throw new ContaComNomeJaExisteException();

        Usuario u = new Usuario(login, senha, nome);
        repo.salvarUsuario(u);
    }

    public String abrirSessao(String login, String senha) throws Exception {
        if (!repo.existeUsuario(login)) throw new LoginSenhaInvalidaException();
        Usuario u = repo.buscarUsuario(login);
        if (!u.getSenha().equals(senha)) throw new LoginSenhaInvalidaException();

        return login;
    }

    public String getAtributoUsuario(String login, String atributo) throws Exception {
        if (!repo.existeUsuario(login)) throw new UsuarioNaoCadastradoException();
        Usuario u = repo.buscarUsuario(login);

        if (atributo.equals("nome")) {
            return u.getNome();
        }
        if (atributo.equals("login")) {
            return u.getLogin();
        }

        return u.getAtributoPerfil(atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        if (!repo.existeUsuario(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario u = repo.buscarUsuario(id);

        if (atributo.equals("nome")) {
            u.setNome(valor);
        } else if (atributo.equals("senha")) {
            u.setSenha(valor);
        } else {
            u.adicionarAtributoPerfil(atributo, valor);
        }
    }
}
