package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta enviar um recado para si mesmo. */
public class NaoPodeEnviarRecadoASiMesmoException extends JackutException {
    public NaoPodeEnviarRecadoASiMesmoException() {
        super("Usu\uFFFDrio n\uFFFDo pode enviar recado para si mesmo.");
    }
}
