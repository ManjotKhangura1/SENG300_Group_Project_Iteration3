package Software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.*;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.*;

public class ScanItem {
	
	public BarcodeScanner main;
	public BarcodeScanner handheld;
	private boolean isEnabled = false;
	public static Map<Barcode, BarcodedProduct> database;
	public FinishesAddingItems done;
	public BaggingArea baged;
	
	private BigDecimal curWeight; //only used locally

	
	/**
	 * scans items
	 * @param BarcodedItem item
	 * @param decline bag prompt
	 * @throws SimulationException if SelfCheckoutStation is null
	 */
	public ScanItem(SelfCheckoutStation station, Map<Barcode, BarcodedProduct> database, FinishesAddingItems d, BaggingArea b) {
		if(station == null) throw new SimulationException(new NullPointerException("station is null"));
		
		main = station.mainScanner;
		main.enable();
		handheld = station.handheldScanner;
		handheld.enable();
		ScanItem.database = database;
		done = d;
		baged = b;
		scannerListener();
	}
	
	/**
	 * Scans a barcoded item from the main scanner
	 * @param a Barcoded item
	 * @param a boolean, true is declining bag prompt
	 * @throws SimulationException if barcodedItem is null
	 */
	public void scanFromMain(BarcodedItem item) {
		
		if(item == null) throw new SimulationException(new NullPointerException("item is null"));
		curWeight = new BigDecimal(item.getWeight());
		main.scan(item);	
	}
	
	/**
	 * Scans a barcoded item from the handheld scanner
	 * @param a Barcoded item
	 * @param a boolean, true if declining a bag prompt
	 * @throws SimulationException if barcodedItem is null
	 */
	public void scanFromHandheld(BarcodedItem item) {
		if(item == null) throw new SimulationException(new NullPointerException("item is null"));
		curWeight = new BigDecimal(item.getWeight());
		handheld.scan(item);		
	}
	 
	/**
	 * returns that value that the listener is enabled
	 * @return boolean is enabled
	 */
	public boolean getIsEnabled() {
		return isEnabled;
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
				done.updateTotals(database.get(barcode).getDescription(), database.get(barcode).getPrice(), curWeight);
				baged.setWeightScanned(curWeight);
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
				done.updateTotals(database.get(barcode).getDescription(), database.get(barcode).getPrice(), curWeight);
				baged.setWeightScanned(curWeight);
			}
			
		});
	}
}
