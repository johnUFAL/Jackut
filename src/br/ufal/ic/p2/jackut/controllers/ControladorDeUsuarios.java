package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe controladora responsável por gerenciar toda a lógica de negócio referente
 * aos usuários do sistema Jackut, incluindo criação de contas, edição de perfis,
 * gerenciamento de amizades e envio/leitura de recados.
 */
public class ControladorDeUsuarios {

    /**
     * Estrutura de dados que armazena os usuários cadastrados no sistema.
     * A chave é o login (String) e o valor é o objeto Usuario correspondente.
     */
    private Map<String, Usuario> usuarios;

    /**
     * Construtor padrão da classe ControladorDeUsuarios.
     * Inicializa o mapa de usuários utilizando um LinkedHashMap para manter a ordem de inserção.
     */
    public ControladorDeUsuarios() {
        this.usuarios = new LinkedHashMap<>();
    }

    /**
     * Obtém o mapa atual de usuários cadastrados no sistema.
     * @return Mapa (Map) contendo os logins como chave e os objetos Usuario como valor.
     */
    public Map<String, Usuario> getUsuarios() { return usuarios; }

    /**
     * Substitui o mapa de usuários do sistema.
     * @param usuarios Novo mapa de usuários a ser definido.
     */
    public void setUsuarios(Map<String, Usuario> usuarios) { this.usuarios = usuarios; }

    /**
     * Reseta o estado do controlador, apagando todos os usuários cadastrados.
     */
    public void zerar() {
        this.usuarios.clear();
    }

    /**
     * Cria e cadastra um novo usuário no sistema Jackut.
     *
     * @param login Identificador único do usuário.
     * @param senha Senha de acesso da conta.
     * @param nome Nome de exibição do usuário.
     * @throws Exception Caso o login ou a senha sejam vazios/nulos, ou se o login já estiver em uso.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty()) throw new LoginInvalido();
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalida();
        if (this.usuarios.containsKey(login)) throw new ContaComNomeJaExiste();

        Usuario u = new Usuario(login, senha, nome);
        this.usuarios.put(login, u);
    }

    /**
     * Recupera o valor de um atributo específico do perfil de um usuário.
     *
     * @param login O login do usuário a ser consultado.
     * @param atributo O nome do atributo desejado (ex: "nome", "login", "descricao").
     * @return O valor em texto (String) correspondente ao atributo solicitado.
     * @throws Exception Caso o usuário não exista ou o atributo não tenha sido preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        if (!this.usuarios.containsKey(login)) throw new UsuarioNaoCadastrado();
        Usuario u = this.usuarios.get(login);

        if (atributo.equals("nome")) {
            return u.getNome();
        }
        if (atributo.equals("login")) {
            return u.getLogin();
        }

        if (!u.getPerfil().containsKey(atributo)) {
            throw new AtributoNaoPreenchido();
        }

        return u.getPerfil().get(atributo);
    }

    /**
     * Autentica um usuário validando suas credenciais de acesso.
     *
     * @param login O login fornecido pelo usuário.
     * @param senha A senha fornecida pelo usuário.
     * @return O identificador da sessão aberta (o próprio login, em caso de sucesso).
     * @throws Exception Caso o login não exista ou a senha esteja incorreta.
     */
    public String abrirSessao(String login, String senha) throws Exception {
        if (!this.usuarios.containsKey(login)) throw new LoginSenhaInvalida();
        Usuario u = this.usuarios.get(login);
        if (!u.getSenha().equals(senha)) throw new LoginSenhaInvalida();

        return login;
    }

    /**
     * Altera ou adiciona um novo atributo ao perfil do usuário autenticado.
     *
     * @param id O identificador (login) da sessão do usuário.
     * @param atributo O nome do atributo a ser criado ou modificado.
     * @param valor O novo conteúdo textual a ser associado a este atributo.
     * @throws Exception Caso a sessão especificada não pertença a um usuário cadastrado.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        if (!this.usuarios.containsKey(id)) {
            throw new UsuarioNaoCadastrado();
        }

        Usuario u = this.usuarios.get(id);

        if (atributo.equals("nome")) {
            u.setNome(valor);
        } else if (atributo.equals("senha")) {
            u.setSenha(valor);
        } else {
            u.getPerfil().put(atributo, valor);
        }
    }

    /**
     * Inicia o fluxo de adição de amizade entre dois usuários. Caso ambos tenham se
     * adicionado mutuamente, a amizade é efetivada. Caso contrário, um convite é registrado.
     *
     * @param idSessao O identificador da sessão do usuário que está enviando o convite.
     * @param idAmigo O login do usuário que receberá o convite.
     * @throws Exception Se algum usuário for inválido, se a amizade já existir, se já houver convite pendente, ou se tentar adicionar a si mesmo.
     */
    public void adicionarAmigo(String idSessao, String idAmigo) throws Exception {
        if (!this.usuarios.containsKey(idSessao) || !this.usuarios.containsKey(idAmigo)) {
            throw new UsuarioNaoCadastrado();
        }
        if (idSessao.equals(idAmigo)) {
            throw new AdicionarASiMesmo();
        }

        Usuario remetente = this.usuarios.get(idSessao);
        Usuario destinantario = this.usuarios.get(idAmigo);

        if (remetente.getAmigos().contains(idAmigo)) {
            throw new JaEhAmigo();
        }

        if (destinantario.getConvitesEnviados().contains(idSessao)) {
            destinantario.getConvitesEnviados().remove(idSessao);
            remetente.getAmigos().add(idAmigo);
            destinantario.getAmigos().add(idSessao);
        } else {
            if (remetente.getConvitesEnviados().contains(idAmigo)) {
                throw new ConvitePendente();
            }
            remetente.getConvitesEnviados().add(idAmigo);
        }
    }

    /**
     * Verifica se existe uma relação de amizade efetivada entre dois usuários.
     *
     * @param login O login do primeiro usuário.
     * @param amigo O login do usuário a ser verificado na lista de amigos do primeiro.
     * @return {@code true} se eles forem amigos, {@code false} caso contrário ou se o usuário não existir.
     */
    public boolean ehAmigo(String login, String amigo) {
        if (!this.usuarios.containsKey(login)) return false;
        return this.usuarios.get(login).getAmigos().contains(amigo);
    }

    /**
     * Obtém a lista completa de amigos de um usuário formatada adequadamente.
     *
     * @param login O login do usuário cujos amigos serão listados.
     * @return Uma String contendo a listagem formatada (ex: "{amigo1,amigo2}").
     * @throws Exception Caso o usuário especificado não esteja cadastrado no sistema.
     */
    public String getAmigos(String login) throws Exception {
        if (!this.usuarios.containsKey(login)) throw new UsuarioNaoCadastrado();
        Usuario u = this.usuarios.get(login);

        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < u.getAmigos().size(); i++) {
            sb.append(u.getAmigos().get(i));
            if (i < u.getAmigos().size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Despacha um recado textual para a caixa de entrada de outro usuário.
     *
     * @param idSessao O identificador da sessão do usuário remetente.
     * @param destinatario O login do usuário que receberá a mensagem.
     * @param recado O conteúdo textual do recado a ser enviado.
     * @throws Exception Caso o destinatário não exista ou caso o usuário tente enviar mensagem para si mesmo.
     */
    public void enviarRecado(String idSessao, String destinatario, String recado) throws Exception {
        if (!this.usuarios.containsKey(destinatario)) {
            throw new UsuarioNaoCadastrado();
        }
        if (idSessao.equals(destinatario)) {
            throw new NaoPodeEnviarRecadoASiMesmo();
        }

        this.usuarios.get(destinatario).getRecados().add(recado);
    }

    /**
     * Lê e remove o recado mais antigo da caixa de entrada do usuário.
     *
     * @param idSessao O identificador da sessão do usuário que deseja ler o recado.
     * @return O conteúdo textual do recado lido.
     * @throws Exception Caso o usuário não tenha nenhum recado pendente em sua caixa de entrada.
     */
    public String lerRecado(String idSessao) throws Exception {
        Usuario u = this.usuarios.get(idSessao);

        if (u.getRecados().isEmpty()) {
            throw new NaoHaReacados();
        }

        return u.getRecados().remove(0);
    }
}