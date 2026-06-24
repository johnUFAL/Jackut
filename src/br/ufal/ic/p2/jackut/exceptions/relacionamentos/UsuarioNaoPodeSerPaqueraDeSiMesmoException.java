package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta definir um usuário como paquera de si mesmo. */
public class UsuarioNaoPodeSerPaqueraDeSiMesmoException extends JackutException {
    public UsuarioNaoPodeSerPaqueraDeSiMesmoException() {super("Usu\uFFFDrio n\uFFFDo pode ser paquera de si mesmo.");}
}
