package br.ufal.ic.p2.jackut.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma comunidade dentro do Jackut, onde múltiplos usuários podem
 * ingressar e interagir. As comunidades possuem um dono exclusivo e uma lista de membros.
 */
public class Comunidade extends EntidadeJackut {

    /** Texto que descreve o propósito da comunidade. */
    private String descricao;

    /** Login do usuário que criou e gerencia a comunidade. */
    private String dono;

    /** Lista contendo os logins de todos os usuários que participam da comunidade. */
    private List<String> membros;

    /**
     * Construtor padrão vazio para o XMLDecoder.
     * Inicializa a lista de membros para evitar NullPointerException durante a leitura.
     */
    public Comunidade() {
        super();
        this.membros = new ArrayList<>();
    }

    /**
     * Cria uma nova comunidade e automaticamente adiciona o criador como o primeiro membro.
     * * param nome Nome único da comunidade.
     * @param descricao Descrição da comunidade.
     * @param dono Login do usuário criador.
     */
    public Comunidade(String nome, String descricao, String dono) {
        super(nome);
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new ArrayList<>();
        this.membros.add(dono);
    }

    /**
     * Formata a lista de membros no padrão exigido pelos testes de aceitação.
     * * @return String contendo os membros formatados (ex: "{membro1,membro2}").
     */
    public String formatarListaDeMembros() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < this.membros.size(); i++) {
            sb.append(this.membros.get(i));
            if (i < this.membros.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Adiciona um novo usuário a lista de membros, evitando duplicações.
     * * @param login Login do usuário a ser adicionado
     */
    public void adicionarMembro(String login) {
        if (!this.membros.contains(login)) {
            this.membros.add(login);
        }
    }

    /**
     * Verifica se um usuário faz parde da comunidade.
     * * @param login Login do usuário.
     * @return true se o usuário for membro, false caso contrário.
     */
    public boolean possuiMembro(String login) {
        return this.membros.contains(login);
    }

    /**
     * Implementação vazia da interface Destinatario.
     * Comunidades atualmente delegam as mensagens diretamente aos seus membros,
     * não armazenando recados internamente.
     */
    @Override
    public void receberRecado(String remetente, String recado) throws Exception {}

    // GETTERS E SETTERS DO XMLDECODER
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDono() { return dono; }
    public void setDono(String dono) { this.dono = dono; }
    public List<String> getMembros() { return membros; }
    public void setMembros(List<String> membros) { this.membros = membros; }
}
