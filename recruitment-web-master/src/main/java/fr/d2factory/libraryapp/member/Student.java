package fr.d2factory.libraryapp.member;

public class Student extends Member {
	private int year;

	public Student(float wallet, int year) {
		super(wallet);
		this.year = year;
	}

	/* (non-Javadoc)
	 * @see fr.d2factory.libraryapp.member.Member#payBook(int)
	 */
	@Override
	public void payBook(int numberOfDays) {
		float tarif = 0;
		if (numberOfDays <= 30) {
			if (this.year != 1 || numberOfDays > 15) {
				tarif = (float) (numberOfDays * 0.1);
			}
		} else {
			tarif = (float) ((30 * 0.1) + (numberOfDays - 30) * 0.15);
		}

		this.setWallet(this.getWallet() - tarif);
	}
}
