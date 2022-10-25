package br.org.serratec.exception;

public class ProdutoIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProdutoIdException(String message) {
        super(message);
    }
    
}
