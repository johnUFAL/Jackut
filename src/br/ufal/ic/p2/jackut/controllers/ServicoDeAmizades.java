package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;

public class ServicoDeAmizades {
    private RepositorioJackut repo;

    public ServicoDeAmizades(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void adicionarAmigo(String idSessao, String idAmigo) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(idAmigo)) {
            throw new UsuarioNaoCadastrado();
        }
        if (idSessao.equals(idAmigo)) {
            throw new AdicionarASiMesmo();
        }

        Usuario remetente = repo.buscarUsuario(idSessao);
        Usuario destinatario = repo.buscarUsuario(idAmigo);

        if (remetente.ehAmigo(idAmigo)) {
            throw new JaEhAmigo();
        }

        if (destinatario.possuiConviteDe(idSessao)) {
            destinatario.aceitarConvite(idSessao);
            remetente.aceitarConvite(idAmigo);
        } else {
            if (remetente.possuiConviteDe(idAmigo)) {
                throw new ConvitePendente();
            }
            remetente.registrarConviteEnviado(idAmigo);
        }
    }

    public boolean ehAmigo(String login, String amigo) {
        if (!repo.existeUsuario(login)) return false;
        return repo.buscarUsuario(login).ehAmigo(amigo);
    }

    public String getAmigos(String login) throws Exception {
        if (!repo.existeUsuario(login)) {
            throw new UsuarioNaoCadastrado();
        }
        return repo.buscarUsuario(login).formatarListaDeAmigos();
    }
}
