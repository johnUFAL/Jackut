package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class ConvitePendente extends RuntimeException{
    public ConvitePendente() {
        super("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
    }
}
