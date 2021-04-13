package Software;
import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.*;
import java.util.ArrayList;
import org.lsmr.selfcheckout.*;

public class ReturnsToAddingItems {
	
	/*
	 * variables for the class
	 */
	public SelfCheckoutStation aSelfCheckoutStation;
	
	/*
	 * initially false
	 */
	public boolean isScanned=false;
	public boolean isEnabled=false;

	/*
	 * Creating instance of BarcodeScannerListener as it is required if customer wants to 
	 * continue adding items
	 */
	
	private BarcodeScannerListener bsl = new BarcodeScannerListener() {
		 
	
        @Override
        public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        	isEnabled=true;
        }

        @Override
        public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
        	isEnabled=false;
        }
 
        @Override
        public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
            // TODO Auto-generated method stub
        	//this will make sure (once they've returned to adding items), if items
        	//successfully get scanned or not
        	isScanned = true;  	    
        }
    };
   
	/*
	 * Constructor for the class
	 * Must pass a SelfCheckoutStation as the program makes use of methods and variables
	 * in SelfCheckoutStation
	 */
    
    public ReturnsToAddingItems(SelfCheckoutStation aStation) {
    	
    	//Station passed cannot be null
    	if(aStation==null) {
    		throw new SimulationException(new NullPointerException("ERROR! No station to work with"));
    	}
    	
    	//If parameter station is not null, we get to this point
    	aSelfCheckoutStation=aStation;
    	
    	//Registering the listener which is required for customers to continue adding items
    	aSelfCheckoutStation.mainScanner.register(bsl);
    	aSelfCheckoutStation.handheldScanner.register(bsl);
    	
    }
	    
    /*
     * Enabling every aspect in the self checkout system that is required for the
     * process of customer adding items to be successful
     * Enabling these so the customer can make use of them to successfully add an item
     */
	
    public void enableRequiredHardware() {
    	
    	aSelfCheckoutStation.mainScanner.enable();
   
    	aSelfCheckoutStation.handheldScanner.enable();
    	aSelfCheckoutStation.baggingArea.enable();
    	aSelfCheckoutStation.scale.enable();
    	
    }
	    
	
}
