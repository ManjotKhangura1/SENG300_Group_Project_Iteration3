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
	private double price;
	private String plu;
	public final ElectronicScale scale;
	private String description; 
	
	public static Map<String, PLUCodedProduct> PLUPRODUCTS = new HashMap<>();
	
	public void initializePLUProductDatabase() throws SimulationException {
		
		PriceLookupCode plu1 = new PriceLookupCode("14040");
		PriceLookupCode plu2 = new PriceLookupCode("97310");
		PriceLookupCode plu3 = new PriceLookupCode("55589");
		PriceLookupCode plu4 = new PriceLookupCode("30897");
		
		
		PLUCodedProduct onions = new PLUCodedProduct(plu1, "These are delicious onions imported from California!", new BigDecimal("2.00"));
		PLUCodedProduct potatoes = new PLUCodedProduct(plu2, "These potatoes are the most nutritionally diverse root vegetable grown in Idaho!", new BigDecimal("1.97"));
		PLUCodedProduct apples = new PLUCodedProduct(plu3, "These apples were grown with utmost care in the valleys of British Columbia!", new BigDecimal("4.37"));
		PLUCodedProduct oranges = new PLUCodedProduct(plu4, "These oranges are the orangest oranges from Florida!", new BigDecimal("2.50"));
		
		PLUPRODUCTS.put(plu1.toString(), onions); 
		PLUPRODUCTS.put(plu2.toString(), potatoes); 
		PLUPRODUCTS.put(plu3.toString(), apples);
		PLUPRODUCTS.put(plu4.toString(), oranges); 
		
	}
	
	/**
	 * Constructor to look up a PLU item through a string input
	 * @param SelfCheckoutStation station
	 * @param String pluCode
	 */
	public LookupProduct(SelfCheckoutStation station, String pluCode) {

		if (pluCode == null) {
			throw new SimulationException(new NullPointerException("PLU code is null"));
		}
		
		this.scale = station.scale;
		this.plu = pluCode;

	}

	/**
	 * Lookup the plu in the database and get the price by multiplying with the weight 
	 */
	public void Lookup() throws OverloadException {

		try {
			initializePLUProductDatabase();
			description = PLUPRODUCTS.get(plu).getDescription();
			// The weight on the scale is in grams so I converted to kg because the price of the products in PLUCodedProduct is per kg 
			price = (scale.getCurrentWeight() / 1000) * PLUPRODUCTS.get(plu).getPrice().doubleValue();
		}
		catch(Exception e) {
			throw new SimulationException(new NullPointerException("Something went wrong. Try again. "));
		}
	}
	
	/**
	 * Get double price 
	 * @return double price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Get BigDecimal price 
	 * @return BigDecimal price
	 */
	public BigDecimal getBigDecimalPrice() {
		return new BigDecimal(price);
	}
	
	/**
	 * Get PLUCodedProduct description 
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}
}