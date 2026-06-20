package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.comunidades.*;
import br.ufal.ic.p2.jackut.models.Comunidade;

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
}
