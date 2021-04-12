import java.util.ArrayList;
import java.util.Map;

import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.*;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.*;

public class ScanItem {
	
	private double totPrice = 0;
	private double totWeight = 0;
	private ArrayList<String> totList;
	public BarcodeScanner main;
	public BarcodeScanner handheld;
	private boolean isEnabled = false;
	public static Map<Barcode, BarcodedProduct> database;
	private static DeclineBagPrompt bagPrompt;
	
	private double curWeight; //only used locally

	
	/**
	 * scans items
	 * @param BarcodedItem item
	 * @param decline bag prompt
	 * @throws SimulationException if SelfCheckoutStation is null
	 */
	public ScanItem(SelfCheckoutStation station, Map<Barcode, BarcodedProduct> database, DeclineBagPrompt bagPrompt) {
		if(station == null) throw new SimulationException(new NullPointerException("station is null"));
		
		main = station.mainScanner;
		main.enable();
		handheld = station.handheldScanner;
		handheld.enable();
		totList = new ArrayList<String>();
		ScanItem.database = database;
		ScanItem.bagPrompt = bagPrompt;
		
		scannerListener();
	}
	
	/**
	 * Scans a barcoded item from the main scanner
	 * @param a Barcoded item
	 * @throws SimulationException if barcodedItem is null
	 */
	public void scanFromMain(BarcodedItem item, boolean declineBagPrompt) {
		
		if(item == null) throw new SimulationException(new NullPointerException("item is null"));
		curWeight = item.getWeight();
		main.scan(item);
		
		bagPrompt.showPrompt();
		if (declineBagPrompt) {
			bagPrompt.attendentClosePrompt();
		}
		
		
	}
	
	/**
	 * Scans a barcoded item from the handheld scanner
	 * @param a Barcoded item
	 * @throws SimulationException if barcodedItem is null
	 */
	public void scanFromHandheld(BarcodedItem item, boolean declineBagPrompt) {
		if(item == null) throw new SimulationException(new NullPointerException("item is null"));
		curWeight = item.getWeight();
		handheld.scan(item);
		
		bagPrompt.showPrompt();
		if (declineBagPrompt) {
			bagPrompt.attendentClosePrompt();
		}
		
		
	}
	
	/**
	 * adds the last scanned item to the total weight 
	 * 
	 * this is not an ideal way to update weight, it should be updated through the listener but the listener does not handle 
	 * barcodedItem only barcode 
	 * 
	 * @param double
	 */
	private void updateWeight(double d) {
		totWeight += d;
	}	
	
	/**
	 * returns the total weight of scanned items
	 * @return total weight
	 */
	public double getTotalWeight() {
		return totWeight;
	}
	
	/**
	 * returns the list of names of scanned items
	 * @return total list
	 */
	public ArrayList<String> getTotalList() {
		return totList;
	}
	
	/**
	 * returns the total price of scanned items
	 * @return total price
	 */
	 public double getTotalPrice(){
	  	return totPrice;
	 }
	 
	/**
	 * returns that value that the listener is enabled
	 * @return boolean is enabled
	 */
	public boolean getIsEnabled() {
		return isEnabled;
	}
	
	/**
	 * looks up the product from the database and updates price
	 * @param barcode
	 */
	private void lookupProduct(Barcode barcode) {
		totPrice += database.get(barcode).getPrice().doubleValue();
	}
	
	/**
	 * create an instance of the listener 
	 */
	private void scannerListener() {
		main.register(new BarcodeScannerListener() {

			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				isEnabled = true;
			}

			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				isEnabled = false;
			}

			@Override
			public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
				totList.add(barcode.toString());
				updateWeight(curWeight);
				lookupProduct(barcode);
			}
			
		});
		handheld.register(new BarcodeScannerListener() {

			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				isEnabled = true;
			}

			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				isEnabled = false;
			}

			@Override
			public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
				totList.add(barcode.toString());
				updateWeight(curWeight);
				lookupProduct(barcode);
			}
			
		});
	}
}
