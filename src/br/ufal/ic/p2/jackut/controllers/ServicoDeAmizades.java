package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.relacionamentos.FuncaoInvalidaInimigoException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Serviço responsável por gerenciar o ciclo de vida das amizades na rede social Jackut.
 * Isola a lógica complexa de convites pendentes, aceitação mútua e bloqueios de segurança,
 * mantendo o Princípio da Responsabilidade Única (SRP).
 */
public class ServicoDeAmizades {

    /** Repositório central contendo o estado atual do sistema em memória. */
    private RepositorioJackut repo;

    /**
     * Construtor do serviço de amizades.
     * @param repo O repositório injetado para acessar as entidades dos usuários.
     */
    public ServicoDeAmizades(RepositorioJackut repo) {
        this.repo = repo;
    }

    /**
     * Tenta adicionar um usuário como amigo.
     * O sistema funciona com convites mútuos: se o destinatário já havia enviado um convite,
     * a amizade é efetivada. Caso contrário, o convite fica pendente.
     * @param idSessao O identificador (login) do usuário remetente.
     * @param idAmigo O identificador (login) do usuário alvo.
     * @throws Exception Caso regras de negócio sejam violadas (auto-adição, já ser amigo, convite pendente repetido ou inimizade).
     */
    public void adicionarAmigo(String idSessao, String idAmigo) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(idAmigo)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (idSessao.equals(idAmigo)) {
            throw new AdicionarASiMesmoException();
        }

        Usuario remetente = repo.buscarUsuario(idSessao);
        Usuario destinatario = repo.buscarUsuario(idAmigo);

        if (destinatario.temComoInimigo(idSessao)) {
            throw new FuncaoInvalidaInimigoException(destinatario.getNome());
        }

        if (remetente.ehAmigo(idAmigo)) {
            throw new JaEhAmigoException();
        }

        if (destinatario.possuiConviteDe(idSessao)) {
            destinatario.aceitarConvite(idSessao);
            remetente.aceitarConvite(idAmigo);
        } else {
            if (remetente.possuiConviteDe(idAmigo)) {
                throw new ConvitePendenteException();
            }
            remetente.registrarConviteEnviado(idAmigo);
        }
    }

    /**
     * Verifica de forma simples se dois usuários já são amigos confirmados.
     * @param login O login do primeiro usuário.
     * @param amigo O login do segundo usuário a ser verificado.
     * @return true se a amizade existir e estiver confirmada, false caso contrário.
     */
    public boolean ehAmigo(String login, String amigo) {
        if (!repo.existeUsuario(login)) return false;
        return repo.buscarUsuario(login).ehAmigo(amigo);
    }

    /**
     * Recupera a lista completa de amigos confirmados de um usuário, formatada para exibição.
     * A responsabilidade de formatar a lista é delegada ao próprio modelo do Usuário.
     * @param login O identificador (login) do usuário.
     * @return String contendo os logins dos amigos formatados.
     * @throws Exception Caso o usuário consultado não exista no sistema.
     */
    public String getAmigos(String login) throws Exception {
        if (!repo.existeUsuario(login)) {
            throw new UsuarioNaoCadastradoException();
        }
        return repo.buscarUsuario(login).formatarListaDeAmigos();
    }
}
