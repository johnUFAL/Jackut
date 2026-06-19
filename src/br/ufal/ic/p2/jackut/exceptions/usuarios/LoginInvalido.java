package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class LoginInvalido extends JackutException {
    public LoginInvalido() {
        super("Login inv\uFFFDlido.");
    }
}
