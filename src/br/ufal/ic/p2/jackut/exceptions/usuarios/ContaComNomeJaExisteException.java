package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta criar uma conta com um nome que já existe. */
public class ContaComNomeJaExisteException extends JackutException {
    public ContaComNomeJaExisteException() {
        super("Conta com esse nome j\uFFFD existe.");
    }
}
