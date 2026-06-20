package br.ufal.ic.p2.jackut.models;

import java.util.ArrayList;
import java.util.List;

public class Comunidade extends EntidadeJackut{

    private String descricao;
    private String dono;
    private List<String> membros;

    public Comunidade () {
        super();
        this.membros = new ArrayList<>();
    }

    public  Comunidade(String nome, String descricao, String dono) {
        super(nome);
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new ArrayList<>();
        this.membros.add(dono);
    }

    public String formatarListaDeMembros() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < this.membros.size(); i++) {
            sb.append(this.membros.get(i));
            if (i < this.membros.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void receberRecado(String recado) throws Exception{}

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDono() { return dono; }
    public void setDono(String dono) { this.dono = dono; }
    public List<String> getMembros() { return membros; }
    public void setMembros(List<String> membros) { this.membros = membros; }
}
