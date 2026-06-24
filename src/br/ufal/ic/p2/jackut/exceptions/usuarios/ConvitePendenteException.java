package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta enviar um convite para um usuário que já está na lista de amigos, mas ainda não aceitou. */
public class ConvitePendenteException extends JackutException {
    public ConvitePendenteException() {
        super("Usu\uFFFDrio j\uFFFD est\uFFFD adicionado como amigo, esperando aceita\uFFFD\uFFFDo do convite.");
    }
}
