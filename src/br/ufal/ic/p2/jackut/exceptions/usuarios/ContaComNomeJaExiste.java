package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ContaComNomeJaExiste extends JackutException {
    public ContaComNomeJaExiste() {
        super("Conta com esse nome j\uFFFD existe.");
    }
}
