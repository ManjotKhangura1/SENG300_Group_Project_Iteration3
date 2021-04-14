package Software;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.CardReaderListener;
import org.lsmr.selfcheckout.external.CardIssuer;

public class PayWithCreditCard {
	
	//enable and disable flag
	public boolean enabled = true;
	
	//tracker for total amount paid
	public BigDecimal amountPaid;
	
	//flags
	private static boolean inProgress = false;
	private static boolean cardRead = false;
	public static boolean isCompleted = false;
	public static boolean isApproved = false;
	
	//interfaces and databases
	public SelfCheckoutStation checkoutStation;
	public static CardIssuer issuer;
	
	
	//information on current transaction
	private static int currentHoldNumber = -1;
	private static String currentCardNumber = "";
	private static BigDecimal currentAmount;
	
	private CardReaderListener cardListener = new CardReaderListener() {

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			// TODO Auto-generated method stub
			enabled = true;
			
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			// TODO Auto-generated method stub
			enabled = false;
			
		}

		@Override
		public void cardInserted(CardReader reader) {
			
		}

		@Override
		public void cardRemoved(CardReader reader) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void cardTapped(CardReader reader) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void cardSwiped(CardReader reader) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void cardDataRead(CardReader reader, CardData data) {
			// TODO Auto-generated method stub
			cardRead = true;
			
		}
		
	};
	
	
	//constructor
	public PayWithCreditCard(SelfCheckoutStation st, CardIssuer issuer){
		this.checkoutStation = st;
		checkoutStation.cardReader.register(cardListener);
		this.issuer = issuer;
		amountPaid = BigDecimal.ZERO;
		currentAmount = BigDecimal.ZERO;
	}
	
	
	
	//paying with tap
	public void payWithTap(Card card, BigDecimal amount) throws SimulationException {
		
		try {
			
			if(!enabled) {
				resetTransaction();
				return;
			}
			
			//set system to "in-progress"
			inProgress = true; 
			
			
			//try to read card from tap and save card info
			CardData cardInfo = checkoutStation.cardReader.tap(card);
			
			//if card data is read successfully, proceed to processing transaction with bank
			//check if the issuer can place card on hold
			currentCardNumber = cardInfo.getNumber();
			currentAmount = amount;
			currentHoldNumber = issuer.authorizeHold(currentCardNumber, amount);
			isCompleted = true;
			if(currentHoldNumber < 0 )
			{
				throw new SimulationException("Payment not authorized");
			}
				
			makePayment();
				
		} catch (Exception e) {
			resetTransaction();
			throw new SimulationException("Card data was not read.");
		}
		
		
	}
	
	
	//paying with swipe
	public void payWithSwipe(Card card, BigDecimal amount, BufferedImage sig) throws SimulationException {
		
		if(!enabled) {
			resetTransaction();
			return;
		}
		
		try {
			//set system to "in-progress"
			inProgress = true;
			
			
			//try to read card from tap and save card info
			CardData cardInfo = checkoutStation.cardReader.swipe(card, sig);
			
			//if card data is read successfully, proceed to processing transaction with bank
			//check if the issuer can place card on hold
			currentCardNumber = cardInfo.getNumber();
			currentAmount = amount;
			currentHoldNumber = issuer.authorizeHold(currentCardNumber, amount);
			isCompleted = true;
			if(currentHoldNumber < 0 )
			{
				throw new SimulationException("Payment not authorized");
			}
				
			makePayment();
				
		} catch (Exception e) {
			resetTransaction();
			throw new SimulationException("Card data was not read.");
		}
		
		
	}
	
	//paying with swipe
	public void payWithInsert(Card card, BigDecimal amount, String pin) throws SimulationException {
		
		if(!enabled) {
			resetTransaction();
			return;
		}
		
		
		try {
			//set system to "in-progress"
			inProgress = true;
			
			
			//try to read card from tap and save card info
			CardData cardInfo = checkoutStation.cardReader.insert(card, pin);
			
			//if card data is read successfully, proceed to processing transaction with bank
			//check if the issuer can place card on hold
			currentCardNumber = cardInfo.getNumber();
			currentAmount = amount;
			currentHoldNumber = issuer.authorizeHold(currentCardNumber, amount);
			isCompleted = true;
			if(currentHoldNumber < 0)
			{
				throw new SimulationException("Payment not authorized");
			}
				
			//at this point, the payment should be set up, ready to be paid
				
		} catch (Exception e) {
			//resetTransaction();
			throw new SimulationException("Card data was not read.");
		}
		
		
	}
	

	
	//reset all flags
	private static void resetTransaction() {
		try {
			issuer.releaseHold(currentCardNumber, currentHoldNumber);
		}catch(Exception e) {}
		cardRead = false;
		inProgress = false;
		isCompleted = false;
		isApproved = false;
		currentCardNumber = "";
		currentHoldNumber = -1;
		currentAmount = BigDecimal.ZERO;
		
	}
	
	//release hold and make payment
	public boolean makePayment() throws SimulationException {
		try {
			isApproved = true;
			
			issuer.postTransaction(currentCardNumber, currentHoldNumber, currentAmount);
			
			amountPaid = amountPaid.add(currentAmount);
			
			
			resetTransaction();
			return true;
		}catch(Exception e) {
			resetTransaction();
			throw new SimulationException("Payment could not go through");
		}
		
	}	
	
	public void cancelPayment() {
		resetTransaction(); 
		try { issuer.releaseHold(currentCardNumber, currentHoldNumber); }catch(Exception e){}
	}
	
	public void removeCard() {
		resetTransaction();
		checkoutStation.cardReader.remove();
	}
	
	public boolean getInProgress() {
		return inProgress;
	}
	
	public BigDecimal getCurrentAmount(){
		return currentAmount;
	}
	
	
}
