package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class SenhaInvalida extends RuntimeException {
    public SenhaInvalida() {
        super("Senha inválida.");
    }
}
