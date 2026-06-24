package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta criar um usuário sem preencher um atributo obrigatório. */
public class AtributoNaoPreenchidoException extends JackutException {
    public AtributoNaoPreenchidoException() {
        super("Atributo n\uFFFDo preenchido.");
    }
}
