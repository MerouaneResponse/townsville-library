package fr.d2factory.libraryapp.library;

/**
 * thrown Exception when a member is late to render a book
 */
public class HasLateBooksException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public HasLateBooksException() {
		super();
	}

	public HasLateBooksException(String message, Throwable th) {
		super(message, th);
	}

	public HasLateBooksException(String message) {
		super(message);
	}

	public HasLateBooksException(Throwable th) {
		super(th);
	}
}
