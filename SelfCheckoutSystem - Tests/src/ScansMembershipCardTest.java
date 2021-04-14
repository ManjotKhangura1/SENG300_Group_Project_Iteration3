import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

import Software.ScansMembershipCard;

public class ScansMembershipCardTest {
	
	//parameters for instance of SelfCheckoutStation
	Currency currency= Currency.getInstance("CAD");
	int[] bankNoteDenominations= {5,10,20,50,100};
	BigDecimal[] coinDenominations= {new BigDecimal(0.01), new BigDecimal(0.05),
			 new BigDecimal(0.1),new BigDecimal(0.25), new BigDecimal(0.50)};
	int scaleMaximumWeight= 1000;
	int scaleSensitivity=1;
	
	SelfCheckoutStation scs = new SelfCheckoutStation(currency, bankNoteDenominations,
			coinDenominations, scaleMaximumWeight, scaleSensitivity);
	
	//instance of the class we are testing so all actions in the tests can be performed through it
	ScansMembershipCard scanMembership = new ScansMembershipCard(scs);
		
	
	//testing if swipe works with a valid membership card
	//should access the cardDataRead listener and set cardDataIsRead to true
	@Test
	public void testSwipeMembershipCard_Valid() {
		Card validCard = new Card("Membership", "1234567", "A Name", null, null, false, false);
		BufferedImage aSignature = new BufferedImage(1,2,3);
		
		//testing the probability
		for(int i=0;i<1000;i++) {
			scanMembership.swipeMembershipCard(validCard, aSignature);
		}
		
		
		boolean actual= scanMembership.cardDataIsRead;
		boolean expected= true;
		
		assertEquals(expected, actual);
		//Accounting for the probability of failure, added 5% delta 
		assertTrue(scanMembership.probabilityCounter>=850);
		
	}
	
	//testing if swipe works with a invalid membership card
	//swiping car should be unsuccessful and cardDataIsRead should remain false 
	@Test
	public void testSwipeMembershipCard_Invalid() {
		
		Card invalidCard = new Card("Membership", "asdkjsdfjhs", "askjd", null, null, false, false);
		BufferedImage aSignature = new BufferedImage(1,2,3);
		
		scanMembership.swipeMembershipCard(invalidCard, aSignature);
		
		boolean actual= scanMembership.cardDataIsRead;
		boolean expected= false;
		
		assertEquals(expected, actual);
	}
	
}
