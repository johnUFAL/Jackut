package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Repositório central do sistema Jackut.
 * Funciona como um banco de dados em memória, armazenando as coleções de Usuários e Comunidades.
 * Centralizar os dados nesta classe facilita a serialização (salvamento em XML) e garante
 * que todos os serviços acessem a mesma fonte de verdade, respeitando a arquitetura do projeto.
 */
public class RepositorioJackut {

    /** Mapa que armazena os usuários cadastrados, usando o login como chave. */
    private Map<String, Usuario> usuarios;

    /** Mapa que armazena as comunidades criadas, usando o nome como chave. */
    private Map<String, Comunidade> comunidades;

    /**
     * Construtor padrão.
     * Inicializa as coleções utilizando LinkedHashMap para preservar a ordem de inserção dos elementos.
     */
    public RepositorioJackut() {
        this.usuarios = new LinkedHashMap<>();
        this.comunidades = new LinkedHashMap<>();
    }

    // =========================================================================
    // GETTERS E SETTERS
    // =========================================================================

    public Map<String, Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Map<String, Usuario> usuarios) { this.usuarios = usuarios; }

    public Map<String, Comunidade> getComunidades() { return comunidades; }
    public void setComunidades(Map<String, Comunidade> comunidades) { this.comunidades = comunidades; }

    // =========================================================================
    // CONTROLE DE ESTADO
    // =========================================================================

    /**
     * Limpa completamente o banco de dados em memória.
     * Utilizado principalmente para resetar o estado entre as execuções dos testes de aceitação.
     */
    public void zerar() {
        this.usuarios.clear();
        this.comunidades.clear();
    }

    // =========================================================================
    // CRUD DE USUÁRIOS
    // =========================================================================

    /**
     * Verifica se um usuário está cadastrado no sistema.
     * @param login O login do usuário a ser verificado.
     * @return true se o usuário existir, false caso contrário.
     */
    public boolean existeUsuario(String login) { return this.usuarios.containsKey(login); }

    /**
     * Busca a entidade completa de um usuário pelo seu login.
     * @param login O login do usuário.
     * @return O objeto Usuario correspondente, ou null se não existir.
     */
    public Usuario buscarUsuario(String login) { return this.usuarios.get(login); }

    /**
     * Salva ou atualiza um usuário no repositório.
     * @param u O objeto Usuario a ser persistido na memória.
     */
    public void salvarUsuario(Usuario u) { this.usuarios.put(u.getLogin(), u); }

    // =========================================================================
    // CRUD DE COMUNIDADES
    // =========================================================================

    /**
     * Verifica se uma comunidade já foi criada no sistema.
     * @param nome O nome da comunidade a ser verificada.
     * @return true se a comunidade existir, false caso contrário.
     */
    public boolean existeComunidade(String nome) { return this.comunidades.containsKey(nome); }

    /**
     * Busca a entidade completa de uma comunidade pelo seu nome.
     * @param nome O nome da comunidade.
     * @return O objeto Comunidade correspondente, ou null se não existir.
     */
    public Comunidade buscarComunidade(String nome) { return this.comunidades.get(nome); }

    /**
     * Salva ou atualiza uma comunidade no repositório.
     * @param c O objeto Comunidade a ser persistido na memória.
     */
    public void salvarComunidade(Comunidade c) { this.comunidades.put(c.getNome(), c); }
}
