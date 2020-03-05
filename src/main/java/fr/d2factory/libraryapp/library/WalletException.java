package fr.d2factory.libraryapp.library;

public class WalletException extends RuntimeException {
	/**
	 * thrown Exception when a member has not enough wallet
	 */
	private static final long serialVersionUID = 1L;

	public WalletException() {
		super();
	}

	public WalletException(String message, Throwable th) {
		super(message, th);
	}

	public WalletException(String message) {
		super(message);
	}

	public WalletException(Throwable th) {
		super(th);
	}
}
