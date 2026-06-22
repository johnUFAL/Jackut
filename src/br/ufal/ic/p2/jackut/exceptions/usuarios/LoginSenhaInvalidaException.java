package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class LoginSenhaInvalidaException extends JackutException {
    public LoginSenhaInvalidaException() {
        super("Login ou senha inv\uFFFDlidos.");
    }
}
