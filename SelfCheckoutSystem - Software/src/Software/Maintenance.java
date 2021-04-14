package Software;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.ReceiptPrinterListener;

public class Maintenance {
	public SelfCheckoutStation station;
	public Currency currency;

	// VARIABLES FOR PRINTER MAINTENANCE
	// global flags to keep track of maintenance functions state
	private boolean paperChangeSuccessful;
	private boolean inkChangeSuccessful;
	private boolean outOfPaper;
	private boolean outOfInk;
	private boolean isEnabled;
	private boolean isDisabled;
	private boolean refillSuccess;
	private int actualRefillAmount;
	private boolean emptied;
	private boolean stationOn;

	// overridden listener for printer, updating flags when necessary
	private ReceiptPrinterListener printerListener = new ReceiptPrinterListener() {

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			isEnabled = true;

		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			isDisabled = true;

		}

		@Override
		public void paperAdded(ReceiptPrinter printer) {
			paperChangeSuccessful = true;

		}

		@Override
		public void outOfPaper(ReceiptPrinter printer) {
			System.out.println("WARNING! The Receipt Printer is out of paper!");
			outOfPaper = true;

		}

		@Override
		public void outOfInk(ReceiptPrinter printer) {
			System.out.println("WARNING! The Receipt Printer is out of ink!");
			outOfInk = true;

		}

		@Override
		public void inkAdded(ReceiptPrinter printer) {
			inkChangeSuccessful = true;

		}
	};

	/**
	 * Maintenance constructor
	 * 
	 * @param station  The SelfCheckoutStation that is currently being used
	 */
	public Maintenance(SelfCheckoutStation station) {
		// throws and exception if station given is null
		if (station == null) {
			throw new SimulationException(new NullPointerException("station is null"));
		}
		this.station = station;
		this.currency = station.coinValidator.currency;

		station.printer.register(printerListener);
		paperChangeSuccessful = false;
		inkChangeSuccessful = false;
		outOfPaper = false;
		outOfInk = false;
		isEnabled = false;
		isDisabled = false;
		refillSuccess = true;
		actualRefillAmount = 0;
		emptied = false;
		stationOn = true;
	}

	/**
	 * Reloads a specified coin dispenser
	 * 
	 * @param coinDenomination the denomination of the dispenser to be reloaded
	 * @param refillAmount the amount of coins the attendant wants to reload with
	 */
	public void refillCoin(BigDecimal coinDenomination, int refillAmount) {
		int maxAmount;
		CoinDispenser coinDispenser;

		// finds the coin dispenser correlated to the given coin denomination
		coinDispenser = station.coinDispensers.get(coinDenomination);

		if(refillAmount > 0 && coinDispenser != null) {
	
			try {
				// finds how many more coins are needed before the coin dispenser reaches
				// capacity
				maxAmount = coinDispenser.getCapacity() - coinDispenser.size();
				
				//checking if the amount the attendant wants to refill with will overload the dispenser
				//if it is, only refill up to the max amount the dispenser can handle
				if(refillAmount > maxAmount) {
					actualRefillAmount = maxAmount;
					System.out.println("You've attempted to load more coins than the system can hold. The max amount was before overloading will loaded");
				}else {
					actualRefillAmount = refillAmount;
				}
	
				// refills the dispenser until it reaches capacity
				for (int i = 0; i < actualRefillAmount; i++) {
					Coin coinToLoad = new Coin(coinDenomination, currency);
					coinDispenser.load(coinToLoad);
				}
				
				refillSuccess = true;
	
			//overload exception should never be thrown because we make sure to not overfill
			} catch (OverloadException e) {
				throw new SimulationException(e);
			}
		} else if (refillAmount <= 0){
			refillSuccess = false;
		}else {
			refillSuccess = false;
		}
	}

	/**
	 * Reloads a specified banknote dispenser
	 * 
	 * @param banknoteDenomination the denomination of the dispenser to be reloaded
	 * @param refillAmount the amount of banknotes the attendant wants to reload with
	 */
	public void refillBanknote(int banknoteDenomination, int refillAmount) {
		int maxAmount;
		BanknoteDispenser banknoteDispenser;

		// finds the banknote dispenser correlated to the given banknote denomination
		banknoteDispenser = station.banknoteDispensers.get(banknoteDenomination);

		if(refillAmount > 0 && banknoteDispenser != null) {
			try {
				// finds how many more banknotes are needed before the banknote dispenser
				// reaches capacity
				maxAmount = banknoteDispenser.getCapacity() - banknoteDispenser.size();
				
				//checking if the amount the attendant wants to refill with will overload the dispenser
				//if it is, only refill up to the max amount the dispenser can handle
				if(refillAmount > maxAmount) {
					actualRefillAmount = maxAmount;
					System.out.println("You've attempted to load more banknote than the system can hold. The max amount was before overloading will loaded");
				}else {
					actualRefillAmount = refillAmount;
				}
	
				// refills the dispenser until it reaches capacity
				for (int i = 0; i < actualRefillAmount; i++) {
					Banknote banknoteToLoad = new Banknote(banknoteDenomination, currency);
					banknoteDispenser.load(banknoteToLoad);
				}
				refillSuccess = true;
			//overload exception should never be thrown because we make sure to not overfill
			} catch (OverloadException e) {
				throw new SimulationException(e);
			}
		}else if (refillAmount <= 0){
			System.out.println("The refill amount chosen is invalid (either negative or 0");
			refillSuccess = false;
		}else {
			System.out.println("Invalid banknote denomination provided");
			refillSuccess = false;
		}
	}

	/**
	 * Empties the coin storage unit
	 * @param csu	the coin storage unit to be emptied
	 */
	public void emptyCoinStorageUnit(CoinStorageUnit csu) {
		if(csu != null) {
			CoinStorageUnit CSU = csu;
			if (CSU.getCoinCount() <= 0) {
				System.out.println("No coins in Storage Unit. None unloaded");
				emptied = false;
			}
			else {
				List<Coin> coins = CSU.unload();

				System.out.println("Removed" + coins.size() + " many coins");
				emptied = true;
			}
		} else {
			emptied = false;
		}
	}

	/**
	 * Empties the banknote storage unit
	 * @param bsu	the banknote storage unit to be emptied
	 */
	public void emptyBanknoteStorageUnit(BanknoteStorageUnit bsu) {
		if(bsu != null) {			
			BanknoteStorageUnit BSU = bsu;
			
			if (BSU.getBanknoteCount() <= 0) {
				System.out.println("No banknote in Storage Unit. None unloaded");
				emptied = false;
			}
			else {
				List<Banknote> notes = BSU.unload();
				
				System.out.println("Removed" + notes.size() + " many notes");
				emptied = true;
			}
		} else {
			emptied = false;
		}

	}
	
	/**
	 * Start up a self checkout station
	 */
	public void startUp() {
		// print out start up statement
		System.out.print("Starting up selfcheckout station...");
		// enable all abstract devices in station
		enableAll();
		
		stationOn = true;
	}
	
	/**
	 * shut down a self checkout station
	 */
	public void shutDown() {
		// print out shut down statement
		System.out.print("Shutting down selfcheckout station...");
		// disable all abstract devices in station
		disableAll();
		
		stationOn = false;
	}
	
	/**
	 * enable all abstract devices in self checkout station
	 */
	private void enableAll() {
		this.station.scale.enable();
		this.station.baggingArea.enable();
		this.station.screen.enable();
		this.station.printer.enable();
		this.station.cardReader.enable();
		this.station.mainScanner.enable();
		this.station.handheldScanner.enable();
		this.station.banknoteInput.enable();
		this.station.banknoteOutput.enable();
		this.station.banknoteValidator.enable();
		this.station.banknoteStorage.enable();
		this.station.coinSlot.enable();
		this.station.coinValidator.enable();
		this.station.coinStorage.enable();
		this.station.coinTray.enable();
	}
	
	/**
	 * disable all abstract devices in self checkout station
	 */
	private void disableAll() {
		this.station.scale.disable();
		this.station.baggingArea.disable();
		this.station.screen.disable();
		this.station.printer.disable();
		this.station.cardReader.disable();
		this.station.mainScanner.disable();
		this.station.handheldScanner.disable();
		this.station.banknoteInput.disable();
		this.station.banknoteOutput.disable();
		this.station.banknoteValidator.disable();
		this.station.banknoteStorage.disable();
		this.station.coinSlot.disable();
		this.station.coinValidator.disable();
		this.station.coinStorage.disable();
		this.station.coinTray.disable();
	}

	/**
	 * Adds paper to printer. When used in the GUI it will always max out the paper in the printer
	 * 
	 * @param units - the units of paper to be added
	 * @return - null if SimulationException detected
	 */
	public ReceiptPrinter changePaper(int units) {
		try {
			station.printer.addPaper(units);
			if (paperChangeSuccessful) {
				paperChangeSuccessful = false;
				outOfPaper = false;
				return station.printer;
			} else {
				throw new SimulationException("Unknown Error While Adding Paper");
			}

		} catch (SimulationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds ink to printer. When used in the GUI it will always max out the ink in the printer
	 * 
	 * @param units - the units of ink to be added
	 * @return - null if SimulationException detected
	 */
	public ReceiptPrinter changeInk(int quantity) {
		try {
			station.printer.addInk(quantity);
			if (inkChangeSuccessful) {
				inkChangeSuccessful = false;
				outOfInk = false;
				return station.printer;
			} else {
				throw new SimulationException("Unknown Error While Adding Ink");
			}

		} catch (SimulationException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the refillSuccess boolean
	 * @return refillSuccess The refillSuccess boolean which represents the success of the last attemped refill
	 */
	public boolean isRefillSuccess() {
		return refillSuccess;
	}
	
	/**
	 * Gets the actualRefillAmount for the last attempted refill
	 * @return actualRefillAmount The actual refill amount the last attempted refill
	 */
	public int getActualRefillAmount() {
		return actualRefillAmount;
	}

	/**
	 * Gets the emptied boolean
	 * @return emptied The emptied boolean which represents the success of the last attemped emptying of a storage unit
	 */
	public boolean isEmptied() {
		return emptied;
	}
	
	/**
	 * Gets the stationOn boolean
	 * @return stationOn The boolean which represents the station status
	 */
	public boolean isStationOn() {
		return stationOn;
	}
	
	

}
