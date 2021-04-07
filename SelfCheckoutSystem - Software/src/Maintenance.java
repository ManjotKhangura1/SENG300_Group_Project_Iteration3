import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class Maintenance {
	
	
	private BigDecimal nickel = new BigDecimal(0.05);
    private BigDecimal dime = new BigDecimal(0.1);
    private BigDecimal quarter = new BigDecimal(0.25);
    private BigDecimal loonie = new BigDecimal(1.00);
    private BigDecimal toonie = new BigDecimal(2.00);
    private BigDecimal[] coinDenominations = {nickel, dime, quarter, loonie, toonie};
	private int[] banknoteDenominations = {5, 10 , 20 , 50, 100};
    private int scaleMaximumWeight = 50000;
    private int scaleSensitivity = 1;
    private SelfCheckoutStation station;
    private Boolean stationOn = true;

    
    public Maintenance(String curr) {
		Currency currency = Currency.getInstance(curr);
		this.station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
    }
    
	public SelfCheckoutStation startUp() {
		System.out.print("Strting up selfcheckout station...");
		enableAll();
		return this.station;
	}
	
	public SelfCheckoutStation shutDown(SelfCheckoutStation scs) {
		System.out.print("Shutting down selfcheckout station...");
		disableAll();
		return this.station;
		// System.exit(1);
	}
	
	public void enableAll() {
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
		this.stationOn = true;
	}
	
	public void disableAll() {
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
		this.stationOn = false;
	}
	
	public Boolean stationState() {
		return this.stationOn;
	}
}
