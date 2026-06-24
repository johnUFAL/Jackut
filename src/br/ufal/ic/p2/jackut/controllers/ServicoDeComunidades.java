package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.comunidades.*;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Serviço responsável por gerir o ciclo de vida e as interações relacionadas com as comunidades do Jackut.
 * Isola a lógica de negócio de criação e associação de grupos, mantendo o sistema
 * em conformidade com o Princípio da Responsabilidade Única (SRP).
 */
public class ServicoDeComunidades {

    /** Repositório central para acesso aos dados partilhados (Utilizadores e Comunidades). */
    private RepositorioJackut repo;

    /**
     * Construtor do serviço de comunidades.
     * @param repo O repositório injetado contendo o estado atual do sistema na memória.
     */
    public ServicoDeComunidades(RepositorioJackut repo) {
        this.repo = repo;
    }

    /**
     * Cria uma nova comunidade no sistema e define automaticamente o criador como o seu dono
     * e primeiro membro, estabelecendo a relação bidirecional desde o início.
     * @param idSessao ID (login) do utilizador que está a criar a comunidade.
     * @param nome Nome único da comunidade.
     * @param descricao Texto descritivo sobre o propósito da comunidade.
     * @throws Exception Caso já exista uma comunidade com o mesmo nome.
     */
    public void criarComunidade(String idSessao, String nome, String descricao) throws Exception {
        if (repo.existeComunidade(nome)) {
            throw new ComunidadeJaExisteException();
        }

        Comunidade c = new Comunidade(nome, descricao, idSessao);
        repo.salvarComunidade(c);

        repo.buscarUsuario(idSessao).entrarNaComunidade(nome);
    }

    /**
     * Consulta a descrição de uma comunidade específica.
     * @param nome O nome da comunidade.
     * @return O conteúdo textual da descrição.
     * @throws Exception Caso a comunidade solicitada não exista.
     */
    public String getDescricaoComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExisteException();
        return repo.buscarComunidade(nome).getDescricao();
    }

    /**
     * Consulta o login do dono da comunidade.
     * @param nome O nome da comunidade.
     * @return O identificador único do dono.
     * @throws Exception Caso a comunidade solicitada não exista.
     */
    public String getDonoComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExisteException();
        return repo.buscarComunidade(nome).getDono();
    }

    /**
     * Obtém a lista de membros de uma comunidade, formatada para exibição.
     * A formatação e encapsulamento da lista são delegados ao modelo da Comunidade.
     * @param nome O nome da comunidade.
     * @return Uma String contendo os logins dos membros formatados (ex: "{membro1,membro2}").
     * @throws Exception Caso a comunidade solicitada não exista.
     */
    public String getMembrosComunidade(String nome) throws Exception {
        if (!repo.existeComunidade(nome)) throw new ComunidadeNaoExisteException();
        return repo.buscarComunidade(nome).formatarListaDeMembros();
    }

    /**
     * Adiciona um utilizador a uma comunidade já existente.
     * Assegura a integridade do relacionamento bidirecional: a comunidade ganha um membro
     * e o utilizador regista a comunidade na sua lista pessoal.
     * @param idSessao ID da sessão do utilizador que deseja entrar.
     * @param nome O nome da comunidade destino.
     * @throws Exception Se o utilizador não existir, a comunidade não existir ou o utilizador já for membro.
     */
    public void adicionarComunidade(String idSessao, String nome) throws Exception {
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

    /**
     * Obtém a lista de todas as comunidades em que o utilizador logado participa.
     * @param login O login do utilizador a consultar.
     * @return Uma String contendo os nomes das comunidades formatados.
     * @throws Exception Caso o utilizador especificado não esteja cadastrado.
     */
    public String getComunidades(String login) throws Exception {
        if (!repo.existeUsuario(login)) throw new UsuarioNaoCadastradoException();

        return repo.buscarUsuario(login).formatarListaDeComunidades();
    }
}
