package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class AtributoNaoPreenchido extends RuntimeException {
    public AtributoNaoPreenchido() {
        super("Atributo n\uFFFDo preenchido.");
    }
}
