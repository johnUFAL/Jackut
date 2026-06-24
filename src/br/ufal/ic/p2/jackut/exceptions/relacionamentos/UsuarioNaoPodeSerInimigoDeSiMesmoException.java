package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta definir um usuário como inimigo de si mesmo. */
public class UsuarioNaoPodeSerInimigoDeSiMesmoException extends JackutException {
    public UsuarioNaoPodeSerInimigoDeSiMesmoException() {
        super("Usu\uFFFDrio n\uFFFDo pode ser inimigo de si mesmo.");
    }
}
