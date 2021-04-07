import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
public class Maintenance {
	public SelfCheckoutStation station;
	public Currency currency;

	/**
	 * Refill Dispensers constructor
	 * 
	 * @param station  The SelfCheckoutStation that is currently being used
	 * @param currency The kind of currency permitted
	 */
	public Maintenance(SelfCheckoutStation station, Currency currency) {
		// throws and exception if station given is null
		if (station == null) {
			throw new SimulationException(new NullPointerException("station is null"));
		}
		this.station = station;

		// throws and exception if station given is null
		if (currency == null) {
			throw new SimulationException(new NullPointerException("currency is null"));
		}
		this.currency = currency;
	}

	/**
	 * Reloads a specified coin dispenser
	 * @param coinDenomination the denomination of the dispenser to be reloaded
	 */
	public void refillCoin(BigDecimal coinDenomination) {
		int amountToRefill;
		CoinDispenser coinDispenser;

		//finds the coin dispenser correlated to the given coin denomination
		coinDispenser = station.coinDispensers.get(coinDenomination);

		try {
			//finds how many more coins are needed before the coin dispenser reaches capacity
			amountToRefill = coinDispenser.getCapacity() - coinDispenser.size();

			//refills the dispenser until it reaches capacity
			for (int i = 0; i < amountToRefill; i++) {
				Coin coinToLoad = new Coin(coinDenomination, currency);
				coinDispenser.load(coinToLoad);
			}

		} catch (SimulationException | OverloadException e) {
			throw new SimulationException(e);
		}
	}
	
	/**
	 * Reloads a specified banknote dispenser
	 * @param banknoteDenomination the denomination of the dispenser to be reloaded
	 */
	public void refillBanknote(int banknoteDenomination) {
		int amountToRefill;
		BanknoteDispenser banknoteDispenser;

		//finds the banknote dispenser correlated to the given banknote denomination
		banknoteDispenser = station.banknoteDispensers.get(banknoteDenomination);

		try {
			//finds how many more banknotes are needed before the banknote dispenser reaches capacity
			amountToRefill = banknoteDispenser.getCapacity() - banknoteDispenser.size();

			//refills the dispenser until it reaches capacity
			for (int i = 0; i < amountToRefill; i++) {
				Banknote banknoteToLoad = new Banknote(banknoteDenomination,currency);
				 banknoteDispenser.load(banknoteToLoad);
			}

		} catch (SimulationException | OverloadException e) {
			throw new SimulationException(e);
		}
	}

	public void AttendantEmptyCoinStorageUnit(CoinStorageUnit csu)
	{

		CoinStorageUnit CSU = csu;
		if(CSU.getCoinCount() <= 0)
		{
			System.out.println("No coins in Storage Unit. None unloaded");
		}
		List<Coin> coins = CSU.unload();

		System.out.println("Removed" + coins.size() + " many coins");


	}

	public void AttendantEmptyBanknoteStorageUnit(BanknoteStorageUnit bsu)
	{
		BanknoteStorageUnit BSU = bsu;

		if(BSU.getBanknoteCount() <= 0)
		{
			System.out.println("No banknote in Storage Unit. None unloaded");
		}
		List<Banknote> notes = BSU.unload();

		System.out.println("Removed" + notes.size() + " many coins");

	}
	
	public void startUp() {
		System.out.print("Starting up selfcheckout station...");
		enableAll();
	}
	
	public void shutDown() {
		System.out.print("Shutting down selfcheckout station...");
		disableAll();
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
	}
}
