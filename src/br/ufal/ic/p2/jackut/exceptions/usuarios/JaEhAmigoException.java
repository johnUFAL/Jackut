package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta adicionar um usuário que já é amigo. */
public class JaEhAmigoException extends JackutException {
    public JaEhAmigoException() {
        super("Usu\uFFFDrio j\uFFFD est\uFFFD adicionado como amigo.");
    }
}
