package br.gov.sp.fatec.domain.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 418605523532072612L;

	public UserNotFoundException() {}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
