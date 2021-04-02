import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.*;

import java.util.ArrayList;

import org.lsmr.selfcheckout.*;


public class FinishesAddingItems {
	ReceiptPrinter printer;
	ScanItem scan;
	boolean isEnabled;
	
	PayWithCoin coin;
	PayWithBanknote banknote;
	PayWithDebit debit;
	PayWithCreditCard credit;
	
	private double finalPrice;
	private ArrayList<String> finalList;

	/**
	 * Constructor to finish transaction
	 * @param SelfCheckoutStation 
	 * @param ScanItem
	 */
	public FinishesAddingItems(SelfCheckoutStation station, ScanItem scan, BaggingArea bags) {
		
		if(station == null) throw new SimulationException(new NullPointerException("station is null"));
		if(scan == null) throw new SimulationException(new NullPointerException("scanner is null"));
		if(bags == null) throw new SimulationException(new NullPointerException("bagging area is null"));
		
		//if the user is done adding items then the scanners and with weigh area do not need to continue updating
		scan.handheld.disable(); //disable the hand held scanner
		scan.main.disable(); //disable the main scanner
		bags.baggingArea.disable(); //disable the weigh scale
		
		finalPrice = scan.getTotalPrice();
		finalList = scan.getTotalList();
		
		coin = new PayWithCoin(station);
		banknote = new PayWithBanknote(station);
		debit = new PayWithDebit(station);
		credit = new PayWithCreditCard(station, null);
	}
	 
	/*
	 * here we would implement the initialization of the pay class which we will make next iteration
	 */
	
	/**
	 * @return the final price
	 */
	public double getPrice() {
		return finalPrice;
	}
	
	/**
	 * @return the final list of items
	 */
	public ArrayList<String> getList(){
		return finalList;
	}
}
