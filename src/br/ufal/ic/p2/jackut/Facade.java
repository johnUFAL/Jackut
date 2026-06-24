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
    private ServicoDeRelacionamentos servicoDeRelacionamentos;

    /**
     * Construtor padrão da Facade.
     * Inicializa o sistema tentando carregar os dados previamente salvos em disco
     * e injeta o repositório nos serviços.
     */
    public Facade() {
        carregarDados();

        this.servicoDePerfil = new ServicoDePerfil(this.repositorio);
        this.servicoDeAmizades = new ServicoDeAmizades(this.repositorio);
        this.servicoDeRecados = new ServicoDeRecados(this.repositorio);
        this.servicoDeComunidades = new ServicoDeComunidades(this.repositorio);
        this.servicoDeMensagens = new ServicoDeMensagens(this.repositorio);
        this.servicoDeRelacionamentos = new ServicoDeRelacionamentos(this.repositorio);
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

    /**
     * Cria e cadastra um novo usuário no sistema.
     * @param login Identificador único do usuário.
     * @param senha Senha de acesso.
     * @param nome Nome de exibição.
     * @throws Exception Caso os dados sejam inválidos ou o login já exista.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        this.servicoDePerfil.criarUsuario(login, senha, nome);
    }

    /**
     * Autentica um usuário no sistema.
     * @param login Identificador do usuário.
     * @param senha Senha correspondente.
     * @return O login do usuário autenticado (ID da sessão).
     * @throws Exception Se as credenciais estiverem incorretas.
     */
    public String abrirSessao(String login, String senha) throws Exception {
        return this.servicoDePerfil.abrirSessao(login, senha);
    }

    /**
     * Consulta um atributo específico do perfil de um usuário.
     * @param login Login do usuário alvo.
     * @param atributo Nome do atributo desejado.
     * @return O valor do atributo.
     * @throws Exception Caso o usuário ou atributo não existam.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        return this.servicoDePerfil.getAtributoUsuario(login, atributo);
    }

    /**
     * Adiciona ou atualiza um atributo no perfil do usuário logado.
     * @param id ID da sessão do usuário.
     * @param atributo Nome do atributo.
     * @param valor Valor do atributo.
     * @throws Exception Caso o usuário não esteja cadastrado.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        this.servicoDePerfil.editarPerfil(id, atributo, valor);
    }

    /**
     * Remove a conta de um usuário e todos os seus rastros do sistema.
     * @param id ID da sessão do usuário a ser removido.
     * @throws Exception Caso o usuário não exista.
     */
    public void removerUsuario(String id) throws Exception {
        this.servicoDePerfil.removerUsuario(id);
    }

    // =========================================================================
    // SERVIÇO DE AMIZADES
    // =========================================================================

    /**
     * Envia um convite de amizade ou efetiva uma amizade mútua.
     * @param id ID da sessão do remetente.
     * @param amigo Login do destinatário.
     * @throws Exception Em caso de regras violadas (ex: já amigos, auto-adição, etc).
     */
    public void adicionarAmigo(String id, String amigo) throws Exception {
        this.servicoDeAmizades.adicionarAmigo(id, amigo);
    }

    /**
     * Verifica se dois usuários são amigos.
     * @param login Login do primeiro usuário.
     * @param amigo Login do segundo usuário.
     * @return true se forem amigos, false caso contrário.
     */
    public boolean ehAmigo(String login, String amigo) {
        return this.servicoDeAmizades.ehAmigo(login, amigo);
    }

    /**
     * Retorna a lista de amigos de um usuário.
     * @param login Login do usuário alvo.
     * @return Lista formatada de amigos (ex: "{amigo1,amigo2}").
     * @throws Exception Caso o usuário não exista.
     */
    public String getAmigos(String login) throws Exception {
        return this.servicoDeAmizades.getAmigos(login);
    }

    // =========================================================================
    // SERVIÇO DE RECADOS
    // =========================================================================

    /**
     * Envia um recado privado para outro usuário.
     * @param id ID da sessão do remetente.
     * @param destinatario Login do destinatário.
     * @param recado Texto do recado.
     * @throws Exception Se o destinatário não existir ou envio inválido.
     */
    public void enviarRecado(String id, String destinatario, String recado) throws Exception {
        this.servicoDeRecados.enviarRecado(id, destinatario, recado);
    }

    /**
     * Lê e consome o recado mais antigo da caixa de entrada do usuário.
     * @param id ID da sessão do usuário.
     * @return Texto do recado lido.
     * @throws Exception Caso não haja recados.
     */
    public String lerRecado(String id) throws Exception {
        return this.servicoDeRecados.lerRecado(id);
    }

    // =========================================================================
    // SERVIÇO DE COMUNIDADES
    // =========================================================================

    /**
     * Cria uma nova comunidade no sistema.
     * @param sessao ID da sessão do criador (dono).
     * @param nome Nome único da comunidade.
     * @param descricao Descrição da comunidade.
     * @throws Exception Se a comunidade já existir.
     */
    public void criarComunidade(String sessao, String nome, String descricao) throws Exception {
        this.servicoDeComunidades.criarComunidade(sessao, nome, descricao);
    }

    /**
     * Consulta a descrição de uma comunidade.
     * @param nome Nome da comunidade.
     * @return A descrição da comunidade.
     * @throws Exception Caso a comunidade não exista.
     */
    public String getDescricaoComunidade(String nome) throws Exception {
        return this.servicoDeComunidades.getDescricaoComunidade(nome);
    }

    /**
     * Consulta o dono de uma comunidade.
     * @param nome Nome da comunidade.
     * @return Login do dono.
     * @throws Exception Caso a comunidade não exista.
     */
    public String getDonoComunidade(String nome) throws Exception {
        return this.servicoDeComunidades.getDonoComunidade(nome);
    }

    /**
     * Consulta a lista de membros de uma comunidade.
     * @param nome Nome da comunidade.
     * @return Lista formatada de membros.
     * @throws Exception Caso a comunidade não exista.
     */
    public String getMembrosComunidade(String nome) throws Exception {
        return this.servicoDeComunidades.getMembrosComunidade(nome);
    }

    /**
     * Adiciona o usuário logado a uma comunidade existente.
     * @param sessao ID da sessão do usuário.
     * @param nome Nome da comunidade.
     * @throws Exception Se o usuário já for membro ou a comunidade não existir.
     */
    public void adicionarComunidade(String sessao, String nome) throws Exception {
        this.servicoDeComunidades.adicionarComunidade(sessao, nome);
    }

    /**
     * Retorna a lista de comunidades das quais o usuário participa.
     * @param login Login do usuário.
     * @return Lista formatada de comunidades.
     * @throws Exception Caso o usuário não exista.
     */
    public String getComunidades(String login) throws Exception {
        return this.servicoDeComunidades.getComunidades(login);
    }

    // =========================================================================
    // SERVIÇO DE MENSAGENS (COMUNIDADES)
    // =========================================================================

    /**
     * Envia uma mensagem pública para todos os membros de uma comunidade.
     * @param id ID da sessão do remetente.
     * @param comunidade Nome da comunidade.
     * @param mensagem Texto da mensagem.
     * @throws Exception Caso o usuário ou a comunidade não existam.
     */
    public void enviarMensagem(String id, String comunidade, String mensagem) throws Exception {
        this.servicoDeMensagens.enviarMensagem(id, comunidade, mensagem);
    }

    /**
     * Lê e consome a mensagem de comunidade mais antiga da caixa de entrada do usuário.
     * @param id ID da sessão do usuário.
     * @return Texto da mensagem lida.
     * @throws Exception Caso não haja mensagens de comunidades.
     */
    public String lerMensagem(String id) throws Exception {
        return this.servicoDeMensagens.lerMensagem(id);
    }

    // =========================================================================
    // SERVIÇO DE RELACIONAMENTOS
    // =========================================================================

    /**
     * Adiciona um usuário à lista de ídolos do usuário logado.
     * @param id ID da sessão do usuário fã.
     * @param idolo Login do ídolo.
     * @throws Exception Caso regras de relacionamento sejam violadas.
     */
    public void adicionarIdolo(String id, String idolo) throws Exception {
        this.servicoDeRelacionamentos.adicionarIdolo(id, idolo);
    }

    /**
     * Verifica se um usuário é fã de outro.
     * @param login Login do possível fã.
     * @param idolo Login do possível ídolo.
     * @return true se for fã, false caso contrário.
     */
    public boolean ehFa(String login, String idolo) {
        return this.servicoDeRelacionamentos.ehFa(login, idolo);
    }

    /**
     * Retorna a lista de fãs de um usuário.
     * @param login Login do usuário.
     * @return Lista formatada de fãs.
     * @throws Exception Caso o usuário não exista.
     */
    public String getFas(String login) throws Exception {
        return this.servicoDeRelacionamentos.getFas(login);
    }

    /**
     * Adiciona um usuário como paquera do usuário logado.
     * Se for mútuo, notifica ambos.
     * @param id ID da sessão do usuário logado.
     * @param paquera Login do usuário alvo.
     * @throws Exception Caso regras de relacionamento sejam violadas.
     */
    public void adicionarPaquera(String id, String paquera) throws Exception {
        this.servicoDeRelacionamentos.adicionarPaquera(id, paquera);
    }

    /**
     * Verifica se um usuário adicionou o outro como paquera.
     * @param id ID da sessão do remetente.
     * @param paquera Login do alvo.
     * @return true se for paquera, false caso contrário.
     */
    public boolean ehPaquera(String id, String paquera) {
        return this.servicoDeRelacionamentos.ehPaquera(id, paquera);
    }

    /**
     * Retorna a lista de paqueras do usuário logado.
     * @param id ID da sessão do usuário.
     * @return Lista formatada de paqueras.
     * @throws Exception Caso o usuário não exista.
     */
    public String getPaqueras(String id) throws Exception {
        return this.servicoDeRelacionamentos.getPaqueras(id);
    }

    /**
     * Adiciona um usuário como inimigo, bloqueando interações futuras.
     * @param id ID da sessão do usuário logado.
     * @param inimigo Login do inimigo.
     * @throws Exception Caso regras de inimizade sejam violadas.
     */
    public void adicionarInimigo(String id, String inimigo) throws Exception {
        this.servicoDeRelacionamentos.adicionarInimigo(id, inimigo);
    }

    // =========================================================================
    // PERSISTÊNCIA DE DADOS
    // =========================================================================

    /**
     * Serializa o repositório central e salva os dados no arquivo XML.
     */
    private void salvarDados() {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("banco_jackut.xml")))) {
            encoder.writeObject(this.repositorio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserializa o arquivo XML para restaurar o estado do sistema.
     * Caso o arquivo não exista ou ocorra um erro, inicializa um repositório vazio.
     */
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
