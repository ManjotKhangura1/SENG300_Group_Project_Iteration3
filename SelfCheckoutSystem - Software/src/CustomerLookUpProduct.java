import java.math.BigDecimal;
import java.util.Map;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.PLUCodedProduct;


public class CustomerLookUpProduct {
	
	
	public static Map<PriceLookupCode, PLUCodedProduct> database;

	
	/*
	 * Constructor 
	 */
	public CustomerLookUpProduct( Map<PriceLookupCode, PLUCodedProduct> database) {
		CustomerLookUpProduct.database = database; 
	}
	
	
	/**
	 * returns the product with the matched PLU code
	 */
	public PLUCodedProduct searchProductInPLUDatabase(PriceLookupCode pluCode) {
			
			if(CustomerLookUpProduct.database.containsKey(pluCode)) {
				return CustomerLookUpProduct.database.get(pluCode);
			
			} else {
				throw new SimulationException("There is no product with this PLU code");
			}
			
	}
	
	
	/**
	 * returns the searched product price
	 */
	public BigDecimal getPriceOfProduct(PriceLookupCode pluCode) {
		
		if(CustomerLookUpProduct.database.containsKey(pluCode)) {
			return CustomerLookUpProduct.database.get(pluCode).getPrice();	
		} else {
			throw new SimulationException("There is no price of product with this PLU code");

		}
	}
	
	
	/**
	 * returns the searched product description
	 */
	public String getDescriptionOfProduct(PriceLookupCode pluCode) {
		
		if(CustomerLookUpProduct.database.containsKey(pluCode)) {
			return CustomerLookUpProduct.database.get(pluCode).getDescription();
		} else {
			throw new SimulationException("There is no description of product with this PLU code");

		}
	}
	

}
