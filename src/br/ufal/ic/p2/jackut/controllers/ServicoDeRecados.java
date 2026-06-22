package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.relacionamentos.FuncaoInvalidaInimigoException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.NaoPodeEnviarRecadoASiMesmoException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;

public class ServicoDeRecados {

    private RepositorioJackut repo;

    public ServicoDeRecados(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void enviarRecado(String idSessao, String destinatario, String recado) throws Exception {
        if (!repo.existeUsuario(destinatario)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (idSessao.equals(destinatario)) {
            throw new NaoPodeEnviarRecadoASiMesmoException();
        }

        repo.buscarUsuario(destinatario).receberRecado(recado);
        Usuario destinatariaObj = repo.buscarUsuario(destinatario);

        if (destinatariaObj.temComoInimigo(idSessao)) {
            throw new FuncaoInvalidaInimigoException(destinatariaObj.getNome());
        }
    }

    public String lerRecado(String idSessao) throws Exception {
        if (!repo.existeUsuario(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }
        return repo.buscarUsuario(idSessao).lerProximoRecado();
    }
}
