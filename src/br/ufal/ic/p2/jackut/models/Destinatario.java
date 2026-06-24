package br.ufal.ic.p2.jackut.models;

/**
 * Interface que define o contrato para qualquer entidade do sistema Jackut
 * que seja capaz de receber mensagens ou recados.
 * Garante o polimorfismo entre Usuários e Comunidades no envio de mensagens.
 */
public interface Destinatario {

    /**
     * Processa o recebimento de uma nova mensagem.
     * * @param remetente O login do usuário que enviou a mensagem.
     * @param recado O conteúdo textual da mensagem.
     * @throws Exception Caso ocorra algum erro no processamento do recebimento.
     */
    void receberRecado(String remetente, String recado) throws Exception;
}
