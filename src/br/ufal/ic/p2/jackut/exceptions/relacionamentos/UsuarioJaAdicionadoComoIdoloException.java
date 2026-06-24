package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta adicionar um usuário que já é idolo. */
public class UsuarioJaAdicionadoComoIdoloException extends JackutException {
    public UsuarioJaAdicionadoComoIdoloException() {super("Usu\uFFFDrio j\uFFFD est\uFFFD adicionado como \uFFFDdolo.");}
}
