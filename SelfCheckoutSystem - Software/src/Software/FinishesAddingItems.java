package Software;

import org.lsmr.selfcheckout.devices.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FinishesAddingItems {
	//local variables
	boolean isEnabled;
	private BigDecimal finalWeight;
	private BigDecimal finalPrice;
	//Local object
	private ArrayList<String> finalList;
	private BaggingArea bags;
	private SelfCheckoutStation station;
	//item tracker
	private Map<String, ArrayList<BigDecimal>> tracker = new HashMap<>();

	/**
	 * Constructor to finish transaction
	 * @param SelfCheckoutStation 
	 * @param ScanItem
	 */
	public FinishesAddingItems(SelfCheckoutStation station, BaggingArea bags) {
		if(station == null) throw new SimulationException(new NullPointerException("station is null"));
		if(station.mainScanner == null) throw new SimulationException(new NullPointerException("scanner is null"));
		if(station.handheldScanner == null) throw new SimulationException(new NullPointerException("scanner is null"));
		if(bags == null) throw new SimulationException(new NullPointerException("bagging area is null"));
		
		this.station = station;
		this.bags = bags;
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
		finalWeight.add(weight); 
		finalPrice.add(price); 
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
		finalList.remove(name);
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
	
	public void finish(){
		//if the user is done adding items then the scanners and with weigh area do not need to continue updating
				station.mainScanner.disable(); //scan.handheld.disable(); //disable the hand held scanner
				station.handheldScanner.disable();//scan.main.disable(); //disable the main scanner
				bags.baggingArea.disable(); //disable the weigh scale
	}	
}
