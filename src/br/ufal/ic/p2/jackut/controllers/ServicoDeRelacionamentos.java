package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.relacionamentos.*;
import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;

public class ServicoDeRelacionamentos {
    private RepositorioJackut repo;

    public ServicoDeRelacionamentos(RepositorioJackut repo) {
        this.repo = repo;
    }

    public void adicionarIdolo(String idSessao, String idolo) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(idolo)) throw new UsuarioNaoCadastradoException();
        if (idSessao.equals(idolo)) throw new UsuarioNaoPodeSerFaDeSiMesmoException();

        Usuario u = repo.buscarUsuario(idSessao);
        Usuario alvo = repo.buscarUsuario(idolo);

        if (alvo.temComoInimigo(idSessao)) throw new FuncaoInvalidaInimigoException(alvo.getNome());
        if (u.getIdolos().contains(idolo)) throw new UsuarioJaAdicionadoComoIdoloException();

        u.adicionarIdolo(idolo);
        alvo.adionarFa(idSessao);
    }

    public boolean ehFa(String login, String idolo) {
        if (!repo.existeUsuario(login)) return false;
        return repo.buscarUsuario(login).ehFa(idolo);
    }

    public String getFas(String login) throws Exception {
        if (!repo.existeUsuario(login)) throw new UsuarioNaoCadastradoException();
        Usuario u = repo.buscarUsuario(login);
        return u.formatarLista(u.getFas());
    }

    public void adicionarPaquera(String idSessao, String paquera) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(paquera)) throw new UsuarioNaoCadastradoException();
        if (idSessao.equals(paquera)) throw new UsuarioNaoPodeSerPaqueraDeSiMesmoException();

        Usuario u = repo.buscarUsuario(idSessao);
        Usuario alvo = repo.buscarUsuario(paquera);

        if (alvo.temComoInimigo(idSessao)) throw new FuncaoInvalidaInimigoException(alvo.getNome());
        if (u.ehPaquera(paquera)) throw new UsuarioJaAdicionadoComoPaqueraException();

        u.adicionarPaquera(paquera);

        if (alvo.ehPaquera(idSessao)) {
            u.receberRecado(alvo.getNome() + " \uFFFD seu paquera - Recado do Jackut.");
            alvo.receberRecado(u.getNome() + " \uFFFD seu paquera - Recado do Jackut.");
        }
    }

    public boolean ehPaquera(String idSessao, String paquera) {
        if (!repo.existeUsuario(idSessao)) return false;
        return repo.buscarUsuario(idSessao).ehPaquera(paquera);
    }

    public String getPaqueras(String idSessao) throws Exception {
        if (!repo.existeUsuario(idSessao)) throw  new UsuarioNaoCadastradoException();
        Usuario u = repo.buscarUsuario(idSessao);
        return  u.formatarLista(u.getPaqueras());
    }

    public void adicionarInimigo(String idSessao, String inimigo) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(inimigo)) throw new UsuarioNaoCadastradoException();
        if (idSessao.equals(inimigo)) throw new UsuarioNaoPodeSerInimigoDeSiMesmoException();

        Usuario u = repo.buscarUsuario(idSessao);
        if (u.temComoInimigo(inimigo)) throw new UsuarioJaAdicionadoComoInimigoException();

        u.adionarInimigo(inimigo);
    }
}
