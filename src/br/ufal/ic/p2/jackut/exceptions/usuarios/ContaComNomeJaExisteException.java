package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ContaComNomeJaExisteException extends JackutException {
    public ContaComNomeJaExisteException() {
        super("Conta com esse nome j\uFFFD existe.");
    }
}
