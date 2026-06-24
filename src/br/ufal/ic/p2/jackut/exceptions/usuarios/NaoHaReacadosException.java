package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta acessar recados que não existem. */
public class NaoHaReacadosException extends JackutException {
    public NaoHaReacadosException() {
        super("N\uFFFDo h\uFFFD recados.");
    }
}
