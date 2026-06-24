package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta definir uma função inválida para um inimigo. */
public class FuncaoInvalidaInimigoException extends JackutException {
    public FuncaoInvalidaInimigoException(String nomeInimigo) {
        super("Fun\uFFFD\uFFFDo inv\uFFFDlida: " + nomeInimigo + " \uFFFD seu inimigo.");
    }
}
