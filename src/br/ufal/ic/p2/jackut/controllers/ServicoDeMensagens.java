package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.comunidades.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Serviço responsável por gerenciar o fluxo de mensagens públicas enviadas para comunidades.
 * Mantém o Princípio da Responsabilidade Única (SRP) ao separar a lógica de "broadcast"
 * (envio em massa para múltiplos membros) do envio de recados privados.
 */
public class ServicoDeMensagens {

    /** Repositório central contendo os dados em memória do sistema. */
    private RepositorioJackut repo;

    /**
     * Construtor do serviço de mensagens.
     * @param repo O repositório injetado para acesso às coleções de usuários e comunidades.
     */
    public ServicoDeMensagens(RepositorioJackut repo) {
        this.repo = repo;
    }

    /**
     * Realiza o envio de uma mensagem pública para uma comunidade.
     * O sistema itera sobre a lista de membros da comunidade e deposita uma cópia
     * da mensagem na fila individual de recebimento de cada usuário.
     * * @param idSessao O identificador (login) do usuário remetente.
     * @param nomeComunidade O nome da comunidade destino.
     * @param mensagem O conteúdo textual da mensagem a ser enviada.
     * @throws Exception Caso o usuário remetente não esteja cadastrado ou a comunidade não exista.
     */
    public void enviarMensagem(String idSessao, String nomeComunidade, String mensagem) throws Exception {
        if (!repo.existeUsuario(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (!repo.existeComunidade(nomeComunidade)) {
            throw new ComunidadeNaoExisteException();
        }

        Comunidade c = repo.buscarComunidade(nomeComunidade);

        for (String idMembro : c.getMembros()) {
            Usuario membro = repo.buscarUsuario(idMembro);
            if (membro != null) {
                membro.receberMensagem(idSessao, mensagem);
            }
        }
    }

    /**
     * Lê e consome a mensagem de comunidade mais antiga da fila de entrada do usuário.
     * O processamento obedece rigidamente à estrutura de fila FIFO
     * * encapsulada dentro da entidade Usuario.
     * * @param idSessao O identificador (login) da sessão do usuário que deseja ler a mensagem.
     * @return O conteúdo textual da mensagem consumida.
     * @throws Exception Caso o usuário não esteja cadastrado ou não haja mensagens pendentes.
     */
    public String lerMensagem(String idSessao) throws Exception {
        if (!repo.existeUsuario(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }
        // Delega o processamento da fila para o próprio modelo
        return repo.buscarUsuario(idSessao).lerProximaMensagem();
    }
}
