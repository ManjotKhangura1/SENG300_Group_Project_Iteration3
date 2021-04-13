import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.ChipFailureException;
import org.lsmr.selfcheckout.InvalidPINException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.external.CardIssuer;

import Software.PayWithDebit;

import org.lsmr.selfcheckout.devices.SimulationException;


public class PayWithDebitTest {

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
			testIssuer.addCardData("111111", "Person One", testCalendar, "111", BigDecimal.valueOf(1000.0));
		//.............................................................................................
		
			BufferedImage testSignature = new BufferedImage(10, 10, 1);
			PayWithDebit debit = new PayWithDebit(testStation);
			Card testCard = new Card("debit", "111111", "Person One", "111", "1234", true, true);
	
				debit.PayWithSwipe(testCard, testIssuer, testSignature, BigDecimal.valueOf(100.0));
		}catch(Exception e){
				error++;
			}
		}
		//Delta is 5, accommodating for potential standard deviations. Can still fail because of outliers
		assertTrue(error <= 15);	
	}
	

	@Test
	public void testPayWithTap() {
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
		for(int i = 0; i < 1000; i++) {
			try{
				//create an issuer to manage payments
				testIssuer = new CardIssuer("testIssuer");
				Calendar testCalendar =  Calendar.getInstance();
				testCalendar.set(Calendar.YEAR, 2030);
				testIssuer.addCardData("111111", "Person One", testCalendar, "111", BigDecimal.valueOf(1000.0));
				//.............................................................................................
				
				BufferedImage testSignature = new BufferedImage(10, 10, 1);
				PayWithDebit debit = new PayWithDebit(testStation);
				Card testCard = new Card("debit", "111111", "Person One", "111", "1234", true, true);
			
				debit.PayWithTap(testCard, testIssuer, BigDecimal.valueOf(100.0));
			}catch(Exception e){
				error++;
			}
		}
		//Delta is 5, accommodating for potential standard deviations. Can still fail because of outliers
		assertTrue(error <= 15);	
	}
	

	@Test
	public void testPayWithChip() {
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
		for(int i = 0; i < 1000; i++) {
			try{
				//create an issuer to manage payments
				testIssuer = new CardIssuer("testIssuer");
				Calendar testCalendar =  Calendar.getInstance();
				testCalendar.set(Calendar.YEAR, 2030);
				testIssuer.addCardData("111111", "Person One", testCalendar, "111", BigDecimal.valueOf(1000.0));
				//.............................................................................................
				
				BufferedImage testSignature = new BufferedImage(10, 10, 1);
				PayWithDebit debit = new PayWithDebit(testStation);
				Card testCard = new Card("debit", "111111", "Person One", "111", "1234", true, true);
			
				debit.PayWithInsert(testCard, testIssuer, BigDecimal.valueOf(100.0), "1234");
			}catch(Exception e){
				error++;
			}
		}
		assertTrue(error <= 10);	
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
				testIssuer.addCardData("111111", "Person One", testCalendar, "111", BigDecimal.valueOf(5.0));
				//.............................................................................................
				
				BufferedImage testSignature = new BufferedImage(10, 10, 1);
				PayWithDebit debit = new PayWithDebit(testStation);
				Card testCard = new Card("debit", "111111", "Person One", "111", "1234", true, true);
			
				debit.PayWithSwipe(testCard, testIssuer, testSignature, BigDecimal.valueOf(100.0));
	}
	
	@Test(expected = SimulationException.class)
	public void testInvalidBalanceTap() {
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
				testIssuer.addCardData("111111", "Person One", testCalendar, "111", BigDecimal.valueOf(5.0));
				//.............................................................................................
				
				PayWithDebit debit = new PayWithDebit(testStation);
				Card testCard = new Card("debit", "111111", "Person One", "111", "1234", true, true);
			
				debit.PayWithTap(testCard, testIssuer, BigDecimal.valueOf(100.0));
	}
	
	
	@Test(expected = SimulationException.class)
	public void testInvalidBalanceInsert() {
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
				testIssuer.addCardData("111111", "Person One", testCalendar, "111", BigDecimal.valueOf(5.0));
				//.............................................................................................
				
				PayWithDebit debit = new PayWithDebit(testStation);
				Card testCard = new Card("debit", "111111", "Person One", "111", "1234", true, true);
			
				debit.PayWithInsert(testCard, testIssuer, BigDecimal.valueOf(100.0), "1234");
	}

	
	@Test(expected = SimulationException.class)
	public void testTapNotEnabled() {
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
				//.............................................................................................
				
				PayWithDebit debit = new PayWithDebit(testStation);
				Card testCard = new Card("debit", "111111", "Person One", "111", "1234", false, true);
				debit.PayWithTap(testCard, testIssuer, BigDecimal.valueOf(100.0));
	}
	
}
