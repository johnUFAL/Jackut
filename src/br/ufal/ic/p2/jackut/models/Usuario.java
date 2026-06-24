package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.usuarios.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.NaoHaMensagensException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.NaoHaReacadosException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

/**
 * Representa um Usuário no sistema Jackut.
 * Esta é uma entidade de Domínio Rico, responsável por encapsular e gerenciar
 * o seu próprio estado, incluindo credenciais, atributos dinâmicos de perfil,
 * relacionamentos diversos (amigos, fãs, paqueras, inimigos) e filas de mensagens.
 */
public class Usuario extends EntidadeJackut {

    /** Identificador único do usuário. */
    private String login;

    /** Senha de acesso. */
    private String senha;

    /** Mapa dinâmico que permite expansão infinita de atributos do perfil. */
    private Map<String, String> perfil;

    /** Lista de logins dos amigos confirmados. */
    private List<String> amigos;

    /** Lista de logins de usuários que enviaram um convite pendente para este usuário. */
    private List<String> convitesEnviados;

    /** Fila (FIFO) de recados privados recebidos. */
    private List<MensagemJackut> recados;

    /** Lista de nomes das comunidades das quais o usuário participa. */
    private List<String> comunidades;

    /** Fila (FIFO) de mensagens públicas recebidas via comunidades. */
    private List<MensagemJackut> mensagens;

    /** Lista de logins dos usuários que este usuário admira (ídolos). */
    private List<String> idolos;

    /** Lista de logins dos usuários que admiram este usuário (fãs). */
    private List<String> fas;

    /** Lista de logins dos usuários marcados como paqueras. */
    private List<String> paqueras;

    /** Lista de logins bloqueados (inimigos). */
    private List<String> inimigos;

    /**
     * Construtor padrão vazio.
     * Exigido obrigatoriamente pelo XMLDecoder para remontar o objeto na memória.
     * Inicializa todas as coleções para evitar NullPointerException.
     */
    public Usuario() {
        super();
        this.perfil = new LinkedHashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
        this.recados = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.mensagens = new ArrayList<>();
        this.idolos = new ArrayList<>();
        this.fas = new ArrayList<>();
        this.paqueras = new ArrayList<>();
        this.inimigos = new ArrayList<>();
    }

    /**
     * Cria um novo usuário com credenciais básicas.
     * @param login Identificador único.
     * @param senha Senha de acesso.
     * @param nome Nome de exibição.
     */
    public Usuario(String login, String senha, String nome) {
        super(nome);
        this.login = login;
        this.senha = senha;
        this.perfil = new LinkedHashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
        this.recados = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.mensagens = new ArrayList<>();
        this.idolos = new ArrayList<>();
        this.fas = new ArrayList<>();
        this.paqueras = new ArrayList<>();
        this.inimigos = new ArrayList<>();
    }

    // =========================================================================
    // REGRAS DE NEGÓCIO ENCAPSULADAS
    // =========================================================================

    /**
     * Adiciona ou atualiza um atributo dinâmico no perfil.
     * @param atributo O nome do atributo (ex: "cidade").
     * @param valor O conteúdo do atributo.
     */
    public void adicionarAtributoPerfil(String atributo, String valor) {
        this.perfil.put(atributo, valor);
    }

    /**
     * Recupera um atributo do perfil.
     * @param atributo Nome do atributo a ser buscado.
     * @return O valor associado.
     * @throws Exception Caso o atributo não exista.
     */
    public String getAtributoPerfil(String atributo) throws Exception {
        if (!this.perfil.containsKey(atributo)) throw new AtributoNaoPreenchidoException();
        return this.perfil.get(atributo);
    }

    /** Registra o recebimento de um novo convite de amizade. */
    public void registrarConviteEnviado(String idAmigo) {
        if (!this.convitesEnviados.contains(idAmigo)) {
            this.convitesEnviados.add(idAmigo);
        }
    }

    /** Consome um convite pendente e efetiva a amizade. */
    public void aceitarConvite(String idAmigo) {
        this.convitesEnviados.remove(idAmigo);
        this.amigos.add(idAmigo);
    }

    /** Efetiva diretamente a amizade (usado em adições mútuas). */
    public void adicionarAmigo(String idAmigo) {
        if (!this.amigos.contains(idAmigo)) {
            this.amigos.add(idAmigo);
        }
    }

    /** Verifica se outro usuário está na lista de amigos. */
    public boolean ehAmigo(String amigo) {
        return this.amigos.contains(amigo);
    }

    /** Verifica se existe um convite pendente vindo deste usuário. */
    public boolean possuiConviteDe(String idSessao) {
        return this.convitesEnviados.contains(idSessao);
    }

    /** Formata a lista de amigos no padrão do EasyAccept. */
    public String formatarListaDeAmigos() {
        return formatarLista(this.amigos);
    }

    /** Adiciona o usuário a uma comunidade. */
    public void entrarNaComunidade(String nomeComunidade) {
        if (!this.comunidades.contains(nomeComunidade)) {
            this.comunidades.add(nomeComunidade);
        }
    }

    /** Formata a lista de comunidades no padrão do EasyAccept. */
    public String formatarListaDeComunidades() {
        return formatarLista(this.comunidades);
    }

    public void adicionarIdolo(String idolo) { this.idolos.add(idolo); }
    public void adicionarFa(String fa) { this.fas.add(fa); }
    public boolean ehFa(String idolo) { return this.idolos.contains(idolo); }

    public void adicionarPaquera(String paquera) { this.paqueras.add(paquera); }
    public boolean ehPaquera(String paquera) { return this.paqueras.contains(paquera); }

    public void adicionarInimigo(String inimigo) { this.inimigos.add(inimigo); }
    public boolean temComoInimigo(String login) { return this.inimigos.contains(login); }

    /**
     * Método utilitário para formatar qualquer lista de Strings no padrão de saída {a,b,c}.
     * @param lista A coleção a ser formatada.
     * @return String formatada.
     */
    public String formatarLista(List<String> lista) {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Adiciona um recado privado ao final da fila (FIFO).
     */
    @Override
    public void receberRecado(String remetente, String recado) {
        this.recados.add(new MensagemJackut(remetente, recado));
    }

    /**
     * Consome e retorna o recado mais antigo da fila.
     * @throws Exception Caso a caixa de entrada esteja vazia.
     */
    public String lerProximoRecado() throws Exception {
        if (this.recados.isEmpty()) throw new NaoHaReacadosException();
        return this.recados.remove(0).getTexto();
    }

    /**
     * Adiciona uma mensagem de comunidade ao final da fila (FIFO).
     */
    public void receberMensagem(String remetente, String mensagem) {
        this.mensagens.add(new MensagemJackut(remetente, mensagem));
    }

    /**
     * Consome e retorna a mensagem mais antiga da fila
     * @throws Exception Caso não haja mensagens de comunidades pendentes.
     */
    public String lerProximaMensagem() throws Exception {
        if (this.mensagens.isEmpty()) throw new NaoHaMensagensException();
        return this.mensagens.remove(0).getTexto();
    }

    // =========================================================================
    // GETTERS E SETTERS (Para o XMLDecoder)
    // =========================================================================

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public Map<String, String> getPerfil() { return perfil; }
    public void setPerfil(Map<String, String> perfil) { this.perfil = perfil; }
    public List<String> getAmigos() { return amigos; }
    public void setAmigos(List<String> amigos) { this.amigos = amigos; }
    public List<String> getConvitesEnviados() { return convitesEnviados; }
    public void setConvitesEnviados(List<String> convitesEnviados) { this.convitesEnviados = convitesEnviados; }
    public List<MensagemJackut> getRecados() { return recados; }
    public void setRecados(List<MensagemJackut> recados) { this.recados = recados; }
    public List<String> getComunidades() { return comunidades; }
    public void setComunidades(List<String> comunidades) { this.comunidades = comunidades; }
    public List<MensagemJackut> getMensagens() { return mensagens; }
    public void setMensagens(List<MensagemJackut> mensagens) { this.mensagens = mensagens; }
    public List<String> getIdolos() { return idolos; }
    public void setIdolos(List<String> idolos) { this.idolos = idolos; }
    public List<String> getFas() { return fas; }
    public void setFas(List<String> fas) { this.fas = fas; }
    public List<String> getPaqueras() { return paqueras; }
    public void setPaqueras(List<String> paqueras) { this.paqueras = paqueras; }
    public List<String> getInimigos() { return inimigos; }
    public void setInimigos(List<String> inimigos) { this.inimigos = inimigos; }
}
