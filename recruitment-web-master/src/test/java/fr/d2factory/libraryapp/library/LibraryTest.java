package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryTest {
	private Library library;
	private BookRepository bookRepository;
	private Member student1, student2, resident1, resident2;

	@Before
	public void setup() {
		List<Book> listBook = new ArrayList<>();
		bookRepository = new BookRepository();
		library = new LibraryFactory();

		listBook.add(new Book("geographique", "Mark", new ISBN(101)));
		listBook.add(new Book("geographique", "Adolf", new ISBN(102)));
		listBook.add(new Book("geographique", "jean", new ISBN(103)));
		listBook.add(new Book("geographique", "elisabette", new ISBN(104)));

		bookRepository.addrBooks(listBook);

	}

	@Test
	public void member_can_borrow_a_book_if_book_is_available() {
		setup();
		student1 = new Student(50, 2);
		Book book1 = BookRepository.findBook(101);
		assertNotNull("the book with isbn 101 is available", book1);
		LocalDate today = LocalDate.now();
		assertEquals(book1, library.borrowBook(101, student1, today));
	}

	@Test(expected = HasLateBooksException.class)
	public void member_can_not_borrow_a_book_if_already_borrowed() {
		setup();
		student2 = new Student(50, 2);
		LocalDate today = LocalDate.now();
		library.borrowBook(102, student2, today);
		library.borrowBook(103, student2, today);
	}

	@Test
	public void borrowed_book_is_no_longer_available() {
		LocalDate today = LocalDate.now();
		library.borrowBook(101, student1, today);
		assertNull("the book with isbn 101 is not available", BookRepository.findBook(101));
	}

	@Test
	public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() {
		resident1 = new Resident(100);
		resident1.payBook(25);
		assertEquals(97.5, resident1.getWallet(), 0);// student must have in his wallet 100 - 25 * 0.1 = 97.5
	}

	@Test
	public void students_pay_10_cents_the_first_30days() {
		student1 = new Student(50, 2);
		student1.payBook(20);
		assertEquals(48.0, student1.getWallet(), 0);// student must have in his wallet 50 - 20 * 0.1 = 48
	}

	@Test
	public void students_in_1st_year_are_not_taxed_for_the_first_15days() {
		student2 = new Student(30, 1);
		student2.payBook(10);
		assertEquals(30.0, student2.getWallet(), 0);// student must have in his wallet 30

	}

	@Test
	public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() {
		student2 = new Student(100, 3);
		student2.payBook(40);
		assertEquals(95.5, student2.getWallet(), 0);// student must have in his wallet 100 - (0.1*30 + 0.15*10)

	}

	@Test
	public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() {
		resident2 = new Resident(100);
		resident2.payBook(70);
		assertEquals(92.0, resident2.getWallet(), 0);// student must have in his wallet 100 - (60 * 0.1 + 0.20 * 10)
	}

	@Test
	public void members_cannot_borrow_book_if_they_have_late_books() {
		setup();
		student1 = new Student(50, 2);
		LocalDate today = LocalDate.now();
		library.borrowBook(101, student1, today);
		assertTrue(bookRepository.getBorrowedBooksMembers().containsKey(student1));

	}

	@Test
	public void return_book() {
		student1 = new Student(50, 2);
		LocalDate dateBorrow = LocalDate.of(2019, 05, 04);
		Book book1 = library.borrowBook(101, student1, dateBorrow);
		library.returnBook(book1, student1);

		assertEquals(49.0, student1.getWallet(), 0);//// student must have in his wallet 50 - 0.1 * 10
	}

}
