package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.comunidades.*;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;

public class ServicoDeComunidades {

    private RepositorioJackut repo;

    public ServicoDeComunidades(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void criarComunidade(String idSessao, String nome, String descricao) throws Exception {
        if (repo.existeComunidade(nome)) {
            throw new ComunidadeJaExisteException();
        }

        Comunidade c = new Comunidade(nome, descricao, idSessao);
        repo.salvarComunidade(c);

        repo.buscarUsuario(idSessao).entrarNaComunidade(nome);
    }

    public String getDescricaoComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExisteException();
        return repo.buscarComunidade(nome).getDescricao();
    }

    public String getDonoComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExisteException();
        return repo.buscarComunidade(nome).getDono();
    }

    public String getMembrosComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExisteException();
        return repo.buscarComunidade(nome).formatarListaDeMembros();
    }

    public void adionarComunidade(String idSessao, String nome) throws Exception {
        if (!repo.existeUsuario(idSessao)) throw new UsuarioNaoCadastradoException();
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExisteException();

        Comunidade c = repo.buscarComunidade(nome);
        Usuario u = repo.buscarUsuario(idSessao);

        if (c.possuiMembro(idSessao)) {
            throw new UsuarioJaFazParteDaComunidadeException();
        }

        c.adicionarMembro(idSessao);
        u.entrarNaComunidade(nome);
    }

    public String getComunidades(String login) throws Exception {
        if (!repo.existeUsuario(login)) throw new UsuarioNaoCadastradoException();

        return repo.buscarUsuario(login).formatarListaDeComunidades();
    }
}
