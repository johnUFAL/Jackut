package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta acessar um usuário que não está cadastrado. */
public class UsuarioNaoCadastradoException extends JackutException {
    public UsuarioNaoCadastradoException() {
        super("Usu\uFFFDrio n\uFFFDo cadastrado.");
    }
}
