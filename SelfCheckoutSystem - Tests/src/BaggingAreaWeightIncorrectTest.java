
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class BaggingAreaWeightIncorrectTest {
	private SelfCheckoutStation scs;
	private BaggingArea baggingArea;
	private ScanItem scanItem;
	private BaggingAreaWeightIncorrect bawi;
	private BarcodedItem barcodedItem;
	Map<Barcode, BarcodedProduct> database;
	
	private int scaleSensitivity;
	//setting up the constructor for the SelfCheckoutStation
		@Before
			public void setup() throws OverloadException {
			Currency currency = Currency.getInstance("USD");
			int[] banknoteDenominations = {5,10,20,50,100};
			BigDecimal[] coinDenominations = {new BigDecimal(0.01), new BigDecimal(0.05), new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
			int scaleMaximumWeight = 1000;
			this.scaleSensitivity = 1;
			
			//Creates a barcoded item
			Barcode barcode = new Barcode("1");
			barcodedItem = new BarcodedItem(barcode, 50);
			BarcodedProduct product = new BarcodedProduct(barcode, "the only item we sell", BigDecimal.valueOf(10.50));
			
			database = new HashMap<>();
			database.put(barcode, product);
			
			
			scs = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
			baggingArea = new BaggingArea (scs);
			scanItem = new ScanItem(scs, database);
			bawi = new BaggingAreaWeightIncorrect(scs, scanItem);
		}
		
		@Test
		public void testing() throws DisabledException, OverloadException {
			scanItem.scanFromHandheld(barcodedItem);
			baggingArea.setWeightScanned(barcodedItem.getWeight());
			baggingArea.addItem(barcodedItem);
			bawi.calculate();
			boolean test = bawi.getWeightCheck();
			assertTrue(test); //ScanItem is wrong, current weight is calculated a line too late
			
			
		}
}
