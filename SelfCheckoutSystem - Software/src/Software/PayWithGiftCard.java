package Software;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.CardReaderListener;
import org.lsmr.selfcheckout.external.CardIssuer;

public class PayWithGiftCard {
	
public SelfCheckoutStation aSelfCheckoutStation;
	
	//initially card data is not read
	public boolean cardDataIsRead=false;
	public boolean cardIsSwiped=false;
	
	public int probabilityCounter;
	
	public CardData data;
	
	//instance of some arbitrary valid cards for testing purposes
	//(false, false) because a membership card doesn't have a chip and can't be tapped
	Card validCard1 = new Card("Membership", "1234567", "A Name", null, null, false, false);
	Card validCard2 = new Card("Membership", "2345678", "A Name", null, null, false, false);
	
	//creating a database with valid card numbers
	public  HashMap<String, Card> validGiftCardID= new HashMap<>();

	//read the card using class CardReader which makes use of CardReaderListener
	//different ways to read card: tap, swipe, insert
	//SelfCheckoutstation has a cardReader so use everything off that
	//all functions in CardReader basically just read the card using different methods & return the data
	
	public CardReaderListener crl = new CardReaderListener() {
		
		//We do not make use of these 5 listeners
		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
		// TODO Auto-generated method stub	
			}
		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void cardInserted(CardReader reader) {
			// TODO Auto-generated method stub	
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
			cardIsSwiped=true;
		}
		
		@Override
		public void cardDataRead(CardReader reader, CardData data) {
			cardDataIsRead=true;
			//to keep count of probability 
			probabilityCounter=probabilityCounter+1;
				
		}	  
	  };  
	  
	  public PayWithGiftCard(SelfCheckoutStation station) {
		  
		  aSelfCheckoutStation= station;
			
			//registering card reader and listener
			aSelfCheckoutStation.cardReader.register(crl);
			
			//initializing database with valid membership
			 validGiftCardID.put("1234567", validCard1);
			 validGiftCardID.put("2345678", validCard2);
		  
	  }
	  
	  public CardData SwipeGiftCard(Card aGiftCard, BufferedImage aSignature, CardIssuer issuer, BigDecimal amount) {
			try {
				String cardID = (aGiftCard.swipe().getNumber());
				
				if(validGiftCardID.containsKey(cardID)){
					try {
						data=aSelfCheckoutStation.cardReader.swipe(aGiftCard, aSignature);
						
						if(cardDataIsRead==true) {
							return data;
						}
						
						if ( !HandleGiftCardHold(data, issuer, amount)) {
							throw new SimulationException("Error when completing transaction");
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						
					}
						}
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				
			}
			
				return null;		
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
		private boolean HandleGiftCardHold(CardData data, CardIssuer issuer, BigDecimal amount) {
			int holdNumber = issuer.authorizeHold(data.getNumber(), amount);
			if (holdNumber != -1) {
				return issuer.postTransaction(data.getNumber(), holdNumber, amount);
				
			} else {
				return issuer.releaseHold(data.getNumber(), holdNumber);
			}
		}

	public void PayWithSwipe(Card testCard, CardIssuer testIssuer, BufferedImage testSignature, BigDecimal valueOf) {
		// TODO Auto-generated method stub
		
	}

}
