import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.ReceiptPrinterListener;

public class PrinterMaintenance {
	
	//global variables for overall machine state
	private boolean enabled;
	private SelfCheckoutStation checkoutStation;
	
	//global flags to keep track of maintenance functions state
	private boolean paperChangeSuccessful;
	private boolean inkChangeSuccessful;
	
	//overridden listener for printer, updating flags when necessary
	private ReceiptPrinterListener printerListener = new ReceiptPrinterListener(){
		@Override
		public void outOfPaper(ReceiptPrinter printer){
			// TODO Auto-generated method stub
			
		}

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void outOfInk(ReceiptPrinter printer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void paperAdded(ReceiptPrinter printer) {
			paperChangeSuccessful=true;
			
		}

		@Override
		public void inkAdded(ReceiptPrinter printer) {
			inkChangeSuccessful=true;
			
		}
	
	};
	
	//constructor for use case
	public PrinterMaintenance(SelfCheckoutStation station) {
		this.checkoutStation = station;
		checkoutStation.printer.register(printerListener);
		this.enabled = true;
		this.paperChangeSuccessful = false;
		this.inkChangeSuccessful = false;
	}
	
	
	//adds the amount of paper to the printer
	//prints error message for any encountered exceptions
	public ReceiptPrinter changePaper(int units) {
		try {
			checkoutStation.printer.addPaper(units);
			if(paperChangeSuccessful) {
				paperChangeSuccessful = false;
				return checkoutStation.printer;
			}else {
				throw new SimulationException("Unknown Error While Adding Paper");
			}
			
		}catch(SimulationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//adds the amount of ink to the printer
	//prints error message for any encountered exceptions
	public ReceiptPrinter changeInk(int quantity) {
		try {
			checkoutStation.printer.addInk(quantity);
			if(inkChangeSuccessful) {
				inkChangeSuccessful = false;
				return checkoutStation.printer;
			}else {
				throw new SimulationException("Unknown Error While Adding Ink");
			}
			
		}catch(SimulationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
