package Software;
import java.util.HashMap;

import org.lsmr.selfcheckout.Card;

public class EnterMembership {
	private HashMap<String, Card> membershipDatabase= new HashMap<>();
	private Card currentCard;
	
	/**
	 * Constructor
	 * @param membershipNumber
	 */
	public EnterMembership(String membershipNumber) {
		initializeMembershipDatabase();
		currentCard = membershipDatabase.get(membershipNumber);
	}
	
	/**
	 * Initialize membership database
	 */
	private void initializeMembershipDatabase() {
		Card card1 = new Card("Membership", "1234567", "Name", null, null, false, false);
		Card card2 = new Card("Membership", "2345678", "Name", null, null, false, false);
		membershipDatabase.put("1234567", card1);
		membershipDatabase.put("2345678", card2);
	}
	
	/**
	 * Returns the membership card currently entered (null or one registered in database)
	 * @return Card currentCard
	 */
	public Card getCurrentCard() {
		return currentCard;
	}
}