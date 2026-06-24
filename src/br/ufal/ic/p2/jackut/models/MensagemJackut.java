package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * Representa uma mensagem ou recado que trafega pelo sistema Jackut.
 * Encapsula o conteúdo textual e o autor do envio, permitindo o rastreio
 * na remoção de contas).
 */
public class MensagemJackut implements Serializable {

    /** O login do usuário que enviou a mensagem. */
    private String remetente;

    /** O conteúdo textual enviaso. */
    private String texto;

    /**
     * Construtor padrão vazio, exigido pelo XMLDecoder.
     */
    public MensagemJackut() {}

    /**
     * Cria uma nova mensagem associando o autor ao conteúdo.
     * * @param remetente Login do autor da mensagem.
     * @param texto Conteúdo da mensagem.
     */
    public MensagemJackut(String remetente, String texto) {
        this.remetente = remetente;
        this.texto = texto;
    }

    public String getRemetente() { return remetente; }
    public void setRemetente(String remetente) { this.remetente = remetente; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
}
