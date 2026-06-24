package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta definir um usuário como fa de si mesmo. */
public class UsuarioNaoPodeSerFaDeSiMesmoException extends JackutException {
    public UsuarioNaoPodeSerFaDeSiMesmoException() {super("Usu\uFFFDrio n\uFFFDo pode ser f\uFFFD de si mesmo.");}
}
