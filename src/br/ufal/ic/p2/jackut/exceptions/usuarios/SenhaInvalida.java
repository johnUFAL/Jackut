package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class SenhaInvalida extends JackutException {
    public SenhaInvalida() {
        super("Senha inv\uFFFDlida.");
    }
}
