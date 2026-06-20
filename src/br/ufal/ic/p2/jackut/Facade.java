package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.controllers.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Classe de Fachada (Facade) que centraliza os pontos de entrada do sistema Jackut.
 * Oculta a complexidade dos subsistemas internos e fornece uma interface simplificada
 * para a execução dos testes de aceitação via EasyAccept e para a persistência de dados.
 */
public class Facade {

    /** Repositório central que armazena as coleções em memória para persistência. */
    private RepositorioJackut repositorio;

    /** Serviços modulares que aplicam as regras de negócio. */
    private ServicoDePerfil servicoDePerfil;
    private ServicoDeAmizades servicoDeAmizades;
    private ServicoDeRecados servicoDeRecados;
    private ServicoDeComunidades servicoDeComunidades;
    private ServicoDeMensagens servicoDeMensagens;

    /**
     * Construtor padrão da Facade.
     * Inicializa o sistema tentando carregar os dados previamente salvos em disco
     * e injeta o repositório nos serviços.
     */
    public Facade() {
        carregarDados();

        // Inicializa os serviços injetando o repositório carregado do XML
        this.servicoDePerfil = new ServicoDePerfil(this.repositorio);
        this.servicoDeAmizades = new ServicoDeAmizades(this.repositorio);
        this.servicoDeRecados = new ServicoDeRecados(this.repositorio);
        this.servicoDeComunidades = new ServicoDeComunidades(this.repositorio);
        this.servicoDeMensagens = new ServicoDeMensagens(this.repositorio);
    }

    /**
     * Reseta completamente o estado do sistema, limpando todos os dados em memória.
     */
    public void zerarSistema() {
        this.repositorio.zerar();
    }

    /**
     * Finaliza a execução do sistema, garantindo que os dados atuais em memória
     * sejam persistidos no arquivo XML antes de fechar.
     */
    public void encerrarSistema() {
        salvarDados();
    }

    // =========================================================================
    // SERVIÇO DE PERFIL & AUTENTICAÇÃO
    // =========================================================================

    public void criarUsuario(String login, String senha, String nome) throws Exception {
        this.servicoDePerfil.criarUsuario(login, senha, nome); // Nota: Você precisa mover a lógica do 'criarUsuario' do controlador antigo para o ServicoDePerfil
    }

    public String abrirSessao(String login, String senha) throws Exception {
        return this.servicoDePerfil.abrirSessao(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo) throws Exception {
        return this.servicoDePerfil.getAtributoUsuario(login, atributo); // Mova a lógica do 'getAtributoUsuario' para o ServicoDePerfil
    }

    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        this.servicoDePerfil.editarPerfil(id, atributo, valor);
    }

    // =========================================================================
    // SERVIÇO DE AMIZADES
    // =========================================================================

    public void adicionarAmigo(String id, String amigo) throws Exception {
        this.servicoDeAmizades.adicionarAmigo(id, amigo);
    }

    public boolean ehAmigo(String login, String amigo) {
        return this.servicoDeAmizades.ehAmigo(login, amigo);
    }

    public String getAmigos(String login) throws Exception {
        return this.servicoDeAmizades.getAmigos(login);
    }

    // =========================================================================
    // SERVIÇO DE RECADOS
    // =========================================================================

    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        this.servicoDeRecados.enviarRecado(id, destinatario, recado);
    }

    public String lerRecado(String id) throws Exception {
        return this.servicoDeRecados.lerRecado(id);
    }

    // =========================================================================
    // SERVIÇO DE COMUNIDADES
    // =========================================================================

    public void criarComunidade(String sessao, String nome, String descricao) throws Exception {
        this.servicoDeComunidades.criarComunidade(sessao, nome, descricao);
    }

    public String getDescricaoComunidade(String nome) throws  Exception {
        return this.servicoDeComunidades.getDescricaoComunidade(nome);
    }

    public String getDonoComunidade(String nome) throws Exception {
        return this.servicoDeComunidades.getDonoComunidade(nome);
    }

    public String getMembrosComunidade(String nome) throws Exception {
        return this.servicoDeComunidades.getMembrosComunidade(nome);
    }

    public void adicionarComunidade(String sessao, String nome) throws Exception {
        this.servicoDeComunidades.adionarComunidade(sessao, nome);
    }

    public String getComunidades(String login) throws Exception {
        return this.servicoDeComunidades.getComunidades(login);
    }

    // =========================================================================
    // SERVIÇO DE MENSAGENS (COMUNIDADES)
    // =========================================================================

    public void enviarMensagem(String id, String comunidade, String mensagem) throws Exception {
        this.servicoDeMensagens.enviarMensagem(id, comunidade, mensagem);
    }

    public String lerMensagem(String id) throws Exception {
        return this.servicoDeMensagens.lerMensagem(id);
    }

    // =========================================================================
    // PERSISTÊNCIA DE DADOS
    // =========================================================================

    private void salvarDados() {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("banco_jackut.xml")))) {
            encoder.writeObject(this.repositorio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarDados() {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("banco_jackut.xml")))) {
            this.repositorio = (RepositorioJackut) decoder.readObject();
        } catch (Exception e) {
        } finally {
            if (this.repositorio == null) {
                this.repositorio = new RepositorioJackut();
            }
        }
    }
}
