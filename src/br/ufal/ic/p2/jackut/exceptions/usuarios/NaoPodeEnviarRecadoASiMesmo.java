package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class NaoPodeEnviarRecadoASiMesmo extends JackutException {
    public NaoPodeEnviarRecadoASiMesmo() {
        super("Usu\uFFFDrio n\uFFFDo pode enviar recado para si mesmo.");
    }
}
