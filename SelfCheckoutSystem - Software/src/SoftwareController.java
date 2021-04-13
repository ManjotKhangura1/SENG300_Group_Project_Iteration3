import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import stationGUI.MainFrame;

public class SoftwareController {

	public static SelfCheckoutStation station;
	public static Map<Barcode, BarcodedProduct> BarcodedProducts = new HashMap<>();
	public static Map<PriceLookupCode, Double> PLUCodedItems  = new HashMap<>();
	public static ArrayList<Barcode> barcodedItemList = new ArrayList<Barcode>();
	public static String membershipNumber;
	public static String PLUCode = "";
	public static BigDecimal total;
	public static double totalOwed = 0;
	
	
	public static void main(String[] args) throws OverloadException {
		
	
	Currency CAD = Currency.getInstance("CAD");
	int[] noteDenominations = {5,10,20,50,100};
	BigDecimal[] coinDenomonations = {BigDecimal.valueOf(0.05) , BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0)};
	int maxWeight = 23000;
	int scaleSensitivity = 10;
	
	station = new SelfCheckoutStation(CAD, noteDenominations, coinDenomonations, maxWeight,scaleSensitivity);
	
	initBarcodedItemsDatabase();
	initPLUCodedItems();
	
	// Instantiating these allows the methods of them to be accessible to the panels within the GUI
	
	DeclineBagPrompt declineBagPrompt = new DeclineBagPrompt();
	
	ScanItem scanItem = new ScanItem(station, BarcodedProducts, declineBagPrompt);
	EnterMembership enterMembership = new EnterMembership(membershipNumber); 
	ScansMembershipCard scanMembership = new ScansMembershipCard(station);
	AddOwnBag addOwnBag = new AddOwnBag(station);
	BaggingArea baggingArea = new BaggingArea(station);
	BaggingAreaWeightIncorrect incorrectBaggingWeight = new BaggingAreaWeightIncorrect(station, scanItem);
	ApproveWeightDiscrepency approveWeightDiscrepancy = new ApproveWeightDiscrepency(station, baggingArea);
	LookupProduct lookupProduct = new LookupProduct(station, PLUCode);
	FinishesAddingItems finishesAddingItems = new FinishesAddingItems(station, scanItem, baggingArea);
	RemoveItem removeItem = new RemoveItem(station, barcodedItemList, PLUCodedItems, total);
	PayWithBanknote payWithBanknote = new PayWithBanknote(station);
	PayWithCoin payWithCoin = new PayWithCoin(station);   
	PayWithDebit payWithDebit = new PayWithDebit(station); 
	PayWithGiftCard payWithGiftCard = new PayWithGiftCard(station);  
	GiveChange giveChange = new GiveChange(station, CAD, totalOwed, payWithBanknote, payWithCoin); 
	RemovePurchasedItems removePurchasedItems = new RemovePurchasedItems(station, scanItem, baggingArea, giveChange);
	ReturnsToAddingItems returnsToAddingItems = new ReturnsToAddingItems(station);
	
	//CustomerLookUpProduct customerLookUpProduct = new CustomerLookUpProduct();

	MainFrame mainFrame = new MainFrame();

	}


	
	
	private static void initPLUCodedItems() {
		PriceLookupCode plu1 = new PriceLookupCode("14040");
		PriceLookupCode plu2 = new PriceLookupCode("97310");
		PriceLookupCode plu3 = new PriceLookupCode("55589");
		PriceLookupCode plu4 = new PriceLookupCode("30897");
		
		PLUCodedItems.put(plu1, 2.00);
		PLUCodedItems.put(plu2, 1.97);
		PLUCodedItems.put(plu3, 4.37);
		PLUCodedItems.put(plu4, 2.50);
	}




	public static void initBarcodedItemsDatabase() throws SimulationException {
		
		Barcode barcode1 = new Barcode("1");
		Barcode barcode2 = new Barcode("2");
		Barcode barcode3 = new Barcode("3");
		Barcode barcode4 = new Barcode("4");
		Barcode barcode5 = new Barcode("5");
		Barcode barcode6 = new Barcode("6");
		
		
		BarcodedProduct milk = new BarcodedProduct(barcode1, "Fresh milk!", new BigDecimal("4.57"));
		BarcodedProduct soymilk = new BarcodedProduct(barcode2, "Soy milk fortified with B12!", new BigDecimal("3.49"));
		BarcodedProduct bread = new BarcodedProduct(barcode3, "This bread is baked fresh!", new BigDecimal("2.49"));
		BarcodedProduct eggs = new BarcodedProduct(barcode4, "These eggs are white and brown! ", new BigDecimal("3.29"));
		BarcodedProduct blackbeans = new BarcodedProduct(barcode5, "From Cuba!", new BigDecimal("2.99"));
		BarcodedProduct crackers = new BarcodedProduct(barcode6,"Crackers!", new BigDecimal("2.99")); 		
		
		BarcodedProducts.put(barcode1, milk); 
		BarcodedProducts.put(barcode2, soymilk); 
		BarcodedProducts.put(barcode3, bread);
		BarcodedProducts.put(barcode4, eggs); 
		BarcodedProducts.put(barcode5, blackbeans); 
		BarcodedProducts.put(barcode6, crackers); 
		
	}

}
