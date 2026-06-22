package br.ufal.ic.p2.jackut.exceptions.comunidades;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ComunidadeJaExisteException extends JackutException {
    public ComunidadeJaExisteException() {super("Comunidade com esse nome j\uFFFD existe.");}
}
