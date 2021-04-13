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
	private SelfCheckoutStation station;
	private ArrayList<Barcode> barcodedItemList;
	private Map<PriceLookupCode, Double> pluCodedItemList;
	private BigDecimal total;
	private BigDecimal currentPrice;
	private double currentWeight;
	
	/**
	 * Create a BarcodeScannerListener
	 * If barcodedItemList has the targeted barcode, 
	 * then remove it from barcodedItemList and subtract price from total
	 */
	private BarcodeScannerListener listener = new BarcodeScannerListener() {
		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			// TODO Auto-generated method stub
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {	
			// TODO Auto-generated method stub
		}

		@Override
		public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
			if(barcodedItemList.contains(barcode)) {
				barcodedItemList.remove(barcode);
				currentPrice = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode).getPrice();
				total = total.subtract(currentPrice);
			}
		}
	};
	
	/**
	 * Constructor
	 * @param station
	 * @param barcodedItemList
	 * @param pluCodedItemList
	 * @param total
	 */
	public RemoveItem(SelfCheckoutStation station, ArrayList<Barcode> barcodedItemList, Map<PriceLookupCode, Double> pluCodedItemList, BigDecimal total) {
		this.station = station;
		this.barcodedItemList = barcodedItemList;
		this.pluCodedItemList = pluCodedItemList;
		this.total = total;
		this.station.mainScanner.register(listener);
		this.station.handheldScanner.register(listener);
	}
	
	/**
	 * Scan by the main BarcodeScanner
	 * @param item
	 */
	public void scanFromMain(BarcodedItem item) {
		station.mainScanner.scan(item);
	}
	
	/**
	 * Scan by the hand-held BarcodeScanner
	 * @param item
	 */
	public void scanFromHandheld(BarcodedItem item) {
		station.handheldScanner.scan(item);
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
	 * Get barcodedItemList
	 * @return ArrayList<Barcode> barcodedItemList
	 */
	public ArrayList<Barcode> getBarcodedItemList() {
		return barcodedItemList;
	}
	
	/**
	 * Get pluCodedItemList
	 * @return Map<PriceLookupCode, Double> pluCodedItemList
	 */
	public Map<PriceLookupCode, Double> getPLUCodedItemList() {
		return pluCodedItemList;
	}
	
	/**
	 * Get total
	 * @return BigDecimal total
	 */
	public BigDecimal getTotal() {
		return total;
	}
	
	/**
	 * Get currentPrice
	 * @return BigDecimal currentPrice
	 */
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	
	/**
	 * Get currentWeight
	 * @return double currentWeight
	 */
	public double getCurrentWeight() {
		return currentWeight;
	}
}