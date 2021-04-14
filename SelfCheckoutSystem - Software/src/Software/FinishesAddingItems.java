package Software;

import org.lsmr.selfcheckout.devices.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FinishesAddingItems {
	//local variables
	boolean isEnabled;
	private BigDecimal finalWeight = new BigDecimal("0.00");
	private BigDecimal finalPrice = new BigDecimal("0.00");
	//Local object
	private ArrayList<String> finalList = new ArrayList<>();
	//item tracker
	private Map<String, ArrayList<BigDecimal>> tracker = new HashMap<>();

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
	 * updates the tracker that keeps track of each items name, price and weight
	 * @param String name
	 * @param BigDecimal price
	 * @param BigDecimal weight
	 */
	public void updateTotals(String name, BigDecimal price, BigDecimal weight) {
		ArrayList<BigDecimal> temp = new ArrayList<BigDecimal>();
		temp.add(price); //element 0
		temp.add(weight); //element 1
		finalWeight = finalWeight.add(weight); 
		finalPrice = finalPrice.add(price); 
		finalList.add(name); 
		tracker.put(name, temp);
	}
	
	/**
	 * removes the name price and weight of the given parameter
	 * @param String name
	 */
	public void removeItem(String name) {
		finalPrice.subtract(tracker.get(name).get(0));
		finalWeight.subtract(tracker.get(name).get(1));
		tracker.remove(name);
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
		return finalWeight.doubleValue();	
	}
	
}
