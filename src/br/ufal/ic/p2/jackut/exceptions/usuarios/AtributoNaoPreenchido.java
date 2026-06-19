package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AtributoNaoPreenchido extends JackutException {
    public AtributoNaoPreenchido() {
        super("Atributo n\uFFFDo preenchido.");
    }
}
