package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class ConvitePendente extends RuntimeException {
    public ConvitePendente() {
        super("Usu\uFFFDrio j\uFFFD est\uFFFD adicionado como amigo, esperando aceita\uFFFD\uFFFDo do convite.");
    }
}
