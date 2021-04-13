package Software;
import java.util.ArrayList;
import java.util.Currency;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.BanknoteValidatorListener;

public class PayWithBanknote {
	public SelfCheckoutStation station;
	public ArrayList<Banknote> notesDeposited;
	public int totalPaid;
	public boolean accepted;
	//values representing if the banknote validator is enabled
	public boolean enabled;

	/**
	 * PayWithBanknote constructor that sets up the instance variable necessary for the class
	 * @param station The SelfCheckoutStation that is currently being used
	 * @throws SimulationException if a null station is given
	 */
	public PayWithBanknote(SelfCheckoutStation station) {
		if(station == null) {
			throw new SimulationException(new NullPointerException("station is null"));
		}
		this.station = station;
		//creates the arraylist of banknotes
		notesDeposited = new ArrayList<Banknote>();
		//sets the totalPaid to 0
		totalPaid = 0;
		accepted = false;
		enabled = true;
		startListener();
	}
	
	/**
	 * Alerts the banknote slot and banknote validator that a banknote has been inserted and if possible
	 * adds it to the banknote storage and updates the notesdeposited arraylist and totalPaid
	 * @param banknote The banknote that was inserted
	 * @throws SimulationException if null banknote is given
	 * @throws SimulationException if disabled exception or overloadexception was thrown by the methods called
	 */
	public void pay(Banknote banknote) {
		//only executes commands if validator is enabled
		if (enabled = true) {
			if(banknote == null) {
				throw new SimulationException(new NullPointerException("station is null"));
			}
			try {
				station.banknoteInput.accept(banknote);
				station.banknoteValidator.accept(banknote);
				station.banknoteStorage.accept(banknote);
			} catch (DisabledException | OverloadException e) {
				throw new SimulationException(e);
			}
			
			//after the banknote successfully passes through all the accept statements we know it is valid and add it to the list of notes deposited
			if(accepted == true) {			
				updateTotalPaid(banknote.getValue());
				updateList(banknote);
			}
		}
	}
	
	/**
	 * Registers the listener
	 */
	private void startListener() {
	
		station.banknoteValidator.register(new BanknoteValidatorListener() {
			
			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				enabled = true;
				
			}
			
			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				enabled = false;
				
			}
			
			@Override
			public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
				accepted = true;
				
			} 
			
			@Override
			public void invalidBanknoteDetected(BanknoteValidator validator) {
				accepted = false;
				
			}
		});
	}
	
	/**
	 * Gets the total value of the banknotes paid 
	 * @return totalPaid The total of all the banknotes received
	 */
	public int getTotalPaid() {
		return totalPaid;
	}
	
	/**
	 * Gets the selfCheckoutstation being used
	 * @return station The selfCheckoutStation
	 */
	public SelfCheckoutStation getStation() {
		return station;
	}
	
	/**
	 * Gets the ArrayList that contains the banknotes received
	 * @return notesDeposited The notesDeposited arrayList
	 */
	public ArrayList<Banknote> getNotesDeposited() {
		return notesDeposited;
	}
	
	/**
	 * Gets the accepted boolean
	 * @return accepted The accepted boolean
	 */
	public boolean getAccepted() {
		return accepted;
	}
	
	/**
	 * Gets the enabled boolean
	 * @return enabled The enabled boolean
	 */
	public boolean getEnabled() {
		return enabled;
	}
	
	/**
	 * Adds to the totalPaid 
	 * @param value The amount you want to add
	 */
	private void updateTotalPaid(int value) {
		totalPaid += value;
	}
	
	/** 
	 * Adds a banknote to the notes deposited arraylist
	 * @param banknote The banknote we want to add
	 */
	private void updateList(Banknote banknote) {
		notesDeposited.add(banknote);
	}
}
