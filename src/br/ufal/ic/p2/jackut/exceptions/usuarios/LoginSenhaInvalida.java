package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class LoginSenhaInvalida extends JackutException {
    public LoginSenhaInvalida() {
        super("Login ou senha inv\uFFFDlidos.");
    }
}
