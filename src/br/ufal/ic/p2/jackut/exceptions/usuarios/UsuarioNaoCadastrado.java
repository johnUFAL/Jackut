package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class UsuarioNaoCadastrado extends RuntimeException {
    public UsuarioNaoCadastrado() {
        super("Usu\uFFFDrio n\uFFFDo cadastrado.");
    }
}
