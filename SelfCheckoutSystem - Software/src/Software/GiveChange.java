package Software;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.BanknoteDispenserListener;
import org.lsmr.selfcheckout.devices.listeners.BanknoteSlotListener;
import org.lsmr.selfcheckout.devices.listeners.BanknoteValidatorListener;
import org.lsmr.selfcheckout.devices.listeners.CoinDispenserListener;
import org.lsmr.selfcheckout.devices.listeners.CoinTrayListener;

public class GiveChange {
	public SelfCheckoutStation station;
	public Currency currency;
	public PayWithBanknote payWithBanknote;
	public PayWithCoin payWithCoin;
	public double totalOwed;
	public double totalPaid;
	public double change;
	public int[] banknoteDenominations;
	public List<BigDecimal> coinDenominations;
	public boolean banknoteSlotEnabled = true;
	public boolean banknoteRemoved = true;
	public boolean coinTrayEnabled = true;
	public boolean failedToComplete;

	/**
	 * Give Change constructor 
	 * @param station station The SelfCheckoutStation that is currently being used
	 * @param currency The kind of currency permitted
	 * @param total The total cost of the transaction
	 * @param payWithBanknote The PayWithBanknote instance used to pay
	 * @param payWithCoin The PayWithCoin instance used to pay
	 */
	public GiveChange(SelfCheckoutStation station, Currency currency, double totalOwed, PayWithBanknote payWithBanknote, PayWithCoin payWithCoin) {
		double sum;
		
		setStation(station);
		setCurrency(currency);
		//grabbing the denominations values
		this.banknoteDenominations = station.banknoteDenominations;
		this.coinDenominations = station.coinDenominations;
		setTotalOwed(totalOwed);
		setPayWithBanknote(payWithBanknote);
		setPayWithCoin(payWithCoin);
	
		//grabbing the total paid by banknote and coins and adding them together
		sum = payWithBanknote.getTotalPaid() + payWithCoin.getcoinsPaid();
		setTotalPaid(sum);
		
		startListener();
		banknoteSlotEnabled = true;
		coinTrayEnabled = true;
	}
	
	/**
	 * Calculates the amount of change to be given
	 */
	public void calculateChange() {
		setChange(totalPaid - totalOwed);
	}
	
	/**
	 * Dispenses the change owed to the user
	 * @throws SimulationException If there is a dangling banknote in the slot
	 * @throws SimulationException If the banknote slot is disabled
	 * @throws SimulationException If the output channel is unable to accept another banknote
	 * @throws SimulationException If the coin tray overflows
	 * @throws SimulationException If the coin tray is disabled
	 * @throws SimulationException If no coins are present in the dispenser to release 
	 * @throws SimulationException If a banknote dispenser is disabled
	 * @throws SimulationException If a coin dispenser is disabled
	 * 
	 */
	public void dispense() {
		int amountToDispense;
		
		calculateChange();
		
		//sort the denominations to be greatest to least
		Arrays.sort(banknoteDenominations);
		Collections.sort(coinDenominations);
		
		if(banknoteSlotEnabled == false || coinTrayEnabled == false){
			throw new SimulationException(new DisabledException());
		}
			
		//calculates the amount of each type of banknote to dispense and tries to dispense it
		//starts with the largest denomination
		for(int i = banknoteDenominations.length - 1; i >= 0; i--) {
			if(change != 0) {
				amountToDispense = (int) Math.floor(change / banknoteDenominations[i]);
				//dispenses the amount needed of the current denomination
				for(int j = 0; j < amountToDispense; j++) {
					try {
						station.banknoteDispensers.get(banknoteDenominations[i]).emit();
						change = BigDecimal.valueOf(change).subtract(BigDecimal.valueOf(banknoteDenominations[i])).doubleValue(); 

						//manually removing this for now due to hardware limitations. We also don't have a user to do this at the moment
						station.banknoteOutput.removeDanglingBanknote();
						
						//waits until the banknote was removed 
						while(banknoteRemoved = false) {
							
						}
					} catch (SimulationException | DisabledException | OverloadException e) {
						throw new SimulationException(e);
					} catch (EmptyException e) {
						//if the denomination you want is empty, continue going through the loop because
						//changeLeftToDispense wouldn't have updated and the next denomination will handle the amount still owed
						continue;
					}				
				}
			}
		}
		
		//calculates the amount of each type of coin to dispense and tries to dispense it
		//starts with the largest denominations
		for(int i = coinDenominations.size() - 1; i >= 0; i--) {
			if(change != 0) {
				amountToDispense = (int) Math.floor(change / coinDenominations.get(i).doubleValue());
				//dispenses the amount needed of the current denomination
				for(int j = 0; j < amountToDispense; j++) {
					try {
						station.coinDispensers.get(coinDenominations.get(i)).emit();
						change = BigDecimal.valueOf(change).subtract(coinDenominations.get(i)).doubleValue(); 
					} catch (OverloadException | DisabledException e) {
						throw new SimulationException(e);
					} catch (EmptyException e) {
						//if the denomination you want is empty, continue going through the loop because
						//changeLeftToDispense wouldn't have updated and the next denomination will handle the amount still owed
						continue;
					}
				}
			}
		}
		
		//if the value we still have to give back to users is less than our lowest denomination and isn't 0.00, then we round up to the 
		//lowest denomination to give them the change, if system can't provide the change, change the boolean value letting the system
		//know the transaction wasn't completed and print out the issue
		if(change < coinDenominations.get(0).doubleValue() && change != 0.00) {
			try {
				station.coinDispensers.get(coinDenominations.get(0)).emit();
				failedToComplete = false;
			} catch (OverloadException | DisabledException e) {
				throw new SimulationException(e);
			} catch (EmptyException e) {
				failedToComplete = true;
				System.out.println("Could not finish giving change, please notify staff");
				throw new SimulationException(e);
			}
		//if the amount still owed to the user is not 0.00, change the boolean value letting the system know the
		//transaction wasn't completed and print out the issue
		}else if(change > 0.00){
			failedToComplete = true;
			System.out.println("Could not finish giving change, please notify staff");
		}else {
			failedToComplete = false;
		}
	}
	
	/**
	 * Registers the listener
	 */
	private void startListener() {
	
		station.banknoteOutput.register(new BanknoteSlotListener() {
			
			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				banknoteSlotEnabled = true;
				
			}
			
			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				banknoteSlotEnabled = false;
				
			}
			
			@Override
			public void banknoteRemoved(BanknoteSlot slot) {
				banknoteRemoved = true;
			}
			
			@Override
			public void banknoteInserted(BanknoteSlot slot) {
				// unnecessary to keep track of since this class takes care of giving change only (aka only ejecting)
				
			}
			
			@Override
			public void banknoteEjected(BanknoteSlot slot) {
				banknoteRemoved = false;
			}
		});
		
		station.coinTray.register(new CoinTrayListener() {
			
			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				coinTrayEnabled = true;
				
			}
			
			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				coinTrayEnabled = false;
				
			}
			
			@Override
			public void coinAdded(CoinTray tray) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * Gets selfCheckoutstation
	 * @return station selfCheckoutStation
	 */
	public SelfCheckoutStation getStation() {
		return station;
	}
	
	/**
	 * Sets the station to given selfCheckoutStation after checking if given is valid
	 * @param station The given selfCheckoutStation
	 * @throws SimulationException if a null station is given
	 */
	private void setStation(SelfCheckoutStation station) {
		//throws and exception if station given is null
		if (station == null) {
			throw new SimulationException(new NullPointerException("station is null"));
		}
		this.station = station;
	}
	
	/**
	 * Gets the permitted currency
	 * @return currency The kind of currency permitted
	 */
	public Currency getCurrency() {
		return currency;
	}
	
	/**
	 * Sets the currency to given currency after checking if given is valid
	 * @param currency The kind of currency permitted
	 * @throws SimulationException if a null currency is given
	 */
	private void setCurrency(Currency currency) {
		//throws and exception if station given is null
		if (currency == null) {
			throw new SimulationException(new NullPointerException("currency is null"));
		}
		this.currency = currency;
	}
	
	/**
	 * Gets payWithBankNote instance 
	 * @return payWithBanknote the payWithBanknote instance
	 */
	public PayWithBanknote getPayWithBanknote() {
		return payWithBanknote;
	}

	/**
	 * Sets the payWithBankote to given payWithBanknote after checking if given is valid
	 * @param payWithBanknote given payWithBanknote
	 * @throws SimulationException if a null payWithBanknote is given
	 */
	private void setPayWithBanknote(PayWithBanknote payWithBanknote) {
		if (payWithBanknote == null) {			
			throw new SimulationException(new NullPointerException("payWithBanknote is null"));
		}
		this.payWithBanknote = payWithBanknote;
	}
	
	/**
	 * Gets payWithCoin instance 
	 * @return payWithCoin the payWithCoin instance
	 */
	public PayWithCoin getPayWithCoin() {
		return payWithCoin;
	}

	/**
	 * Sets the payWithCoin to given payWithCoin after checking if given is valid
	 * @param payWithCoin given payWithCoin
	 * @throws SimulationException if a null payWithCoin is given
	 */
	private void setPayWithCoin(PayWithCoin payWithCoin) {
		if (payWithCoin == null) {			
			throw new SimulationException(new NullPointerException("payWithCoin is null"));
		}
		this.payWithCoin = payWithCoin;
	}

	/**
	 * Gets the total owed from the transaction 
	 * @return totalOwed the amount owed
	 */
	public double getTotalOwed() {
		return totalOwed;
	}

	/**
	 * Sets the totalOwed to given totalOwed after checking if given is valid
	 * @param totalOwed the given totalOwed
	 * @throws SimulationException if totalOwed is less than 0.00
	 */
	private void setTotalOwed(double totalOwed) {
		//throws exception if totalOwed is less than 0.00
		if (totalOwed < 0.00) {
			throw new SimulationException(
				new IllegalArgumentException("totalOwed cannot be less than nothing"));
		}
		this.totalOwed = totalOwed;
	}
	
	/**
	 * Gets the total owed from the transaction 
	 * @return totalOwed the amount owed
	 */
	public double getTotalPaid() {
		return totalPaid;
	}

	/**
	 * Sets the totalPaid to given totalPaid after checking if given is valid
	 * @param totalPaid the given totalPaid
	 */
	private void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
	
	/**
	 * Gets the change amount due for the transaction 
	 * @return change the amount of change 
	 */
	public double getChange() {
		return change;
	}

	/**
	 * Sets the change amount to given change amount after checking if given is valid
	 * @param change the given change
	 * @throws SimulationException if change is less than 0.00
	 */
	private void setChange(double change) {
		//throws exception if totalOwed is less than 0.00
		if (change < 0.00) {
			throw new SimulationException(
				new IllegalArgumentException("amount of change to be given cannot be less than nothing"));
		}
		this.change = change;
	}
	
	/**
	 * Gets the banknoteSlotEnabled boolean
	 * @return banknoteSlotEnabled The banknoteSlotEnabled boolean
	 */
	public boolean getBanknoteSlotEnabled() {
		return banknoteSlotEnabled;
	}
	
	/**
	 * Gets the coinTrayEnabled boolean
	 * @return coinTrayEnabled The coinTrayEnabled boolean
	 */
	public boolean getCoinTrayEnabled() {
		return coinTrayEnabled;
	}
	
	/**
	 * Gets the banknoteRemoved boolean
	 * @return banknoteRemoved The banknoteRemoved boolean
	 */
	public boolean getBanknoteRemoved() {
		return banknoteRemoved;
	}
	
	/**
	 * Gets the failedToComplete boolean
	 * @return failedToComplete The failedToComplete boolean
	 */
	public boolean getFailedToComplete() {
		return failedToComplete;
	}

}
