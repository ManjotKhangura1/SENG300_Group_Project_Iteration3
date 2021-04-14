package Software;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

//Price is determined by the weight of the item on the scale and the price as determined in the database  
public class CustomerLookUpProduct {
	//local variables
	private BigDecimal price;
	private String description; 
	private BigDecimal weight;
	//Local objects
	public final ElectronicScale scale;
	public FinishesAddingItems done;
	public BaggingArea baged;
	
	public static Map<String, PLUCodedProduct> PLUPRODUCTS;
	
	/**
	 * Constructor to look up a PLU item through a string input
	 * @param station
	 * @param database
	 * @param d
	 */
	public CustomerLookUpProduct(SelfCheckoutStation station, Map<String, PLUCodedProduct> database, FinishesAddingItems d, BaggingArea b) {
		if(station == null) {
			throw new SimulationException(new NullPointerException("station is null"));
		}
		this.scale = station.scale;
		CustomerLookUpProduct.PLUPRODUCTS = database;
		done = d;
		baged = b;
	}

	/**
	 * Lookup the plu in the database and get the price by multiplying with the weight 
	 * @param pluCode
	 * @throws OverloadException
	 */
	public void Lookup(String pluCode) throws OverloadException {
		if (pluCode == null) {
			throw new SimulationException(new NullPointerException("PLU code is null"));
		}
		try {
			description = PLUPRODUCTS.get(pluCode).getDescription();
			// The weight on the scale is in grams so I converted to kg because the price of the products in PLUCodedProduct is per kg 
			price = BigDecimal.valueOf(scale.getCurrentWeight()).divide(new BigDecimal(1000)).multiply(PLUPRODUCTS.get(pluCode).getPrice());
			weight = BigDecimal.valueOf(scale.getCurrentWeight());
			
			//update totals in FinishesAddingItems and BaaggingArea
			done.updateTotals(description, price, weight);
			baged.setWeightScanned(weight);
			
		}catch(OverloadException e) {
			throw new OverloadException("Item overweight! Please remove."); //from scale.getCurrentWeight
		}catch(Exception e) {
			throw new SimulationException(new NullPointerException("Not in database. Please try a different description."));
		}
	}
}
