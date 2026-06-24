package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

/**
 * Classe abstrata base para os elementos principais da rede (Usuários e Comunidades).
 * Fornece a estrutura comum de identificação por nome e garante que todas as entidades
 * sejam serializáveis para persistência em disco e atuem como destinatários de mensagens.
 */
public abstract class EntidadeJackut implements Destinatario, Serializable {

    /** Nome de exibição ou título da entidade. */
    protected String nome;

    /**
     * Construtor padrão vazio, exigido pelo XMLDecoder para desserialização.
     */
    public EntidadeJackut() {}

    /**
     * Constrói uma nova entidade inicializando seu nome.
     * * @param nome O nome atribuído à entidade.
     */
    public EntidadeJackut(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
