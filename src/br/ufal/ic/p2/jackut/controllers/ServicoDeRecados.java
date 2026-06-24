package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.relacionamentos.FuncaoInvalidaInimigoException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.NaoPodeEnviarRecadoASiMesmoException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;

/**
 * Serviço responsável por gerenciar o envio e leitura de recados privados entre usuários.
 * Mantém o Princípio da Responsabilidade Única separando o fluxo de mensagens diretas
 * de outras interações do sistema (como mensagens de comunidade).
 */
public class ServicoDeRecados {

    /** Repositório central para acesso aos dados dos usuários. */
    private RepositorioJackut repo;

    /**
     * Construtor do serviço de recados.
     * @param repo O repositório contendo o estado atual do sistema.
     */
    public ServicoDeRecados(RepositorioJackut repo) {
        this.repo = repo;
    }

    /**
     * Envia um recado privado para a caixa de entrada de outro usuário.
     * Verifica regras de negócio como a existência do usuário, auto-envio
     * e o bloqueio de segurança por inimizade.
     * * @param idSessao O ID da sessão do usuário remetente (quem está enviando).
     * @param destinatario O login do usuário que receberá o recado.
     * @param recado O conteúdo textual da mensagem.
     * @throws Exception Caso o destinatário não exista, seja auto-envio ou haja bloqueio por inimizade.
     */
    public void enviarRecado(String idSessao, String destinatario, String recado) throws Exception {
        if (!repo.existeUsuario(destinatario)) {
            throw new UsuarioNaoCadastradoException();
        }
        if (idSessao.equals(destinatario)) {
            throw new NaoPodeEnviarRecadoASiMesmoException();
        }

        Usuario destinatarioObj = repo.buscarUsuario(destinatario);

        if (destinatarioObj.temComoInimigo(idSessao)) {
            throw new FuncaoInvalidaInimigoException(destinatarioObj.getNome());
        }

        destinatarioObj.receberRecado(idSessao, recado);
    }

    /**
     * lê e remove o recado mais antigo da caixa de entrada do usuário.
     * A leitura segue o padrão de fila FIFO.
     * * @param idSessao O ID da sessão do usuário autenticado que está lendo.
     * @return O conteúdo textual do recado.
     * @throws Exception Caso o usuário não exista ou a sua caixa de entrada esteja vazia.
     */
    public String lerRecado(String idSessao) throws Exception {
        if (!repo.existeUsuario(idSessao)) {
            throw new UsuarioNaoCadastradoException();
        }

        return repo.buscarUsuario(idSessao).lerProximoRecado();
    }
}
