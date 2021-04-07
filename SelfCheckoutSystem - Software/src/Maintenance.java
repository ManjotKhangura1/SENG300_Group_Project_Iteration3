import java.math.BigDecimal;
import java.util.Currency;

import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class Maintenance {
	
	private SelfCheckoutStation station;
	private BigDecimal nickel = new BigDecimal(0.05);
    private BigDecimal dime = new BigDecimal(0.1);
    private BigDecimal quarter = new BigDecimal(0.25);
    private BigDecimal loonie = new BigDecimal(1.00);
    private BigDecimal toonie = new BigDecimal(2.00);
    private BigDecimal[] coinDenominations = {nickel, dime, quarter, loonie, toonie};
	private int[] banknoteDenominations = {5, 10 , 20 , 50, 100};
    private int scaleMaximumWeight = 50000;
    private int scaleSensitivity = 1;
	
	
	public SelfCheckoutStation startUp(String curr) {
		Currency currency = Currency.getInstance(curr);
		this.station = new SelfCheckoutStation(currency, banknoteDenominations, coinDenominations, scaleMaximumWeight, scaleSensitivity);
		return this.station;
	}
	
	public void shutDown() {
		
	}
}
