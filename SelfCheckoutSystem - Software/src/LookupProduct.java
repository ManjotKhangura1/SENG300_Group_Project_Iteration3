import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.PLUCodedProduct;


// Attendant looks up product using the PLU code to give a price check for the customer when there is no barcode
// Price is determined by the weight of the item on the scale and the price as determined in the database  
public class LookupProduct {
	//local variables
	private BigDecimal price;
	private String name;
	private String description; 
	private BigDecimal weight;
	//Local objects
	public final ElectronicScale scale;
	public FinishesAddingItems done;
	
	public static Map<String, PLUCodedProduct> PLUPRODUCTS = new HashMap<>();
	
	/**
	 * Constructor to look up a PLU item through a string input
	 * @param station
	 * @param database
	 * @param d
	 */
	public LookupProduct(SelfCheckoutStation station, Map<String, PLUCodedProduct> database, FinishesAddingItems d) {
		this.scale = station.scale;
		PLUPRODUCTS = database;
		done = d;
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
		}
		catch(Exception e) {
			throw new SimulationException(new NullPointerException("Something went wrong. Try again. "));
		}
		//update totals in finishesAddingItems
		done.setList(description);
		done.setPrice(price);
		done.setWeight(weight);
	}
}