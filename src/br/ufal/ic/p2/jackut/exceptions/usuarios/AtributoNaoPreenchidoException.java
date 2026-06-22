package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AtributoNaoPreenchidoException extends JackutException {
    public AtributoNaoPreenchidoException() {
        super("Atributo n\uFFFDo preenchido.");
    }
}
