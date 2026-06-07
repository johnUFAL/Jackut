package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class AdicionarASiMesmo extends RuntimeException {
    public AdicionarASiMesmo() {
        super("Usu\uFFFDrio n\uFFFDo pode adicionar a si mesmo como amigo.");
    }
}
