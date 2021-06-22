package br.gov.sp.fatec.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3489791570203889372L;

	public PostNotFoundException() {}
	
	public PostNotFoundException(String message) {
		super(message);
	}
	
	public PostNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public PostNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
