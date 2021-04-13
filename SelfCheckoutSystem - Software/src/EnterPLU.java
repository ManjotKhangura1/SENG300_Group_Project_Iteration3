import java.math.BigDecimal;
import java.util.Map;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class EnterPLU {
	//local variables
	private BigDecimal price;
	public BigDecimal weight;
	private String description;
	private PriceLookupCode plu;
	//local objects
	public static Map<PriceLookupCode, PLUCodedProduct> database;
	public final ElectronicScale scale;
	public FinishesAddingItems done;


	/**
	 * PLULookup constructor
	 * @param station
	 * @param database
	 */
	public EnterPLU(SelfCheckoutStation station, Map<PriceLookupCode, PLUCodedProduct> database, FinishesAddingItems d) {
		EnterPLU.database = database;
		scale = station.scale;
		done = d;
	}

	/**
	 * gets the user input of a 4-5 length numerical code and checks it against the PLUdatabase
	 * multiples the price of the item by the weight of the item in kilograms
	 * @param input
	 * @throws OverloadException 
	 */
	public void itemLookup(String input) throws OverloadException {
		if(input == null)
			throw new SimulationException(new NullPointerException("station is null"));
		
		plu = new PriceLookupCode(input);
		try {
			if(plu.equals(database.get(plu).getPLUCode())) {
				description = database.get(plu).getDescription(); //update the item description
				price = database.get(plu).getPrice().multiply(BigDecimal.valueOf(scale.getCurrentWeight())); //multiply price by weight
			}
		}catch(OverloadException e) {
			throw new OverloadException("Item not on scale"); //from scale.getCurrentWeight
		}catch(Exception e) {
			throw new SimulationException(new NullPointerException("code not recognised")); //from database.get(plu)
		}
		
		//update totals in finishesAddingItems
		done.setList(description);
		done.setPrice(price);
		done.setWeight(weight);
	}
	
}
