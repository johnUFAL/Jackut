package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class JaEhAmigo extends RuntimeException{
    public JaEhAmigo() {
        super("Usu�rio j� est� adicionado como amigo.");
    }
}
