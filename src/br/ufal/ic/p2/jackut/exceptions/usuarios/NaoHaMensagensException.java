package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta acessar mensagens que não existem. */
public class NaoHaMensagensException extends JackutException {
    public NaoHaMensagensException() {super("N\uFFFDo h\uFFFD mensagens.");}
}
