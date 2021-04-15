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
	private ArrayList<String> finalList = new ArrayList<String>();
	private ArrayList<BigDecimal> temp = new ArrayList<BigDecimal>();
	private SelfCheckoutStation station;
	private AddOwnBag aob;
	private BaggingArea bags;
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
		try {
			this.aob = new AddOwnBag(station);
		} catch (OverloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finalWeight = new BigDecimal(0.0);
		finalPrice = new BigDecimal(0.0);
		finalList = new ArrayList<>();
	}
	 
	/**
	 * updates the tracker that keeps track of each items name, price and weight
	 * @param String name
	 * @param BigDecimal price
	 * @param BigDecimal weight
	 */
	public void updateTotals(String name, BigDecimal price, BigDecimal weight) {

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
		finalPrice = finalPrice.subtract(tracker.get(name).get(0));
		finalWeight = finalWeight.subtract(tracker.get(name).get(1));
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
	
	/**
	 * 
	 * @param bag
	 * @param name of the bag(must be unique)
	 * @throws DisabledException
	 * @throws OverloadException
	 */
	public void addOwnBag(Bag bag, String name) throws DisabledException, OverloadException {
		aob.addBag(bag);
		bags.updateWeight();
		updateTotals(name, BigDecimal.valueOf(0.10), BigDecimal.valueOf(bag.getWeight()));
	}
	
	/**
	 * 
	 * @param bag
	 * @param name (name of the bag you are trying to remove must be associated together)
	 * @throws DisabledException
	 * @throws OverloadException
	 */
	public void removeOwnBag(Bag bag, String name) throws DisabledException, OverloadException {
		aob.removeBag(bag);
		bags.updateWeight();
		removeItem(name);
	}
	
	public Map<String, ArrayList<BigDecimal>> getTracker(){
		return tracker;
	}
	
	
	public void finish(){
		//if the user is done adding items then the scanners and with weigh area do not need to continue updating
				station.mainScanner.disable(); //disable the hand held scanner
				station.handheldScanner.disable();//disable the main scanner
				bags.baggingArea.disable(); //disable the weigh scale
	}	
}
