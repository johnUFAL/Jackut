package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class AdicionarASiMesmo extends RuntimeException{
    public AdicionarASiMesmo() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}
