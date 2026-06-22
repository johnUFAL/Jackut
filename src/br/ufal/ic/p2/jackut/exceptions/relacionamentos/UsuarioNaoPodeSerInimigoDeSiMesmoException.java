package br.ufal.ic.p2.jackut.exceptions.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class UsuarioNaoPodeSerInimigoDeSiMesmoException extends JackutException {
    public UsuarioNaoPodeSerInimigoDeSiMesmoException() {
        super("Usu\uFFFDrio n\uFFFDo pode ser inimigo de si mesmo.");
    }
}
