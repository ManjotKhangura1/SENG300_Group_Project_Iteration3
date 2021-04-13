
import java.util.ArrayList;
import java.util.Map;

import org.lsmr.selfcheckout.PLUCodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class AddPLUItem {
	public static Map<PriceLookupCode, PLUCodedProduct> database;
	public final ElectronicScale scale;
	
	//list that keeps all the PLU-scanned items
	private ArrayList<String> totList;
	//total price of all the PLU-scanned items
	private double totPrice;


	public AddPLUItem(SelfCheckoutStation station, Map<PriceLookupCode, PLUCodedProduct> database) {
		if(station == null && database == null) {
			throw new SimulationException(new NullPointerException("station or database is null."));
		}
		this.database = database;
		scale = station.scale;
		totList = new ArrayList<String>();
	}
	//adding a PLUcodeditem onto the electronic scale (not the bagging area scale)
	public void addItem(PLUCodedItem item) {
		if(item == null) {
			throw new SimulationException(new NullPointerException("Null bag being added."));
		}
		scale.add(item);
		double price = database.get(item.getPLUCode()).getPrice().doubleValue() * item.getWeight();
		totPrice += price; //increment the price of this item to the total
		totList.add(item.getPLUCode().toString()); //add this item to the array
	}
	
	//getter for totalPrice
	public double getTotalPrice() {
		return totPrice;
	}
	//getter for totalList
	public ArrayList<String> getTotalList(){
		return totList;
	}


}
