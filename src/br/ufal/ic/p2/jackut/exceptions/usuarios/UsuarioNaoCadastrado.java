package br.ufal.ic.p2.jackut.exceptions.usuarios;

public class UsuarioNaoCadastrado extends RuntimeException {
    public UsuarioNaoCadastrado() {
        super("Usuário não cadastrado.");
    }
}
