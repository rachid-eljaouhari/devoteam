package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.d2factory.libraryapp.member.Member;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
	private static Map<ISBN, Book> availableBooks = new HashMap<>();
	/**
	 * 
	 */
	private static Map<Book, LocalDate> borrowedBooks = new HashMap<>();
	private static Map<Member, Book> borrowedBooksMembers = new HashMap<>();

	/**
	 * @return Map<Member, Book>
	 */
	public Map<Member, Book> getBorrowedBooksMembers() {
		return borrowedBooksMembers;
	}

	/**
	 * @param borrowedBooksMembers
	 */
	public void setBorrowedBooksMembers(Map<Member, Book> borrowedBooksMembers) {
		BookRepository.borrowedBooksMembers = borrowedBooksMembers;
	}

	/**
	 * @param books
	 */
	public void addrBooks(List<Book> books) {
		for (int i = 0; i < books.size(); i++) {
			availableBooks.put(books.get(i).getIsbn(), books.get(i));
		}

	}

	/**
	 * @param isbnCode
	 * 
	 * find book in the available list
	 */
	public static Book findBook(long isbnCode) {
		Book book = null;
		ISBN isbnObject = new ISBN(isbnCode);
		if (availableBooks.containsKey(isbnObject)) {
			book = availableBooks.get(isbnObject);
		}
		return book;
	}

	/**
	 * @param book
	 * @param borrowedAt
	 * @param member
	 */
	public static void saveBookBorrow(Book book, LocalDate borrowedAt, Member member) {
		borrowedBooks.put(book, borrowedAt);
		borrowedBooksMembers.put(member, book);
		availableBooks.remove(book.getIsbn());
	}

	/**
	 * @param book
	 * 
	 * find the date of borrowed book
	 */
	public static LocalDate findBorrowedBookDate(Book book) {
		LocalDate BorrowedBookDate = null;
		if (borrowedBooks.containsKey(book)) {
			BorrowedBookDate = borrowedBooks.get(book);
		}
		return BorrowedBookDate;
	}

	/**
	 * @param member
	 * 
	 * find the book which borrowed by a member
	 */
	public static Book findBorrowedBookMember(Member member) {
		Book book = null;
		if (borrowedBooksMembers.containsKey(member)) {
			book = borrowedBooksMembers.get(member);
		}
		return book;
	}

}
