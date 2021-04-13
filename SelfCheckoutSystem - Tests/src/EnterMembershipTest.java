import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import Software.EnterMembership;

public class EnterMembershipTest {
	@Test
	public void testFirstCard() {
		EnterMembership test = new EnterMembership("1234567");
		assertNotNull(test.getCurrentCard());
	}
	
	@Test
	public void testSecondCard() {
		EnterMembership test = new EnterMembership("2345678");
		assertNotNull(test.getCurrentCard());
	}
	
	@Test
	public void testInvalidCard() {
		EnterMembership test = new EnterMembership("3456789");
		assertNull(test.getCurrentCard());
	}
}