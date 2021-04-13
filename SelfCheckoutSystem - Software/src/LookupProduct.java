import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class LookupProduct {
	
	//Attendant looks up product using the PLU code to give a price check for the customer when there is no barcode
	public static Map<PriceLookupCode, PLUCodedProduct> database;

	
	/**
	 * Attendant LookUpProduct constructor
	 * @param Map<PriceLookupCode, PLUCodedProduct> database
	 */
	public LookupProduct(Map<PriceLookupCode, PLUCodedProduct> database) {
		LookupProduct.database = database; 
	}
	
	
	/**
	 * returns the product with the matched PLU code
	 * @param PriceLookupCode pluCode
	 */
	public PLUCodedProduct searchProductInPLUDatabase(PriceLookupCode pluCode) {
			
			if(LookupProduct.database.containsKey(pluCode)) {
				return LookupProduct.database.get(pluCode);
			
			} else {
				throw new SimulationException("There is no product with this PLU code");
			}
			
	}
	
	
	/**
	 * returns the searched product price
	 * @param PriceLookupCode pluCode
	 */
	public BigDecimal getPriceOfProduct(PriceLookupCode pluCode) {
		
		if(LookupProduct.database.containsKey(pluCode)) {
			return LookupProduct.database.get(pluCode).getPrice();	
		} else {
			throw new SimulationException("There is no price of product with this PLU code");

		}
	}
	
	
	/**
	 * returns the searched product description
	 * @param PriceLookupCode pluCode
	 */
	public String getDescriptionOfProduct(PriceLookupCode pluCode) {
		
		if(LookupProduct.database.containsKey(pluCode)) {
			return LookupProduct.database.get(pluCode).getDescription();
		} else {
			throw new SimulationException("There is no description of product with this PLU code");

		}
	}
	

}