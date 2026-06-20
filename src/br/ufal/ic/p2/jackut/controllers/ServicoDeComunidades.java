package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.comunidades.*;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;

public class ServicoDeComunidades {

    private RepositorioJackut repo;

    public ServicoDeComunidades(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void criarComunidade(String idSessao, String nome, String descricao) throws Exception {
        if (repo.existeComunidade(nome)) {
            throw new ComunidadeJaExiste();
        }

        Comunidade c = new Comunidade(nome, descricao, idSessao);
        repo.salvarComunidade(c);

        repo.buscarUsuario(idSessao).entrarNaComunidade(nome);
    }

    public String getDescricaoComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExiste();
        return repo.buscarComunidade(nome).getDescricao();
    }

    public String getDonoComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExiste();
        return repo.buscarComunidade(nome).getDono();
    }

    public String getMembrosComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExiste();
        return repo.buscarComunidade(nome).formatarListaDeMembros();
    }

    public void adionarComunidade(String idSessao, String nome) throws Exception {
        if (!repo.existeUsuario(idSessao)) throw new UsuarioNaoCadastrado();
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExiste();

        Comunidade c = repo.buscarComunidade(nome);
        Usuario u = repo.buscarUsuario(idSessao);

        if (c.possuiMembro(idSessao)) {
            throw new UsuarioJaFazParteDaComunidade();
        }

        c.adicionarMembro(idSessao);
        u.entrarNaComunidade(nome);
    }

    public String getComunidades(String login) throws Exception {
        if (!repo.existeUsuario(login)) throw new UsuarioNaoCadastrado();

        return repo.buscarUsuario(login).formatarListaDeComunidades();
    }
}
