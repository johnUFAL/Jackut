package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class FuncaoInvalidaInimigoException extends JackutException {
    public FuncaoInvalidaInimigoException(String nomeInimigo) {
        super("Fun\uFFFD\uFFFDo inv\uFFFDlida: " + nomeInimigo + " \uFFFD seu inimigo.");
    }
}
