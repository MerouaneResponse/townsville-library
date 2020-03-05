package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
	
	/**
     * An initial sum of money the member has
     */
    private float wallet;
    protected int numberDaysBrrow;
    protected Book borrowBook;

    public Member(float wallet) {
		super();
		this.wallet = wallet;
	}

	
    public Book getBorrowBook() {
		return borrowBook;
	}

	public void setBorrowBook(Book borrowBook) {
		this.borrowBook = borrowBook;
	}

	/**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);

    public int getNumberDaysBrrow() {
		return numberDaysBrrow;
	}

	public void setNumberDaysBrrow(int numberDaysBrrow) {
		this.numberDaysBrrow = numberDaysBrrow;
	}

	public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }
}
