package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AdicionarASiMesmo extends JackutException {
    public AdicionarASiMesmo() {
        super("Usu\uFFFDrio n\uFFFDo pode adicionar a si mesmo como amigo.");
    }
}
