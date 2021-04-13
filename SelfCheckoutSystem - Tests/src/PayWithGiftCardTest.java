import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.CardIssuer;

import Software.PayWithGiftCard;

import org.lsmr.selfcheckout.devices.SimulationException;


public class PayWithGiftCardTest {
	
	@Test
	public void testPayWithSwipe() {
		//SETUP.......................................................................................
		SelfCheckoutStation testStation;
		CardIssuer testIssuer;

		//create a self checkout station and the components necessary to create it
		Currency currency = Currency.getInstance("CAD");
		int[] noteDenominations = {5,10,20,50,100};
		BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
				BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
		int maxWeight = 23000;
		int scaleSensitivity = 10;
		testStation = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
		
		int error = 0;
		for(int i = 0; i < 100; i++) {
		try{
			//create an issuer to manage payments
			testIssuer = new CardIssuer("testIssuer");
			Calendar testCalendar =  Calendar.getInstance();
			testCalendar.set(Calendar.YEAR, 2030);
			testIssuer.addCardData("111111", "Customer", testCalendar, "111", BigDecimal.valueOf(1000.0));
		//.............................................................................................
		
			BufferedImage testSignature = new BufferedImage(10, 10, 1);
			PayWithGiftCard giftCard = new PayWithGiftCard(testStation);
			Card testCard = new Card("gift card", "111111", "Customer", "111", "1234", true, true);
	
			giftCard.PayWithSwipe(testCard, testIssuer, testSignature, BigDecimal.valueOf(100.0));
		}catch(Exception e){
				error++;
			}
		}
		//Delta is 5, accommodating for potential standard deviations. Can still fail because of outliers
		assertTrue(error <= 15);	
	}
	
	@Test(expected = SimulationException.class)
	public void testInvalidBalanceSwipe() {
		//SETUP.......................................................................................
				SelfCheckoutStation testStation;
				CardIssuer testIssuer;

				//create a self checkout station and the components necessary to create it
				Currency currency = Currency.getInstance("CAD");
				int[] noteDenominations = {5,10,20,50,100};
				BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), 
						BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
				int maxWeight = 23000;
				int scaleSensitivity = 10;
				testStation = new SelfCheckoutStation(currency, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
				
				//create an issuer to manage payments
				testIssuer = new CardIssuer("testIssuer");
				Calendar testCalendar =  Calendar.getInstance();
				testCalendar.set(Calendar.YEAR, 2030);
				testIssuer.addCardData("111111", "Customer", testCalendar, "111", BigDecimal.valueOf(5.0));
				//.............................................................................................
				
				BufferedImage testSignature = new BufferedImage(10, 10, 1);
				PayWithGiftCard giftCard = new PayWithGiftCard(testStation);
				Card testCard = new Card("gift card", "111111", "Customer", "111", "1234", true, true);
			
				giftCard.PayWithSwipe(testCard, testIssuer, testSignature, BigDecimal.valueOf(100.0));
	}
	
	//testing if swipe works with a valid gift card
	//should access the cardDataRead listener and set cardDataIsRead to true
	@Test
	public void testSwipeGiftCard_ValidID() {
		CardIssuer testIssuer;

		//parameters for instance of SelfCheckoutStation
		Currency currency= Currency.getInstance("CAD");
		int[] bankNoteDenominations= {5,10,20,50,100};
		BigDecimal[] coinDenominations= {new BigDecimal(0.01), new BigDecimal(0.05),
				new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
		int scaleMaximumWeight= 1000;
		int scaleSensitivity=1;
			
		SelfCheckoutStation scs = new SelfCheckoutStation(currency, bankNoteDenominations,
				coinDenominations, scaleMaximumWeight, scaleSensitivity);
		
		//create an issuer to manage payments
		testIssuer = new CardIssuer("testIssuer");
		Calendar testCalendar =  Calendar.getInstance();
		testCalendar.set(Calendar.YEAR, 2030);
		testIssuer.addCardData("111111", "Customer", testCalendar, "111", BigDecimal.valueOf(5.0));
		//.............................................................................................
			
		//instance of the class we are testing so all actions in the tests can be performed through it
		PayWithGiftCard scanGiftCardID = new PayWithGiftCard(scs);
		Card validCard = new Card("Membership", "1234567", "A Name", null, null, false, false);
		BufferedImage aSignature = new BufferedImage(1,2,3);
			
		for(int i=0;i<1000;i++) {
			scanGiftCardID.SwipeGiftCard(validCard, aSignature, testIssuer, BigDecimal.valueOf(5.0));
		}
		
		boolean actual= scanGiftCardID.cardDataIsRead;
		boolean expected= true;
		
		assertEquals(expected, actual);
		//Accounting for the probability of failure, added 5% delta 
		assertTrue(scanGiftCardID.probabilityCounter>=850);
		
	}
	
	//testing if swipe works with a invalid membership card
	//swiping car should be unsuccessful and cardDataIsRead should remain false 
	@Test
	public void testSwipeMembershipCard_Invalid() {
		
		CardIssuer testIssuer;

		//parameters for instance of SelfCheckoutStation
		Currency currency= Currency.getInstance("CAD");
		int[] bankNoteDenominations= {5,10,20,50,100};
		BigDecimal[] coinDenominations= {new BigDecimal(0.01), new BigDecimal(0.05),
				new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
		int scaleMaximumWeight= 1000;
		int scaleSensitivity=1;
			
		SelfCheckoutStation scs = new SelfCheckoutStation(currency, bankNoteDenominations,
				coinDenominations, scaleMaximumWeight, scaleSensitivity);
		
		//create an issuer to manage payments
		testIssuer = new CardIssuer("testIssuer");
		Calendar testCalendar =  Calendar.getInstance();
		testCalendar.set(Calendar.YEAR, 2030);
		testIssuer.addCardData("111111", "Customer", testCalendar, "111", BigDecimal.valueOf(5.0));
		//.............................................................................................
			
		PayWithGiftCard scanGiftCardID = new PayWithGiftCard(scs);
		Card invalidCard = new Card("Membership", "asdkjsdfjhs", "askjd", null, null, false, false);
		BufferedImage aSignature = new BufferedImage(1,2,3);
			
		scanGiftCardID.SwipeGiftCard(invalidCard, aSignature, testIssuer, null);
			
		boolean actual= scanGiftCardID.cardDataIsRead;
		boolean expected= false;
			
		assertEquals(expected, actual);
	}

	

}
