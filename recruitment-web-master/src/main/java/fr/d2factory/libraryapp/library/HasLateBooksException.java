package fr.d2factory.libraryapp.library;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class HasLateBooksException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  throw exception and print the error message
	 */
	public HasLateBooksException() {
		System.out.println("Member has already a book, he can not borrow");		
	}
}
