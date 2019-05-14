package fr.d2factory.libraryapp.member;

public class Resident extends Member {

	public Resident(float wallet) {
		super(wallet);
	}

	/* (non-Javadoc)
	 * @see fr.d2factory.libraryapp.member.Member#payBook(int)
	 */
	@Override
	public void payBook(int numberOfDays) {
		float tarif = 0;
		if (numberOfDays <= 60) {
			tarif = (float) (numberOfDays * 0.1);
		} else {
			tarif = (float) ((60 * 0.1) + (numberOfDays - 60) * 0.2);
		}
		this.setWallet(this.getWallet() - tarif);
	}

}
