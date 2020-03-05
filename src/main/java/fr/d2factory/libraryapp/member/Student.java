package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.WalletException;
import parameters.ParamLibrary;

public class Student extends Member {
	private boolean firstYear;

	
	public Student(boolean firstYear, int wallet) {
		super(wallet);
		this.firstYear = firstYear;
		numberDaysBrrow = ParamLibrary.getDaysBeforeLateStudent();
	}
	
	public boolean isFirstYear() {
		return firstYear;
	}
	
	public void payBook(int numberOfDays) {
		int cost = 0;

		if (firstYear)
			numberOfDays -= ParamLibrary.getFirstYearFree();
		cost = costOfBorrowBook(numberOfDays);
		if (cost > getWallet()) {
			throw new WalletException("Votre solde est insuffisant pour l'emprunt de ce produit: " + getWallet() + ", cost: " + cost);
		}
		setWallet(getWallet() - cost);
	
		
	}
	
	public int costOfBorrowBook(int numberOfDays) {
		int prixEmprunt;
		prixEmprunt = numberOfDays * ParamLibrary.getMembrePriceBeforeLate();
		
		return prixEmprunt;
	}

}
