package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta adicionar um usuário que já é paquera. */
public class UsuarioJaAdicionadoComoPaqueraException extends JackutException {
    public UsuarioJaAdicionadoComoPaqueraException() {super("Usu\uFFFDrio j\uFFFD est\uFFFD adicionado como paquera.");}
}
