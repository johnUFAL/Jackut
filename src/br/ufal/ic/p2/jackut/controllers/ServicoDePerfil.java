package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.models.Comunidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável por gerenciar o ciclo de vida das contas de usuário,
 * autenticação (login) e manipulação dos atributos de perfil.
 * Centraliza as regras de negócio de criação e remoção em cascata, garantindo
 * a integridade dos dados no repositório.
 */
public class ServicoDePerfil {

    /** Repositório central contendo os dados em memória do sistema. */
    private RepositorioJackut repo;

    /**
     * Construtor do serviço de perfil.
     * @param repo O repositório injetado para acesso às coleções de usuários e comunidades.
     */
    public ServicoDePerfil(RepositorioJackut repo) {
        this.repo = repo;
    }

    /**
     * Cria e cadastra um novo usuário no sistema Jackut.
     * @param login Identificador único do usuário.
     * @param senha Senha de acesso.
     * @param nome Nome de exibição do usuário.
     * @throws Exception Caso os dados fornecidos sejam inválidos (nulos/vazios) ou o login já esteja em uso.
     */
    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty()) throw new LoginInvalidoException();
        if (senha == null || senha.trim().isEmpty()) throw new SenhaInvalidaException();
        if (repo.existeUsuario(login)) throw new ContaComNomeJaExisteException();

        Usuario u = new Usuario(login, senha, nome);
        repo.salvarUsuario(u);
    }

    /**
     * Autentica um usuário validando suas credenciais de acesso.
     * @param login O login fornecido.
     * @param senha A senha fornecida.
     * @return O login do usuário autenticado (que atua como ID de sessão).
     * @throws Exception Caso o login não exista ou a senha esteja incorreta.
     */
    public String abrirSessao(String login, String senha) throws Exception {
        if (!repo.existeUsuario(login)) throw new LoginSenhaInvalidaException();
        Usuario u = repo.buscarUsuario(login);
        if (!u.getSenha().equals(senha)) throw new LoginSenhaInvalidaException();

        return login;
    }

    /**
     * Recupera o valor de um atributo específico do perfil de um usuário.
     * @param login O login do usuário a ser consultado.
     * @param atributo O nome do atributo (ex: "nome", "login" ou atributos customizados).
     * @return O valor textual correspondente ao atributo solicitado.
     * @throws Exception Caso o usuário não exista ou o atributo não tenha sido preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) throws Exception {
        if (!repo.existeUsuario(login)) throw new UsuarioNaoCadastradoException();
        Usuario u = repo.buscarUsuario(login);

        if (atributo.equals("nome")) {
            return u.getNome();
        }
        if (atributo.equals("login")) {
            return u.getLogin();
        }

        return u.getAtributoPerfil(atributo);
    }

    /**
     * Altera ou adiciona um novo atributo ao perfil do usuário autenticado.
     * @param id O identificador (login) da sessão do usuário.
     * @param atributo O nome do atributo a ser criado ou modificado.
     * @param valor O novo conteúdo textual associado ao atributo.
     * @throws Exception Caso o usuário não esteja cadastrado.
     */
    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        if (!repo.existeUsuario(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario u = repo.buscarUsuario(id);

        if (atributo.equals("nome")) {
            u.setNome(valor);
        } else if (atributo.equals("senha")) {
            u.setSenha(valor);
        } else {
            u.adicionarAtributoPerfil(atributo, valor);
        }
    }

    /**
     * Remove permanentemente a conta de um usuário e apaga todos os seus rastros do sistema.
     * O processo garante que:
     * 1. Recados e mensagens enviados pelo usuário sejam apagados das caixas de entrada de terceiros.
     * 2. O usuário seja removido de todas as listas de relacionamento
     * 3. As comunidades onde o usuário era dono sejam deletadas e removidas dos perfis dos membros.
     * 4. O usuário seja removido da lista de membros de comunidades de terceiros.
     * @param id O identificador (login) do usuário a ser removido.
     * @throws Exception Caso o usuário especificado não exista.
     */
    public void removerUsuario(String id) throws Exception {
        if (!repo.existeUsuario(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        for (Usuario u : repo.getUsuarios().values()) {
            u.getRecados().removeIf(m -> m.getRemetente().equals(id));
            u.getMensagens().removeIf(m -> m.getRemetente().equals(id));

            u.getAmigos().remove(id);
            u.getConvitesEnviados().remove(id);
            u.getIdolos().remove(id);
            u.getFas().remove(id);
            u.getPaqueras().remove(id);
            u.getInimigos().remove(id);
        }

        List<String> comunidadesParaExcluir = new ArrayList<>();
        for (Comunidade c : repo.getComunidades().values()) {
            if (c.getDono().equals(id)) {
                comunidadesParaExcluir.add(c.getNome());
            } else {
                c.getMembros().remove(id);
            }
        }

        for (String nomeComunidade : comunidadesParaExcluir) {
            repo.getComunidades().remove(nomeComunidade);
            for (Usuario u : repo.getUsuarios().values()) {
                u.getComunidades().remove(nomeComunidade);
            }
        }

        repo.getUsuarios().remove(id);
    }
}
