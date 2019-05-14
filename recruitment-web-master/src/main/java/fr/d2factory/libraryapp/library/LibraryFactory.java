package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;

/**
 * Library Factory is the implementation of interface Library
 * it's manage all the function of borrowing and return a book
 */
public class LibraryFactory implements Library {

	/* (non-Javadoc)
	 * @see fr.d2factory.libraryapp.library.Library#borrowBook(long, fr.d2factory.libraryapp.member.Member, java.time.LocalDate)
	 */
	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
		Book book = new Book();
		if (!hasBook(member)) {
			book = BookRepository.findBook(isbnCode);
			BookRepository.saveBookBorrow(book, borrowedAt, member);
		} else {
			throw new HasLateBooksException();
		}
		return book;
	}

	/**
	 * @param borrowedAt
	 * 
	 * calculate difference between borrowing book and today
	 */
	public int calculateTimeBorrowing(LocalDate borrowedAt) {
		LocalDate today = LocalDate.now();
		return (int) ChronoUnit.DAYS.between(borrowedAt, today);

	}

	/**
	 * @param member
	 * 
	 * return true if the member has a book, false else
	 */
	public boolean hasBook(Member member) {
		return (BookRepository.findBorrowedBookMember(member) != null) ? true : false;
	}

	/* (non-Javadoc)
	 * @see fr.d2factory.libraryapp.library.Library#returnBook(fr.d2factory.libraryapp.book.Book, fr.d2factory.libraryapp.member.Member)
	 */
	@Override
	public void returnBook(Book book, Member member) {
		int nbrOfDays = 0;
		LocalDate borrowedAt = BookRepository.findBorrowedBookDate(book);
		nbrOfDays = calculateTimeBorrowing(borrowedAt);
		member.payBook(nbrOfDays);
	}
}
