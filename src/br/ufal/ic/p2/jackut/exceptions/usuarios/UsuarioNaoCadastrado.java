package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class UsuarioNaoCadastrado extends JackutException {
    public UsuarioNaoCadastrado() {
        super("Usu\uFFFDrio n\uFFFDo cadastrado.");
    }
}
