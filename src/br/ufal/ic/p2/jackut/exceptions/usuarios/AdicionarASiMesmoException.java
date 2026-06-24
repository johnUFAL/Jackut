package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta adicionar a si mesmo como amigo. */
public class AdicionarASiMesmoException extends JackutException {
    public AdicionarASiMesmoException() {
        super("Usu\uFFFDrio n\uFFFDo pode adicionar a si mesmo como amigo.");
    }
}
