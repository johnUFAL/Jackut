package br.ufal.ic.p2.jackut;

import easyaccept.EasyAccept;
import br.ufal.ic.p2.jackut.controllers.ControladorDeUsuarios;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class Facade {
    private ControladorDeUsuarios controladorUsuarios;

    public Facade() {
        carregarDados();
    }

    public void zerarSistema() {
        this.controladorUsuarios.zerar();;
    }

    public void encerrarSistema() {
        salvarDados();
    }

    public void criarUsuario(String login, String senha, String nome) throws  Exception {
        this.controladorUsuarios.criarUsuario(login, senha, nome);
    }

    public String abrirSessao(String login, String senha) throws Exception {
        return this.controladorUsuarios.abrirSessap(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo) throws Exception {
        return this.controladorUsuarios.getAtributoUsuario(login, atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) throws Exception {
        this.controladorUsuarios.editarPerfil(id, atributo, valor);
    }

    public void adicionarAmigo(String id, String amigo) throws Exception {
        this.controladorUsuarios.adicionarAmigo(id, amigo);
    }

    public boolean ehAmigo(String login, String amigo) {
        return this.controladorUsuarios.ehAmigo(login, amigo);
    }

    public String getAmigos(String login) throws Exception{
        return this.controladorUsuarios.getAmigos(login);
    }

    private void salvarDados() {
        try (XMLEncoder encoder = new XMLEncoder((new BufferedOutputStream((new FileOutputStream("banco_jackut.xml")))))) {
            encoder.writeObject(this.controladorUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarDados() {
        try (XMLDecoder decoder = new XMLDecoder((new BufferedInputStream((new FileInputStream("banco_jackut.xml")))))) {
            this.controladorUsuarios = (ControladorDeUsuarios) decoder.readObject();
        } catch (IOException e) {
        } finally {
            if (this.controladorUsuarios == null) {
                this.controladorUsuarios = new ControladorDeUsuarios();
            }
        }
    }
}
