package br.ufal.ic.p2.jackut;

import easyaccept.EasyAccept;
import br.ufal.ic.p2.jackut.controllers.ControladorDeUsuarios;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Classe de Fachada (Facade) que centraliza os pontos de entrada do sistema Jackut.
 * Oculta a complexidade dos subsistemas internos e fornece uma interface simplificada
 * para a execução dos testes de aceitação via EasyAccept e para a persistência de dados.
 */
public class Facade {

    /** Controlador responsável por delegar as regras de negócio referentes aos usuários. */
    private ControladorDeUsuarios controladorUsuarios;

    /**
     * Construtor padrão da Facade.
     * Inicializa o sistema tentando carregar os dados previamente salvos em disco.
     */
    public Facade() {
        carregarDados();
    }

    /**
     * Reseta completamente o estado do sistema, limpando todos os dados em memória.
     */
    public void zerarSistema() {
        this.controladorUsuarios.zerar();
    }

    /**
     * Finaliza a execução do sistema, garantindo que os dados atuais em memória
     * sejam persistidos no arquivo XML antes de fechar.
     */
    public void encerrarSistema() {
        salvarDados();
    }

    /**
     * Delega a criação de um novo usuário para o controlador de usuários.
     *
     * @param login O identificador único do usuário.
     * @param senha A senha de acesso.
     * @param nome O nome de exibição.
     * @throws Exception Caso os dados sejam inválidos ou o login já exista.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        this.controladorUsuarios.criarUsuario(login, senha, nome);
    }

    /**
     * Autentica um usuário no sistema e retorna sua identificação de sessão.
     *
     * @param login O identificador único do usuário.
     * @param senha A senha correspondente.
     * @return O login do usuário autenticado para ser usado como ID de sessão.
     * @throws Exception Se as credenciais estiverem incorretas ou não existirem.
     */
    public String abrirSessao(String login, String senha) throws Exception {
        return this.controladorUsuarios.abrirSessao(login, senha);
    }

    /**
     * Consulta um atributo específico de um usuário.
     *
     * @param login O identificador único do usuário alvo.
     * @param atributo O nome do atributo desejado.
     * @return O valor em texto do atributo.
     * @throws Exception Caso o atributo não exista ou não tenha sido preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        return this.controladorUsuarios.getAtributoUsuario(login, atributo);
    }

    /**
     * Edita ou adiciona um novo atributo ao perfil do usuário logado.
     *
     * @param id O ID da sessão do usuário autenticado.
     * @param atributo O nome do atributo a ser editado.
     * @param valor O novo valor para o atributo.
     * @throws Exception Caso o usuário não esteja cadastrado.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        this.controladorUsuarios.editarPerfil(id, atributo, valor);
    }

    /**
     * Adiciona um usuário à lista de convites ou de amigos do usuário atual.
     *
     * @param id O ID da sessão do usuário autenticado que faz a solicitação.
     * @param amigo O login do usuário alvo do convite.
     * @throws Exception Caso as regras de amizade sejam violadas.
     */
    public void adicionarAmigo(String id, String amigo) throws Exception {
        this.controladorUsuarios.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usuários possuem vínculo de amizade.
     *
     * @param login O login do primeiro usuário.
     * @param amigo O login do segundo usuário.
     * @return {@code true} se forem amigos, {@code false} caso contrário.
     */
    public boolean ehAmigo(String login, String amigo) {
        return this.controladorUsuarios.ehAmigo(login, amigo);
    }

    /**
     * Retorna a lista formatada de todos os amigos de um usuário.
     *
     * @param login O login do usuário a ser pesquisado.
     * @return Uma String contendo os amigos formatada entre chaves (ex: "{amigo1,amigo2}").
     * @throws Exception Caso o usuário não seja encontrado.
     */
    public String getAmigos(String login) throws Exception {
        return this.controladorUsuarios.getAmigos(login);
    }

    /**
     * Envia um recado para a caixa de entrada de outro usuário.
     *
     * @param id O ID da sessão do usuário remetente.
     * @param destinatario O login do usuário que receberá o recado.
     * @param recado O texto do recado a ser enviado.
     * @throws Exception Caso o destinatário não exista ou o envio seja inválido.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        this.controladorUsuarios.enviarRecado(id, destinatario, recado);
    }

    /**
     * Lê e consome o recado mais antigo da caixa de entrada do usuário.
     *
     * @param id O ID da sessão do usuário autenticado.
     * @return O texto do primeiro recado da fila.
     * @throws Exception Caso a caixa de entrada esteja vazia.
     */
    public String lerRecado(String id) throws Exception {
        return this.controladorUsuarios.lerRecado(id);
    }

    /**
     * Serializa o controlador de usuários e salva os dados no arquivo XML.
     */
    private void salvarDados() {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("banco_jackut.xml")))) {
            encoder.writeObject(this.controladorUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserializa o arquivo XML para restaurar o estado do sistema.
     * Caso o arquivo não exista ou ocorra um erro, inicializa um controlador vazio.
     */
    private void carregarDados() {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("banco_jackut.xml")))) {
            this.controladorUsuarios = (ControladorDeUsuarios) decoder.readObject();
        } catch (IOException e) {
        } finally {
            if (this.controladorUsuarios == null) {
                this.controladorUsuarios = new ControladorDeUsuarios();
            }
        }
    }
}