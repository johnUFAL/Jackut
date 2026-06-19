package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.usuarios.NaoPodeEnviarRecadoASiMesmo;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastrado;

public class ServicoDeRecados {

    private RepositorioJackut repo;

    public ServicoDeRecados(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void enviarRecado(String idSessao, String destinatario, String recado) throws Exception {
        if (!repo.existeUsuario(destinatario)) {
            throw new UsuarioNaoCadastrado();
        }
        if (idSessao.equals(destinatario)) {
            throw new NaoPodeEnviarRecadoASiMesmo();
        }

        repo.buscarUsuario(destinatario).receberRecado(recado);
    }

    public String lerRecado(String idSessao) throws Exception {
        if (!repo.existeUsuario(idSessao)) {
            throw new UsuarioNaoCadastrado();
        }
        return repo.buscarUsuario(idSessao).lerProximoRecado();
    }
}
