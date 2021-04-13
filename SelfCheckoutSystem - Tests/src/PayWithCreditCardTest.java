import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.external.CardIssuer;

import Software.PayWithCreditCard;

public class PayWithCreditCardTest {
	
	
	
	//tests disabled system
		@Test
		public void testDisabledTap() {
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "111111", "Person One", "111", "1234", true, true);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			creditPayment.checkoutStation.cardReader.disable();
			for(int i=0; i<100; i++) {
				try {
					
					BigDecimal testPrice = BigDecimal.TEN;
					creditPayment.payWithTap(testCard, testPrice);
					
				}catch(Exception e) {}
			}
			
	
			assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
			assertTrue(!creditPayment.getInProgress());
			
		}
	
		
		//tests disabled system
		@Test
		public void testDisabledSwipe() {
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "111111", "Person One", "111", "1234", true, true);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			creditPayment.checkoutStation.cardReader.disable();
			for(int i=0; i<100; i++) {
				try {
					
					BigDecimal testPrice = BigDecimal.TEN;
					creditPayment.payWithSwipe(testCard, testPrice, null);
					
				}catch(Exception e) {}
			}
			
	
			assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
			assertTrue(!creditPayment.getInProgress());
			
		}
		
		//tests disabled system
		@Test
		public void testDisabledInsert() {
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "111111", "Person One", "111", "1234", true, true);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			creditPayment.checkoutStation.cardReader.disable();
			for(int i=0; i<100; i++) {
				try {
					if(creditPayment.getCurrentAmount()==BigDecimal.ZERO)
						creditPayment.removeCard();
					BigDecimal testPrice = BigDecimal.TEN;
					creditPayment.payWithInsert(testCard, testPrice, "1234");
					
				}catch(Exception e) {}
			}
			
	
			assertTrue(creditPayment.getCurrentAmount().compareTo(BigDecimal.ZERO) == 0);
			assertTrue(!creditPayment.getInProgress());
			
		}
	
	//tests a simple valid tap
	@Test
	public void testTap() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1234", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
		creditPayment.checkoutStation.cardReader.enable();
		for(int i=0; i<100; i++) {
			try {
				
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithTap(testCard, testPrice);
				
			}catch(Exception e) {}
		}
		
		assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.TEN) > 0);
		assertTrue(!creditPayment.getInProgress());
		
	}
	
	//tests a card that does not have the tap feature
	@Test
	public void testTapDisabled() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1234", false, false);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
		for(int i=0; i<100; i++) {
			try {
	
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithTap(testCard, testPrice);
				creditPayment.makePayment();
				
			}catch(Exception e) {}
		}
		
		assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
		assertTrue(!creditPayment.getInProgress());
		
	}
	
	//tests a tap with a card that does not exist in the bank's database
	@Test
	public void testTapIncorrectCard() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "444444", "Person One", "111", "1234", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			try {
				
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithTap(testCard, testPrice);
				
			}catch(Exception e) {}
		
		assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
		assertTrue(!creditPayment.getInProgress());
	}
	
	
	//processes a tap with a card that does not have sufficient funds
	@Test
	public void testTapInsufficientFunds() {
		
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "222222", "Person One", "111", "1234", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			try {
				
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithTap(testCard, testPrice);
				
			}catch(Exception e) {}
		
		assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
		assertTrue(!creditPayment.getInProgress());
		
	}
	
	
	
	//tests the insertion and putting on hold of payment
	@Test
	public void testInsert() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("100000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1111", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
		BigDecimal testPrice = BigDecimal.TEN;
		for(int i=0; i<1000; i++) {
			try {
				if(creditPayment.getCurrentAmount()==BigDecimal.ZERO)
					creditPayment.removeCard();
				creditPayment.payWithInsert(testCard, testPrice, "1111");
				
			}catch(Exception e) {}
		}
		
		
		assertTrue(creditPayment.getCurrentAmount().compareTo(BigDecimal.TEN) >= 0);
		assertTrue(creditPayment.getInProgress());
		
	}
	
	
	//tests the insertion with a card not in the database
	@Test
	public void testInsertBadCard() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "1111211", "Person One", "111", "1111", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
		for(int i=0; i<100; i++) {
			try {
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithInsert(testCard, testPrice, "1111");
				
			}catch(Exception e) {}
		}
		

		assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
		assertTrue(creditPayment.getInProgress());
		
	}
	
	
	//tests insert with incorrect pin
	@Test
	public void testInsertWrongPin() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1111", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
		for(int i=0; i<100; i++) {
			try {
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithInsert(testCard, testPrice, "2222");
				
			}catch(Exception e) {}
		}
		
		assertTrue(creditPayment.getCurrentAmount().compareTo(BigDecimal.ZERO) == 0);
		assertTrue(creditPayment.getInProgress());
		
	}
	
	//tests insertion and confirmation of payment
	@Test
	public void testInsertConfirm() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1111", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
		for(int i=0; i<100; i++) {
			try {
				if(creditPayment.getCurrentAmount()==BigDecimal.ZERO)
					creditPayment.removeCard();
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithInsert(testCard, testPrice, "1111");
				
			}catch(Exception e) {}
		}
		
		try{creditPayment.makePayment();}catch(Exception e) {}
		
		assertTrue(creditPayment.getCurrentAmount().compareTo(BigDecimal.ZERO) == 0);
		assertEquals(creditPayment.amountPaid, BigDecimal.TEN);
		assertTrue(!creditPayment.getInProgress());
		
	}
	
	//tests insertion and then cancellation of payment
	@Test
	public void testInsertCancel() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1111", true, true);
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
		for(int i=0; i<100; i++) {
			try {
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithInsert(testCard, testPrice, "1111");
				
			}catch(Exception e) {}
		}
		
		try{creditPayment.cancelPayment();}catch(Exception e) {}
		
		assertTrue(creditPayment.getCurrentAmount().compareTo(BigDecimal.ZERO) == 0);
		assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
		assertTrue(!creditPayment.getInProgress());
		
	}
	
	//tests insertion of second card without removing the first one
	@Test
	public void insertWithoutRemoving() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1111", true, true);
		for(int i=0; i<100; i++) {
			try {
				if(creditPayment.getCurrentAmount()==BigDecimal.ZERO)
					creditPayment.removeCard();
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithInsert(testCard, testPrice, "1111");
				
			}catch(Exception e) {}
		}
		
		Card testCard2 = new Card("credit", "222222", "Person Two", "222", "2222", true, true);
		for(int i=0; i<100; i++) {
			try {
				BigDecimal testPrice = BigDecimal.ONE;
				creditPayment.payWithInsert(testCard2, testPrice, "1111");
				
			}catch(Exception e) {}
		}
		
		try{creditPayment.makePayment();}catch(Exception e) {}
		
		assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.TEN) == 0);
		assertTrue(!creditPayment.getInProgress());
		
	}
	
	//tests insertion, removing, and then a second insertion
	@Test
	public void removeAndInsert() {
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
		testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("100000"));
		testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
		testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
		//.............................................................................................
		PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			
		Card testCard = new Card("credit", "111111", "Person One", "111", "1111", true, true);
		for(int i=0; i<100; i++) {
			try {
				if(creditPayment.getCurrentAmount()==BigDecimal.ZERO)
					creditPayment.removeCard();
				BigDecimal testPrice = BigDecimal.TEN;
				creditPayment.payWithInsert(testCard, testPrice, "1111");
				
			}catch(Exception e) {}
		}
		
		creditPayment.makePayment();
		creditPayment.removeCard();
		
		for(int i=0; i<100; i++) {
			try {
				if(creditPayment.getCurrentAmount()==BigDecimal.ZERO)
					creditPayment.removeCard();
				BigDecimal testPrice2 = BigDecimal.ONE;
				creditPayment.payWithInsert(testCard, testPrice2, "1111");
			}catch(Exception e) {}
		}
		
		creditPayment.makePayment();
		creditPayment.removeCard();
		
		
		assertEquals(creditPayment.amountPaid, new BigDecimal("11"));
		assertTrue(!creditPayment.getInProgress());
		
	}
	
	
	//tests insertion of card without chip
		@Test
		public void insertNoChipCard() {
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "111111", "Person One", "111", "1111", false, false);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			for(int i=0; i<100; i++) {
				try {
					BigDecimal testPrice = BigDecimal.TEN;
					creditPayment.payWithInsert(testCard, testPrice, "1111");
					
				}catch(Exception e) {}
			}
			
			try{creditPayment.makePayment();}catch(Exception e) {}
			
			assertTrue(creditPayment.getCurrentAmount().compareTo(BigDecimal.ZERO) == 0);
			assertEquals(creditPayment.amountPaid, BigDecimal.ZERO);
			assertTrue(!creditPayment.getInProgress());
			
		}
	
		//tests a simple valid swipe
		@Test
		public void testSwipe() {
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "111111", "Person One", "111", "1234", true, true);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			for(int i=0; i<100; i++) {
				try {
					
					BigDecimal testPrice = BigDecimal.TEN;
					creditPayment.payWithSwipe(testCard, testPrice, null);
					
				}catch(Exception e) {}
			}
			
			assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.TEN) > 0);
			assertTrue(!creditPayment.getInProgress());
			
		}
	
		//tests a swipe with a card that does not exist in the bank's database
		@Test
		public void testSwipeIncorrectCard() {
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "444444", "Person One", "111", "1234", true, true);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
				try {
					
					BigDecimal testPrice = BigDecimal.TEN;
					creditPayment.payWithSwipe(testCard, testPrice, null);
					
				}catch(Exception e) {}
			
			assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
			assertTrue(!creditPayment.getInProgress());
		}
	
		
		//processes a tap with a card that does not have sufficient funds
		@Test
		public void testSwipeInsufficientFunds() { 
			
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "222222", "Person One", "111", "1234", true, true);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
				try {
					
					BigDecimal testPrice = BigDecimal.TEN;
					creditPayment.payWithSwipe(testCard, testPrice, null);
					
				}catch(Exception e) {}
			
			assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
			assertTrue(!creditPayment.getInProgress());
			
		}
	
		
		//make payment when no hold is placed
		@Test
		public void testWrongMakePayment() { 
			
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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", new BigDecimal("1000"));
			testIssuer.addCardData("222222", "Person Two", testCalendar, "222", new BigDecimal("5"));
			testIssuer.addCardData("333333", "Person Three", testCalendar, "333", new BigDecimal("10"));
			//.............................................................................................
			
				
			Card testCard = new Card("credit", "222222", "Person One", "111", "1234", true, true);
			PayWithCreditCard creditPayment = new PayWithCreditCard(testStation, testIssuer);
			
			try{
				creditPayment.makePayment();
			}catch(Exception e) {
				
			}
			
			assertTrue(creditPayment.amountPaid.compareTo(BigDecimal.ZERO) == 0);
			assertTrue(!creditPayment.getInProgress());
			
		}

}

