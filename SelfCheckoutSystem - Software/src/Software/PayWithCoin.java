package Software;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.CoinValidatorListener;

public class PayWithCoin {

	public double coinsPaid;
	public ArrayList<Coin> depositedCoins;
	public SelfCheckoutStation station;

	// isEnabled is the value representing if the coinValidator is enabled
	private boolean isEnabled = true;
	// accepted is the boolean representing if valid coin is detected
	private boolean accepted = false;

	/**
	 * PayWithCoin this is a constructor that sets up the instant variable for the
	 * class
	 * 
	 * @param station SelfCheckoutStation
	 * 
	 * @throws SimulationException if a null station is given
	 */
	public PayWithCoin(SelfCheckoutStation station) {

		if (station == null) {
			throw new SimulationException(new NullPointerException("Station is null"));
		}

		this.station = station;

		depositedCoins = new ArrayList<Coin>(); // Coin array list is made.

		coinsPaid = 0.00; // total coins paid are 0.

		startListeners();
	}

	/**
	 * Updates the coin slot whether a coin has been inserted adds it to the coin
	 * storage and updates the notesdeposited arraylist and totalPaid
	 * 
	 * @param coin Coin inserted
	 * @throws DisabledException
	 * @throws OverloadException
	 * @throws SimulationException if no coin inserted
	 * @throws SimulationException if disabled exception or overloadexception was
	 *                             thrown by the methods called
	 */
	public void pay(Coin coin) throws DisabledException, OverloadException {
		// isEnabled is the value representing if the coinValidator is enabled
		if (isEnabled) {
			// Throwing exception if coin is null
			if (coin == null) {

				throw new SimulationException(new NullPointerException("Station is null."));
			}
			try {
				station.coinSlot.accept(coin);
				station.coinValidator.accept(coin);
				station.coinStorage.accept(coin);
			} catch (DisabledException | OverloadException e) {
				throw new SimulationException(e);
			}

			// accepted is the boolean representing if valid coin is detected
			if (accepted) {
				updateList(coin);
				updateCoinsPaid(coin.getValue().doubleValue());
			}
		}
	}

	/**
	 * Registers the listener
	 */
	private void startListeners() {
		// anonymous class for coin validator listener
		station.coinValidator.register(new CoinValidatorListener() {
			// Overriding the methods and setting corresponding booleans within the methods
			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				isEnabled = true;
			}

			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				isEnabled = false;
			}

			@Override
			public void validCoinDetected(CoinValidator validator, BigDecimal value) {
				accepted = true;

			}

			@Override
			public void invalidCoinDetected(CoinValidator validator) {
				accepted = false;
			}

		});
	}

	/**
	 * Gets isEnabled boolean value
	 * 
	 * @return isEnabled
	 */
	public boolean getEnabled() {
		return isEnabled;
	}

	/**
	 * Sets the isEnabled based on the input variable
	 * 
	 * @return isEnabled
	 */
	public void setEnabled(boolean b) {
		isEnabled = b;
	}

	/**
	 * Gets accepted boolean value
	 * 
	 * @return accepted
	 */
	public boolean getAccepted() {
		return accepted;
	}

	/**
	 * Gets selfCheckoutstation
	 * 
	 * @return station selfCheckoutStation
	 */
	public SelfCheckoutStation getStation() {
		return station;
	}

	/**
	 * Gets the total value of the coins paid
	 * 
	 * @return coinsPaid return the total value of coins inserted
	 */
	public double getcoinsPaid() {
		return coinsPaid;
	}

	/**
	 * gets arraylist containing the received coins
	 * 
	 * @return
	 */
	public ArrayList<Coin> getCoinsDepositied() {
		return depositedCoins;
	}

	/**
	 * Adds to the coinsPaid
	 * 
	 * @param value The amount you want to add
	 */
	private void updateCoinsPaid(double value) {
		coinsPaid += value;
	}

	/**
	 * Updates coins arraylist with added coin
	 * 
	 * @param coin the coin we will add
	 */
	private void updateList(Coin coin) {
		depositedCoins.add(coin);
	}

}
