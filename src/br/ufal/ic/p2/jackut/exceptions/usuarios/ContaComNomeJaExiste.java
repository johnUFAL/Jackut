package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class ContaComNomeJaExiste extends RuntimeException {
    public ContaComNomeJaExiste() {
        super("Conta com esse nome já existe.");
    }
}
