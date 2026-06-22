package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.usuarios.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.NaoHaMensagensException;
import br.ufal.ic.p2.jackut.exceptions.usuarios.NaoHaReacadosException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class Usuario extends EntidadeJackut {

    private String login;
    private String senha;
    private Map<String, String> perfil;
    private List<String> amigos;
    private List<String> convitesEnviados;
    private List<String> recados;
    private List<String> comunidades;
    private List<String> mensagens;
    private List<String> idolos;
    private List<String> fas;
    private List<String> paqueras;
    private List<String> inimigos;

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

    public void adicionarAtributoPerfil(String atributo, String valor) {
        this.perfil.put(atributo, valor);
    }

    public String getAtributoPerfil(String atributo) throws Exception {
        if (!this.perfil.containsKey(atributo)) throw new AtributoNaoPreenchidoException();
        return this.perfil.get(atributo);
    }

    public void registrarConviteEnviado(String idAmigo) {
        if (!this.convitesEnviados.contains(idAmigo)) {
            this.convitesEnviados.add(idAmigo);
        }
    }

    public void aceitarConvite(String idAmigo) {
        this.convitesEnviados.remove(idAmigo);
        this.amigos.add(idAmigo);
    }

    public void adicionarAmigo(String idAmigo) {
        if (!this.amigos.contains(idAmigo)) {
            this.amigos.add(idAmigo);
        }
    }

    public boolean ehAmigo(String amigo) {
        return this.amigos.contains(amigo);
    }

    public boolean possuiConviteDe(String idSessao) {
        return this.convitesEnviados.contains(idSessao);
    }

    public String formatarListaDeAmigos() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < this.amigos.size(); i++) {
            sb.append(this.amigos.get(i));
            if (i < this.amigos.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void entrarNaComunidade(String nomeComunidade) {
        if (!this.comunidades.contains(nomeComunidade)) {
            this.comunidades.add(nomeComunidade);
        }
    }

    public String formatarListaDeComunidades() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < this.comunidades.size(); i++) {
            sb.append(this.comunidades.get(i));
            if (i < this.comunidades.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void adicionarIdolo(String idolo) {this.idolos.add(idolo);}
    public void adionarFa(String fa) {this.fas.add(fa);}
    public boolean ehFa(String idolo) {return this.idolos.contains(idolo);}

    public void adicionarPaquera(String paquera) {this.paqueras.add(paquera);}
    public boolean ehPaquera(String paquera) {return this.paqueras.contains(paquera);}

    public void adionarInimigo(String inimigo) {this.inimigos.add(inimigo);}
    public boolean temComoInimigo(String login) {return this.inimigos.contains(login);}

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

    public void receberMensagem(String mensagem) {
        this.mensagens.add(mensagem);
    }

    public String lerProximaMensagem() throws Exception {
        if (this.mensagens.isEmpty()) throw  new NaoHaMensagensException();
        return this.mensagens.remove(0);
    }

    @Override
    public void receberRecado(String recado) {
        this.recados.add(recado);
    }

    public String lerProximoRecado() throws Exception {
        if (this.recados.isEmpty()) throw new NaoHaReacadosException();
        return this.recados.remove(0);
    }

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
    public List<String> getRecados() { return recados; }
    public void setRecados(List<String> recados) { this.recados = recados; }
    public List<String> getComunidades() { return comunidades; }
    public void setComunidades(List<String> comunidades) { this.comunidades = comunidades; }
    public List<String> getMensagens() { return mensagens; }
    public void setMensagens(List<String> mensagens) { this.mensagens = mensagens; }
    public List<String> getIdolos() {return idolos;}
    public void setIdolos(List<String> idolos) {this.idolos = idolos;}
    public List<String> getFas() {return fas;}
    public void setFas(List<String> fas) {this.fas = fas;}
    public List<String > getPaqueras() {return paqueras;}
    public void setPaqueras(List<String> paqueras) {this.paqueras = paqueras;}
    public List<String> getInimigos() {return inimigos;}
    public void setInimigos(List<String> inimigos) {this.inimigos = inimigos;}
}
