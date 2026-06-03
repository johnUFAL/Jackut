package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class NaoHaReacados extends RuntimeException{
    public NaoHaReacados() {
        super("N�o h� recados.");
    }
}
