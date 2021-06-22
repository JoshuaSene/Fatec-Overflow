package br.gov.sp.fatec.domain.exception;

public class FileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4726985984736825857L;

	public FileNotFoundException() {}
	
	public FileNotFoundException(String message) {
		super(message);
	}
	
	public FileNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public FileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
