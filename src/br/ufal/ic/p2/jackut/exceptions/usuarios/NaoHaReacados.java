package br.ufal.ic.p2.jackut.exceptions.usuarios;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class NaoHaReacados extends JackutException {
    public NaoHaReacados() {
        super("N\uFFFDo h\uFFFD recados.");
    }
}
