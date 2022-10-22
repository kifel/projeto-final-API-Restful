package br.org.serratec.exception;

public class NomeUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NomeUsuarioException(String message) {
        super(message);
    }
}
