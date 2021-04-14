package Software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.BarcodeScannerListener;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class RemoveItem {
	//local objects
	private SelfCheckoutStation station;
	private Map<Barcode, BarcodedProduct> barcodedProduct;
	private Map<PriceLookupCode, PLUCodedProduct> pluCodedItemList;
	private Map<String, PLUCodedProduct> PLULookup;
	Map<Barcode, BarcodedItem> barcodedItem;
	private FinishesAddingItems done;
	
	/**
	 * Constructor for Remove item
	 * @param SelfCheckoutStation station
	 * @param ArrayList<Barcode> barcodedItemList
	 * @param Map<PriceLookupCode, Double> pluCodedItemList
	 * @param BigDecimal total
	 */
	public RemoveItem(SelfCheckoutStation station, Map<Barcode, BarcodedProduct> barcodedProduct, Map<PriceLookupCode, PLUCodedProduct> pluCodedItemList, 
			Map<String, PLUCodedProduct> PLULookup, Map<Barcode, BarcodedItem> barcodedItem, FinishesAddingItems done) {
		this.station = station;
		this.barcodedProduct = barcodedProduct;
		this.pluCodedItemList = pluCodedItemList;
		this.PLULookup = PLULookup;
		this.barcodedItem = barcodedItem;
		this.done = done;

	}
	
	/**
	 * Remove PLUCodedItem
	 * @param pluCode
	 * @param weight
	 * If removed weight is greater than 95% of purchased weight, treat as the customer quit all of the product in case of weight discrepancy
	 * Otherwise update the purchased weight because the customer quit partially
	 */
	public void removePLUCodedItem(PriceLookupCode pluCode, double weight) {
		if(pluCodedItemList.get(pluCode) != null) {
			if(weight >= pluCodedItemList.get(pluCode) * 0.95) {
				currentWeight = pluCodedItemList.get(pluCode);
				pluCodedItemList.remove(pluCode);
			}
			else {
				currentWeight = weight;
				pluCodedItemList.put(pluCode, pluCodedItemList.get(pluCode) - currentWeight);
			}
			currentPrice = ProductDatabases.PLU_PRODUCT_DATABASE.get(pluCode).getPrice().multiply(new BigDecimal(Double.toString(currentWeight)));
			total = total.subtract(currentPrice);
		}
	}
	
	/**
	 * Removes the given item from the total by the given parameter
	 * @param Barcode barcode
	 */
	public void removeBarcodedItem(Barcode barcode) {
		done.removeItem(bar);
	}
	
	/**
	 * Removes the given item from the total by the given parameter
	 * @param String plu name
	 */
	public void removeItem(String name) {
		done.removeList(PLULookup.get(name).getDescription());
		done.removePrice(PLULookup.get(name).getPrice());
		//done.removeWeight(PLULookup.get(name).get);
	}
}