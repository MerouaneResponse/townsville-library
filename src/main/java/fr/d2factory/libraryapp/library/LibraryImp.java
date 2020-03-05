package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;

public class LibraryImp implements Library {
	
	
	BookRepository bookRepository;

	public LibraryImp(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt)  {
		
		if (member.getBorrowBook() != null) {
			bookRepository.findBorrowedBookDate(member.getBorrowBook()).ifPresent(borrowedDate -> {
				if (ChronoUnit.DAYS.between(borrowedDate, LocalDate.now()) > member.getNumberDaysBrrow()) {
					throw new HasLateBooksException();
				}
			});
		}

		Book borrowedBook = bookRepository.findBook(isbnCode).orElseThrow(() -> new BookException("le produit choisi n'est pas disponible pour le moment :"));
		bookRepository.saveBookBorrow(borrowedBook, borrowedAt);
		member.setBorrowBook(borrowedBook);
		return borrowedBook;
	}

	@Override
	public void returnBook(Book book, Member member) {
		
		bookRepository.findBorrowedBookDate(book).ifPresent(borrowedDate -> {
			int keepingDays = (int) ChronoUnit.DAYS.between(borrowedDate, LocalDate.now());
			member.payBook(keepingDays);
		});
		
	}

}
