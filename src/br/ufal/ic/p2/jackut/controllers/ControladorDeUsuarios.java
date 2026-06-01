package br.ufal.ic.p2.jackut.controllers;

import br.ufal.ic.p2.jackut.exceptions.usuarios.*;
import br.ufal.ic.p2.jackut.models.Usuario;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControladorDeUsuarios {
    private Map<String, Usuario> usuarios;

    public ControladorDeUsuarios() {
        this.usuarios = new LinkedHashMap<>();
    }

    public Map<String, Usuario> getUsuarios() {return  usuarios;}
    public void setUsuarios(Map<String, Usuario> usuarios) {this.usuarios = usuarios;}

    public void zerar() {
        this.usuarios.clear();
    }

    public void criarUsuario(String login, String senha, String nome) throws Exception {
        if (login == null || login.trim().isEmpty()) throw  new LoginInvalido();
        if (senha == null || senha.trim().isEmpty()) throw  new SenhaInvalida();
        if (this.usuarios.containsKey(login)) throw new ContaComNomeJaExiste();

        Usuario u = new Usuario(login, senha, nome);
        this.usuarios.put(login, u);
    }

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

    public String abrirSessap(String login, String senha) throws Exception {
        if (!this.usuarios.containsKey(login)) throw new LoginSenhaInvalida();
        Usuario u = this.usuarios.get(login);
        if (!u.getSenha().equals(senha)) throw new LoginSenhaInvalida();

        return login;
    }

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

        if (remetente.getConvitesEnviados().contains(idAmigo)) {
            destinantario.getConvitesEnviados().remove(idSessao);
            remetente.getAmigos().add(idAmigo);
            destinantario.getAmigos().add(idSessao);
        } else {
            remetente.getConvitesEnviados().add(idAmigo);
        }
    }

    public boolean ehAmigo(String login, String amigo) {
        if (!this.usuarios.containsKey(login)) return false;
        return this.usuarios.get(login).getAmigos().contains(amigo);
    }

    public String getAmigos(String login) throws  Exception {
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

}
