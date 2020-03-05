package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.WalletException;
import parameters.ParamLibrary;

public class Resident extends Member {

	public Resident(float wallet) {
		super(wallet);
	}

	public void payBook(int numberOfDays) {
		int cost = 0;
		cost = costOfBorrowBook(numberOfDays);
		
		if (cost > getWallet()) {
			throw new WalletException("Votre solde est insuffisant pour l'emprunt de ce produit: " + getWallet() + ", cost: " + cost);
		}
		setWallet(getWallet() - cost);
		
	}
	public int costOfBorrowBook(int numberOfDays) {
		int costOfBorrow;
		if (numberOfDays <= ParamLibrary.getDaysBeforeLateResident()) {
			costOfBorrow = numberOfDays * ParamLibrary.getMembrePriceBeforeLate();
		} else {
			costOfBorrow = numberOfDays * ParamLibrary.getResidentPriceAfterLate()
					+ ParamLibrary.getMembrePriceBeforeLate()
							* (numberOfDays - ParamLibrary.getDaysBeforeLateResident());
		}
		return costOfBorrow;

	}

}
