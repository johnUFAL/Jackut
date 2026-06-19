package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ConvitePendente extends JackutException {
    public ConvitePendente() {
        super("Usu\uFFFDrio j\uFFFD est\uFFFD adicionado como amigo, esperando aceita\uFFFD\uFFFDo do convite.");
    }
}
