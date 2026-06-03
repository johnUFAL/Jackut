package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class NaoPodeEnviarRecadoASiMesmo extends RuntimeException{
    public NaoPodeEnviarRecadoASiMesmo() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}