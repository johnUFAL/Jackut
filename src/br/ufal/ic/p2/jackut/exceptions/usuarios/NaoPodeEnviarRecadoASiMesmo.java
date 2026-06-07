package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class NaoPodeEnviarRecadoASiMesmo extends RuntimeException {
    public NaoPodeEnviarRecadoASiMesmo() {
        super("Usu\uFFFDrio n\uFFFDo pode enviar recado para si mesmo.");
    }
}
