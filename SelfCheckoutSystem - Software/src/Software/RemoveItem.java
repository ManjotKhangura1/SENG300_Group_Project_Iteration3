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
		initializeDatabase();
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
	
	/**
	 * Initialize product database
	 */
	private void initializeDatabase() {
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(new Barcode("01"), new BarcodedProduct(new Barcode("01"), "One", new BigDecimal("10.99")));
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(new Barcode("02"), new BarcodedProduct(new Barcode("02"), "Two", new BigDecimal("2.49")));
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(new Barcode("03"), new BarcodedProduct(new Barcode("03"), "Three", new BigDecimal("20.45")));
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(new Barcode("04"), new BarcodedProduct(new Barcode("04"), "Four", new BigDecimal("6.94")));
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(new Barcode("05"), new BarcodedProduct(new Barcode("05"), "Five", new BigDecimal("9.29")));
		
		ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode("0001"), new PLUCodedProduct(new PriceLookupCode("0001"), "A", new BigDecimal("7.49")));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode("0002"), new PLUCodedProduct(new PriceLookupCode("0002"), "B", new BigDecimal("5.99")));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode("0003"), new PLUCodedProduct(new PriceLookupCode("0003"), "C", new BigDecimal("10.59")));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode("0004"), new PLUCodedProduct(new PriceLookupCode("0004"), "D", new BigDecimal("9.77")));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(new PriceLookupCode("0005"), new PLUCodedProduct(new PriceLookupCode("0005"), "E", new BigDecimal("6.49")));
	}
}