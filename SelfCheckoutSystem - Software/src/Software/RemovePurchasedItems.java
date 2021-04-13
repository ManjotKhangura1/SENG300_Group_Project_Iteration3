package Software;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

public class RemovePurchasedItems {
	ScanItem scan;
	boolean isEnabled = false;
	boolean itemsTaken = false;
	
	/**
	 * Constructor to re-enable the self checkout station based on the balance being paid and the items removed off the bagging area
	 * @param SelfCheckoutStation 
	 * @param ScanItem
	 * @param baggingArea
	 * @param transaction
	 */
	public RemovePurchasedItems(SelfCheckoutStation station, ScanItem scan, BaggingArea baggingArea, GiveChange transaction) {
		
		if(station == null) throw new SimulationException(new NullPointerException("Station is null"));
		if(scan == null) throw new SimulationException(new NullPointerException("Scanner is null"));
		if(baggingArea == null) throw new SimulationException(new NullPointerException("Bagging area is null"));
		if(transaction == null) throw new SimulationException(new NullPointerException("Transaction is null"));
		
		if ((transaction.getTotalOwed() <= transaction.getTotalPaid())) {
			scan.handheld.enable(); //re-enable the hand held scanner to allow another customer to come through and use the station
			scan.main.enable(); //re-enable the main scanner to allow another customer to come through and use the station
			isEnabled = true;
			//System.out.println("Thank you for your purchase. You may now take your items out of the bagging area.");
		}
		
		if (baggingArea.getWeightBaggingArea().compareTo(BigDecimal.valueOf(0.0)) == 0.0) {
			itemsTaken = true;
			//System.out.println("Come again!");
			baggingArea.baggingArea.enable(); //re-enable the bagging area to allow another customer to come through and use the station
		}
	
	}
	
	/**
	 * Tells whether the main scanner and handheld scanner are re-enabled
	 * @return isEnabled
	 */
	public boolean getIsEnabled() {
		return isEnabled;
	}
	
	/**
	 * Tells whether the items have been taken yet and whether the bagging area is re-enabled
	 * @return itemsTaken
	 */
	public boolean getItemsTaken() {
		return itemsTaken;
	}
	
	
}
