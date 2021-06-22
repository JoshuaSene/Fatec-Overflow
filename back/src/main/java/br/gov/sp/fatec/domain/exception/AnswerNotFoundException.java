package br.gov.sp.fatec.domain.exception;

public class AnswerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1610081932549676269L;

	public AnswerNotFoundException() {}
	
	public AnswerNotFoundException(String message) {
		super(message);
	}
	
	public AnswerNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public AnswerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
