package br.ufal.ic.p2.jackut.exceptions.comunidades;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ComunidadeNaoExisteException extends JackutException {
    public ComunidadeNaoExisteException() {super("Comunidade n\uFFFDo existe.");}
}
