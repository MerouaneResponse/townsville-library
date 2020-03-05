package fr.d2factory.libraryapp.library;


import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import parameters.ParamLibrary;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Do not forget to consult the README.md :)
 */
public class LibraryTest {
    private Library library ;
    private BookRepository bookRepository;
    private static List<Book> books = new ArrayList<>();


    @SuppressWarnings("unchecked")
	@Before
	public void setup() throws ParseException, org.json.simple.parser.ParseException {
		bookRepository = new BookRepository();
		library = new LibraryImp(bookRepository);

		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("src\\test\\resources\\books.json")) {
			Object obj = jsonParser.parse(reader);
			JSONArray bookList = (JSONArray) obj;
			bookList.forEach(element -> {
				JSONObject bo = (JSONObject) element;
				String title = (String) bo.get("title");
				String author = (String) bo.get("author");
				long isb = (long) ((JSONObject) bo.get("isbn")).get("isbnCode");
				Book tmp = new Book(title, author, new ISBN(isb));
				books.add(tmp);
			});

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		bookRepository.addBooks(books);

	}

    @Test
    public void member_can_borrow_a_book_if_book_is_available(){
    	Member member1 = new Resident(1000);
		library.borrowBook(46578964513L, member1, LocalDate.now());
		Assert.assertSame(false,bookRepository.isAvailable(46578964513L));
    }

    @Test(expected = BookException.class)
    public void borrowed_book_is_no_longer_available(){
    	Member member1 = new Student(true, 10000);
		library.borrowBook(33264561467846L, member1, LocalDate.now());
    }

    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
    	Member member1 = new Resident(500);
		library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member1, LocalDate.now().minusDays(10));
		library.returnBook(books.get(0), member1);
		Assert.assertEquals(400, member1.getWallet(), 0);
    }

    @Test
    public void students_pay_10_cents_the_first_30days(){
    	Member member1 = new Resident(500);
		library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member1, LocalDate.now().minusDays(30));
		library.returnBook(books.get(0), member1);
		Assert.assertEquals(200, member1.getWallet(), 0);
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days(){
    	Member member1 = new Student(true, 999);
		library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member1,
				LocalDate.now().minusDays(ParamLibrary.getFirstYearFree()));
		library.returnBook(books.get(0), member1);
		Assert.assertEquals(999, member1.getWallet(), 0);
    }
    
    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
    	Member member20 = new Resident(7000);
		int days = ParamLibrary.getDaysBeforeLateResident() + 5;
		library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member20, LocalDate.now().minusDays(days));
		library.returnBook(books.get(0), member20);
		int price = days * ParamLibrary.getResidentPriceAfterLate()
				+ ParamLibrary.getMembrePriceBeforeLate() * (days - ParamLibrary.getDaysBeforeLateResident());

		Assert.assertEquals((7000 - price), member20.getWallet(), 1);
    }

    @Test(expected = HasLateBooksException.class)
    public void members_cannot_borrow_book_if_they_have_late_books(){
    	Member member1 = new Resident(5000);

		library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member1,
				LocalDate.now().minusDays(ParamLibrary.getDaysBeforeLateResident() + 1));
		library.borrowBook(books.get(1).getIsbn().getIsbnCode(), member1, LocalDate.now());
    }
}
