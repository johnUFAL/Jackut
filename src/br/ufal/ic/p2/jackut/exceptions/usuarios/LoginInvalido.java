package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class LoginInvalido extends RuntimeException {
    public LoginInvalido() {
        super("Login inválido.");
    }
}
