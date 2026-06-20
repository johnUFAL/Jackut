package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.comunidades.ComunidadeNaoExiste;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastrado;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;

public class ServicoDeMensagens {

    private RepositorioJackut repo;

    public ServicoDeMensagens(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void enviarMensagem(String idSessao, String nomeComunidade, String mensagem) throws Exception {
        if (!repo.existeUsuario(idSessao)) {
            throw new UsuarioNaoCadastrado();
        }
        if (!repo.existeComunidade(nomeComunidade)) {
            throw new ComunidadeNaoExiste();
        }

        Comunidade c = repo.buscarComunidade(nomeComunidade);
        for (String idMembro : c.getMembros()) {
            Usuario membro = repo.buscarUsuario(idMembro);
            if (membro != null) {
                membro.receberMensagem(mensagem);
            }
        }
    }

    public String lerMensagem(String idSessao) throws Exception {
        if (!repo.existeUsuario(idSessao)) {
            throw new UsuarioNaoCadastrado();
        }
        return repo.buscarUsuario(idSessao).lerProximaMensagem();
    }
}
