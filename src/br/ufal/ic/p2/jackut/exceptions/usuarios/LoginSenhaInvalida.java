package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class LoginSenhaInvalida extends RuntimeException {
    public LoginSenhaInvalida() {
        super("Login ou senha inv\uFFFDlidos.");
    }
}
