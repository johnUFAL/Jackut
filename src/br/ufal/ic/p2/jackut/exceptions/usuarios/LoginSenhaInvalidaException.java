package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta fazer login com credenciais inválidas. */
public class LoginSenhaInvalidaException extends JackutException {
    public LoginSenhaInvalidaException() {
        super("Login ou senha inv\uFFFDlidos.");
    }
}
