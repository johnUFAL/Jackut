package br.ufal.ic.p2.jackut.exceptions.comunidades;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

/** Exceção lançada quando se tenta incluir um usuário que já faz parte da comunidade. */
public class UsuarioJaFazParteDaComunidadeException extends JackutException{
    public UsuarioJaFazParteDaComunidadeException() {super("Usuario j\uFFFD faz parte dessa comunidade.");}
}
