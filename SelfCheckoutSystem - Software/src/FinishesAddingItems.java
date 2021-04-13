import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.lsmr.selfcheckout.*;


public class FinishesAddingItems {
	boolean isEnabled;
	
	private BigDecimal finalPrice;
	private ArrayList<String> finalList;
	private BigDecimal finalWeight;

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
	}
	 
	/**
	 * add the price given to the total price
	 * @param BigDecimal price
	 */
	public void setPrice(BigDecimal price) {
		finalPrice.add(price);
	}
	
	/**
	 * add the new item name to the item list
	 * @param String name
	 */
	public void setList(String name) {
		finalList.add(name);
	}
	
	/**
	 * add the new item weight to the total weight
	 * @param weight
	 */
	public void setWeight(BigDecimal weight) {
		finalWeight.add(weight);
	}
	
	/**
	 * @return the final price
	 */
	public double getPrice() {
		return finalPrice.doubleValue();
	}
	
	/**
	 * @return the final list of items
	 */
	public ArrayList<String> getList(){
		return finalList;
	}
	
	public double getWeight() {
		return finalWeight.doubleValue();	}
	
}
