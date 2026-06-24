package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta criar um usuário com uma senha inválida. */
public class SenhaInvalidaException extends JackutException {
    public SenhaInvalidaException() {
        super("Senha inv\uFFFDlida.");
    }
}
