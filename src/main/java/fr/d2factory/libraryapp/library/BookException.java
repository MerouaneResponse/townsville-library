package fr.d2factory.libraryapp.library;

public class BookException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BookException() {
		super();
	}

	public BookException(String message, Throwable th) {
		super(message, th);
	}

	public BookException(String message) {
		super(message);
	}

	public BookException(Throwable th) {
		super(th);
	}

}
