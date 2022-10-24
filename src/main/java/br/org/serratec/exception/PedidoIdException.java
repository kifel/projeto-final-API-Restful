package br.org.serratec.exception;

public class PedidoIdException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public PedidoIdException(String message) {
        super(message);
    }
}
