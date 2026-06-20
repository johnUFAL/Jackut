package br.ufal.ic.p2.jackut.exceptions.comunidades;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ComunidadeJaExiste extends JackutException {
    public ComunidadeJaExiste() {super("Comunidade com esse nome j\uFFFD existe.");}
}
