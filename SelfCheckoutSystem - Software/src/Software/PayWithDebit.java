package Software;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.CardReaderListener;
import org.lsmr.selfcheckout.external.CardIssuer;



public class PayWithDebit {

	private SelfCheckoutStation checkoutStation;
	public CardIssuer cardIssuer;
	
	/* Constructors */
	public PayWithDebit(SelfCheckoutStation station) {
		this.checkoutStation = station;
	}
	
	//pay with swipe
	public boolean PayWithSwipe(Card card, CardIssuer issuer,  BufferedImage signature, BigDecimal amount)  {
		try {
			CardData data = this.checkoutStation.cardReader.swipe(card, signature);
			if ( !HandleDebitHold(data, issuer, amount)) {
				throw new SimulationException("Error when completing transaction");
			}
		} catch (IOException e) {
			throw new SimulationException(e);
		}
		return true;
	}
	
	
	//pay with tap
	public boolean PayWithTap(Card card, CardIssuer issuer, BigDecimal amount)  {
		try {
			CardData data = this.checkoutStation.cardReader.tap(card);
			if(data == null) {
				throw new SimulationException("Tap not enabled");
			}
			if ( !HandleDebitHold(data, issuer, amount)) {
				throw new SimulationException("Error when completing transaction");
			}
			
		} catch (IOException e) {
			throw new SimulationException(e);
		}
		return true;

	}
	
	public boolean PayWithInsert(Card card, CardIssuer issuer, BigDecimal amount, String pin)  {
		try {
			CardData data = this.checkoutStation.cardReader.insert(card, pin);
			
			if ( !HandleDebitHold(data, issuer, amount)) {
				throw new SimulationException("Error when completing transaction");
			}
			this.checkoutStation.cardReader.remove();
		} catch (IOException e) {
			this.checkoutStation.cardReader.remove(); 	// Either way if you succeed or fail you have to remove the card
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * Handles the debit transactions for your Card Issuer
	 * 2 cases : 
	 * 	a) If HoldNumber returns -1 then the transaction has failed, and you need to release the hold
	 * 	b) If HoldNumber returns anything else, then take that hold number and call PostTransaction, which will post & release the hold
	 * @param data Card data to be used
	 * @param issuer Issuer for the card
	 * @param amount Amount that you're looking to spend
	 */
	private boolean HandleDebitHold(CardData data, CardIssuer issuer, BigDecimal amount) {
		int holdNumber = issuer.authorizeHold(data.getNumber(), amount);
		if (holdNumber != -1) {
			return issuer.postTransaction(data.getNumber(), holdNumber, amount);
			
		} else {
			return issuer.releaseHold(data.getNumber(), holdNumber);
		}
	}
}



