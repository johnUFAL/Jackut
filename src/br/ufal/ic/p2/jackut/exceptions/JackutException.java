package br.ufal.ic.p2.jackut.exceptions;

/**
 * Classe base para todas as exceções personalizadas do sistema Jackut.
 * Facilita o agrupamento e o tratamento hierárquico de erros de domínio da aplicação.
 */
public class JackutException extends Exception {

    /**
     * Constrói a exceção com uma mensagem de erro específica.
     * @param mensagem A mensagem descritiva do erro, que será capturada pelos testes de aceitação.
     */
    public JackutException(String mensagem) {
        super(mensagem);
    }
}
