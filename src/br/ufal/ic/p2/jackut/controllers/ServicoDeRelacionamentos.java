package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.relacionamentos.*;
import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Serviço responsável por gerenciar os relacionamentos estendidos da rede social Jackut.
 * Centraliza as lógicas de Ídolos/Fãs, Paqueras e Inimigos, mantendo o Princípio
 * da Responsabilidade Única (SRP) ao separar essas interações das amizades comuns.
 */
public class ServicoDeRelacionamentos {

    /** Repositório central para busca e persistência dos usuários. */
    private RepositorioJackut repo;

    /**
     * Construtor do serviço de relacionamentos.
     * @param repo O repositório injetado contendo os dados do sistema em memória.
     */
    public ServicoDeRelacionamentos(RepositorioJackut repo) {
        this.repo = repo;
    }

    // =========================================================================
    // ÍDOLOS E FÃS
    // =========================================================================

    /**
     * Estabelece uma relação de fã e ídolo entre dois usuários.
     * Atualiza as listas de ambos os envolvidos, desde que não haja um bloqueio de inimizade.
     * * @param idSessao ID do usuário que está adicionando o ídolo
     * @param idolo Login do usuário que será o ídolo.
     * @throws Exception Se usuários não existirem, for auto-adição, já for ídolo ou houver inimizade.
     */
    public void adicionarIdolo(String idSessao, String idolo) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(idolo)) throw new UsuarioNaoCadastradoException();
        if (idSessao.equals(idolo)) throw new UsuarioNaoPodeSerFaDeSiMesmoException();

        Usuario u = repo.buscarUsuario(idSessao);
        Usuario alvo = repo.buscarUsuario(idolo);

        if (alvo.temComoInimigo(idSessao)) throw new FuncaoInvalidaInimigoException(alvo.getNome());
        if (u.getIdolos().contains(idolo)) throw new UsuarioJaAdicionadoComoIdoloException();

        u.adicionarIdolo(idolo);
        alvo.adicionarFa(idSessao);
    }

    /**
     * Verifica se existe uma relação onde o primeiro usuário é fã do segundo.
     * * @param login Login do possível fã.
     * @param idolo Login do possível ídolo.
     * @return true se a relação existir, false caso contrário ou se o usuário não existir.
     */
    public boolean ehFa(String login, String idolo) {
        if (!repo.existeUsuario(login)) return false;
        return repo.buscarUsuario(login).ehFa(idolo);
    }

    /**
     * Obtém a lista de fãs de um usuário formatada para exibição.
     * * @param login Login do usuário alvo.
     * @return String contendo os fãs formatados entre chaves.
     * @throws Exception Caso o usuário não esteja cadastrado.
     */
    public String getFas(String login) throws Exception {
        if (!repo.existeUsuario(login)) throw new UsuarioNaoCadastradoException();
        Usuario u = repo.buscarUsuario(login);
        return u.formatarLista(u.getFas());
    }

    // =========================================================================
    // PAQUERAS E MATCHES
    // =========================================================================

    /**
     * Adiciona um usuário como paquera de outro de forma privada.
     * Caso o alvo já tenha adicionado o remetente como paquera anteriormente,
     * o sistema identifica o "Match" e envia um recado automático para ambos.
     * * @param idSessao ID do usuário logado
     * @param paquera Login do alvo do flerte
     * @throws Exception Se houver autoadição, já for paquera, não existir ou houver inimizade.
     */
    public void adicionarPaquera(String idSessao, String paquera) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(paquera)) throw new UsuarioNaoCadastradoException();
        if (idSessao.equals(paquera)) throw new UsuarioNaoPodeSerPaqueraDeSiMesmoException();

        Usuario u = repo.buscarUsuario(idSessao);
        Usuario alvo = repo.buscarUsuario(paquera);

        if (alvo.temComoInimigo(idSessao)) throw new FuncaoInvalidaInimigoException(alvo.getNome());
        if (u.ehPaquera(paquera)) throw new UsuarioJaAdicionadoComoPaqueraException();

        u.adicionarPaquera(paquera);

        if (alvo.ehPaquera(idSessao)) {
            u.receberRecado("Jackut", alvo.getNome() + " \uFFFD seu paquera - Recado do Jackut.");
            alvo.receberRecado("Jackut", u.getNome() + " \uFFFD seu paquera - Recado do Jackut.");
        }
    }

    /**
     * Verifica se um usuário adicionou outro como paquera.
     * * @param idSessao Login do usuário que tomou a ação.
     * @param paquera Login do alvo verificado.
     * @return true se estiver na lista de paqueras, false caso contrário.
     */
    public boolean ehPaquera(String idSessao, String paquera) {
        if (!repo.existeUsuario(idSessao)) return false;
        return repo.buscarUsuario(idSessao).ehPaquera(paquera);
    }

    /**
     * Recupera a lista formatada de todos os usuários que o usuário logado adicionou como paquera.
     * * @param idSessao ID do usuário logado.
     * @return String formatada contendo os logins das paqueras.
     * @throws Exception Caso o usuário não esteja cadastrado.
     */
    public String getPaqueras(String idSessao) throws Exception {
        if (!repo.existeUsuario(idSessao)) throw  new UsuarioNaoCadastradoException();
        Usuario u = repo.buscarUsuario(idSessao);
        return u.formatarLista(u.getPaqueras());
    }

    // =========================================================================
    // INIMIGOS E BLOQUEIOS
    // =========================================================================

    /**
     * Adiciona um usuário à lista de inimigos, criando uma barreira (bloqueio)
     * que impedirá o inimigo de enviar recados, convites de amizade,
     * paqueras ou tornar-se fã do usuário que o bloqueou.
     * * @param idSessao ID do usuário que está aplicando o bloqueio.
     * @param inimigo Login do usuário que será bloqueado.
     * @throws Exception Em caso de auto-adição, se já for inimigo ou usuário inexistente.
     */
    public void adicionarInimigo(String idSessao, String inimigo) throws Exception {
        if (!repo.existeUsuario(idSessao) || !repo.existeUsuario(inimigo)) throw new UsuarioNaoCadastradoException();
        if (idSessao.equals(inimigo)) throw new UsuarioNaoPodeSerInimigoDeSiMesmoException();

        Usuario u = repo.buscarUsuario(idSessao);
        if (u.temComoInimigo(inimigo)) throw new UsuarioJaAdicionadoComoInimigoException();

        u.adicionarInimigo(inimigo);
    }
}
