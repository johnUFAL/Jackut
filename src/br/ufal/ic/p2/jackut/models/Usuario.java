package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/**
 * Esta entidade representa um usuário do sistema Jackut, contendo todas as informações
 * e funcionalidades relacionadas ao perfil, amizades e mensagens.
 *
 * <p>A classe é serializável para permitir persistência dos dados.</p>
 */
public class Usuario implements Serializable {

    private String login;
    private String senha;
    private String nome;

    /** Mapa que armazena os atributos customizáveis do perfil do usuário. */
    private Map<String, String> perfil;

    /** Lista contendo os logins dos amigos confirmados do usuário. */
    private List<String> amigos;

    /** Lista contendo os logins dos usuários que o  usuário enviou um convite de amizade. */
    private List<String> convitesEnviados;

    /** Fila contendo os recados recebidos pelo usuário. */
    private List<String> recados;

    /**
     * Construtor padrão e vazio da classe Usuario.
     * Necessário para a correta serialização e desserialização utilizando o XMLDecoder.
     */
    public Usuario() {
        this.perfil = new LinkedHashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
        this.recados = new ArrayList<>();
    }

    /**
     * Constrói um novo usuário com os dados básicos de credenciais.
     *
     * @param login Identificador único do usuário.
     * @param senha Senha de acesso.
     * @param nome Nome de exibição do usuário.
     */
    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.perfil = new LinkedHashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
        this.recados = new ArrayList<>();
    }

    /**
     * Obtém o login do usuário.
     * @return String contendo o login.
     */
    public String getLogin() { return login; }

    /**
     * Obtém a senha do usuário.
     * @return String contendo a senha.
     */
    public String getSenha() { return senha; }

    /**
     * Obtém o nome de exibição do usuário.
     * @return String contendo o nome.
     */
    public String getNome() { return nome; }

    /**
     * Obtém o mapa de atributos do perfil.
     * @return Mapa (Map) de chave e valor com os dados do perfil.
     */
    public Map<String, String> getPerfil() { return perfil; }

    /**
     * Obtém a lista de amigos do usuário.
     * @return Lista (List) de Strings contendo os logins dos amigos.
     */
    public List<String> getAmigos() { return amigos; }

    /**
     * Obtém a lista de convites de amizade enviados.
     * @return Lista (List) de Strings contendo os logins dos convidados.
     */
    public List<String> getConvitesEnviados() { return convitesEnviados; }

    /**
     * Obtém a lista de recados recebidos.
     * @return Lista (List) de Strings contendo as mensagens.
     */
    public List<String> getRecados() { return recados; }

    /**
     * Define um novo login para o usuário.
     * @param login Novo identificador único.
     */
    public void setLogin(String login) { this.login = login; }

    /**
     * Define uma nova senha para o usuário.
     * @param senha Nova senha de acesso.
     */
    public void setSenha(String senha) { this.senha = senha; }

    /**
     * Define um novo nome de exibição para o usuário.
     * @param nome Novo nome.
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Substitui o mapa do perfil do usuário.
     * @param perfil Novo mapa contendo atributos e valores.
     */
    public void setPerfil(Map<String, String> perfil) { this.perfil = perfil; }

    /**
     * Substitui a lista de amigos do usuário.
     * @param amigos Nova lista de logins de amigos.
     */
    public void setAmigos(List<String> amigos) { this.amigos = amigos; }

    /**
     * Substitui a lista de convites enviados.
     * @param convitesEnviados Nova lista de convites pendentes.
     */
    public void setConvitesEnviados(List<String> convitesEnviados) { this.convitesEnviados = convitesEnviados; }

    /**
     * Substitui a lista de recados da caixa de entrada.
     * @param recados Nova lista de mensagens.
     */
    public void setRecados(List<String> recados) { this.recados = recados; }
}