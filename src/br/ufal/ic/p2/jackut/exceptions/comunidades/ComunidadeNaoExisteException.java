package br.ufal.ic.p2.jackut.exceptions.comunidades;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta acessar uma comunidade que não existe. */
public class ComunidadeNaoExisteException extends JackutException {
    public ComunidadeNaoExisteException() {super("Comunidade n\uFFFDo existe.");}
}
