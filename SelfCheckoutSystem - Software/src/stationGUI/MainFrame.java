package stationGUI;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.TouchScreen;
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Item;

import Software.*;
import net.miginfocom.swing.MigLayout;

//import BaggingArea;

public class MainFrame {
	
	public AddItemPanel addItemPanel;
	public AttendantLoginPanel attendantLoginPanel;
	public AttendantPanel attendantPanel;
	public HelpPanel helpPanel;
	public ReceiptPanel receiptPanel;
	public ScanningPanel scanningPanel;
	public WelcomePanel welcomePanel;
	public BaggingAreaPanel baggingAreaPanel;
	public CashWaitingPanel cashWaitingPanel;
	public CreditDebitWaitingPanel creditDebitWaitingPanel;
	public GiftCardWaitingPanel giftCardWaitingPanel;
	public AcknowledgementPanel acknowledgementPanel;
	public PaymentPanel paymentPanel;
	public MembershipWaitingPanel membershipWaitingPanel;

	public BaggingArea baggingArea;
	public ScanItem scanItem;
	public EnterMembership enterMembership;
	public ScansMembershipCard scanMembership;
	public AddOwnBag addOwnBag;
	public ApproveWeightDiscrepency approveWeightDiscrepancy;
	public FinishesAddingItems finishesAddingItems;
	public PayWithBanknote payWithBanknote;
	public PayWithCoin payWithCoin;
	public PayWithDebit payWithDebit;
	public PayWithCreditCard payWithCreditCard;
	public PayWithGiftCard payWithGiftCard;
	public GiveChange giveChange;
	public RemovePurchasedItems removePurchasedItems;
	public ReturnsToAddingItems returnsToAddingItems;
	public Maintenance maintenance;
	public FailBagging failBagging;
	public EntersPlasticBagsUsed bagsUsed;
	public EnterPLU enterPLU;
	public CardIssuer cardIssuer;

	
	public SelfCheckoutStation station;

	public Map<Barcode, BarcodedItem> BarcodedItems = new HashMap<>();
	//public Map<PriceLookupCode, Double> PLUCodedItems = new HashMap<>(); //removeItem
	//public Map<PriceLookupCode, PLUCodedProduct> PLUCodedItems = new HashMap<>(); //EnterPLU and LookupItem
	public Map<String, PLUCodedProduct> PLULookup = new HashMap<>(); //cutomerLooksupItem
	//public ArrayList<Barcode> barcodedItemList = new ArrayList<Barcode>();
	public String membershipNumber;
	public String PLUCode = "";
	public BigDecimal total;
	public double totalOwed = 0;
	

	
	TouchScreen touchscreen = new TouchScreen();
	//JFrame frame = new JFrame(); //touchScreen.getFrame();
	JFrame frame = touchscreen.getFrame();
	
	
	
	

	public MainFrame()
	{
		initBarcodedProductsDatabase();
		initBarcodedItemsDatabase();
		initPLUCodedItems();
		initComponents();
		// initInventory(); Will do this for completeness within the database if I have time to compile all the products together
	}
	
	/**
	 * Initializes basic components of the frame
	 */
	private void initComponents()
	{
		Currency CAD = Currency.getInstance("CAD");
		int[] noteDenominations = { 5, 10, 20, 50, 100 };
		BigDecimal[] coinDenomonations = { BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25),
				BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0) };
		int maxWeight = 23000;
		int scaleSensitivity = 10;

		station = new SelfCheckoutStation(CAD, noteDenominations, coinDenomonations, maxWeight, scaleSensitivity);

		// Instantiating these allows the methods of them to be accessible to the panels
		// within the GUI

		scanItem = new ScanItem(station, ProductDatabases.BARCODED_PRODUCT_DATABASE, finishesAddingItems, baggingArea);
		enterMembership = new EnterMembership(membershipNumber);
		scanMembership = new ScansMembershipCard(station);
		
		try {
			addOwnBag = new AddOwnBag(station);
			baggingArea = new BaggingArea(station);
			approveWeightDiscrepancy = new ApproveWeightDiscrepency(station, baggingArea);
			finishesAddingItems = new FinishesAddingItems(station, baggingArea);
			payWithBanknote = new PayWithBanknote(station);
			payWithCoin = new PayWithCoin(station);
			payWithDebit = new PayWithDebit(station);
			payWithCreditCard = new PayWithCreditCard(station, cardIssuer);
			payWithGiftCard = new PayWithGiftCard(station);
			giveChange = new GiveChange(station, CAD, totalOwed, payWithBanknote, payWithCoin);
			removePurchasedItems = new RemovePurchasedItems(station, scanItem, baggingArea, giveChange);
			returnsToAddingItems = new ReturnsToAddingItems(station);
			maintenance = new Maintenance(station);

		} catch (OverloadException e) {
			e.printStackTrace();
		}
		addItemPanel = new AddItemPanel(this);
		attendantLoginPanel = new AttendantLoginPanel(this);
		attendantPanel = new AttendantPanel(this);
		helpPanel = new HelpPanel(this);
		receiptPanel = new ReceiptPanel(this);
		scanningPanel = new ScanningPanel(this);
		baggingAreaPanel = new BaggingAreaPanel(this);
		welcomePanel = new WelcomePanel(this);
		acknowledgementPanel = new AcknowledgementPanel(this);
		attendantPanel = new AttendantPanel(this);
		cashWaitingPanel = new CashWaitingPanel(this);
		creditDebitWaitingPanel = new CreditDebitWaitingPanel(this);
		giftCardWaitingPanel = new GiftCardWaitingPanel(this);
		paymentPanel = new PaymentPanel(this);
		membershipWaitingPanel = new MembershipWaitingPanel(this);

		frame.setBounds(0,0,1280,720);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.exit(0);
            }
        });
		
		scanningPanel.setVisible(true);
		
		frame.getContentPane().add(scanningPanel);
		frame.getContentPane().add(addItemPanel);
		frame.getContentPane().add(attendantLoginPanel);
		frame.getContentPane().add(attendantPanel);
		frame.getContentPane().add(helpPanel);
		frame.getContentPane().add(receiptPanel);
		frame.getContentPane().add(baggingAreaPanel);
		frame.getContentPane().add(welcomePanel);
		frame.getContentPane().add(acknowledgementPanel);
		frame.getContentPane().add(attendantLoginPanel);
		frame.getContentPane().add(paymentPanel);
		frame.getContentPane().add(cashWaitingPanel);
		frame.getContentPane().add(creditDebitWaitingPanel);
		frame.getContentPane().add(giftCardWaitingPanel);
		frame.getContentPane().add(membershipWaitingPanel);
	}
	
	private void initPLUCodedItems() {
		PriceLookupCode plu1 = new PriceLookupCode("14040");
		PriceLookupCode plu2 = new PriceLookupCode("97310");
		PriceLookupCode plu3 = new PriceLookupCode("55589");
		PriceLookupCode plu4 = new PriceLookupCode("30897");

		//EnterPLU and LookupItem
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu1, new PLUCodedProduct(plu1, "Onions", BigDecimal.valueOf(2.0)));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu2, new PLUCodedProduct(plu2, "Potatoes", BigDecimal.valueOf(1.96)));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu3, new PLUCodedProduct(plu3, "Apples", BigDecimal.valueOf(0.50)));
		ProductDatabases.PLU_PRODUCT_DATABASE.put(plu4, new PLUCodedProduct(plu4, "Oranges", BigDecimal.valueOf(1.62)));
	}

	/*
	 * Make Barcoded Product Database
	 */
	public void initBarcodedProductsDatabase() throws SimulationException {

		Barcode barcode1 = new Barcode("1");
		Barcode barcode2 = new Barcode("2");
		Barcode barcode3 = new Barcode("3");
		Barcode barcode4 = new Barcode("4");
		Barcode barcode5 = new Barcode("5");
		Barcode barcode6 = new Barcode("6");

		BarcodedProduct milk = new BarcodedProduct(barcode1, "Milk", new BigDecimal("4.57")); // 4800g
		BarcodedProduct soymilk = new BarcodedProduct(barcode2, "Soy Milk", new BigDecimal("3.49")); // 3890g
		BarcodedProduct bread = new BarcodedProduct(barcode3, "Bread", new BigDecimal("2.49")); // 350g 
		BarcodedProduct eggs = new BarcodedProduct(barcode4, "Eggs", new BigDecimal("3.29")); // 300g
		BarcodedProduct blackbeans = new BarcodedProduct(barcode5, "Black beans", new BigDecimal("2.99")); // 450g
		BarcodedProduct crackers = new BarcodedProduct(barcode6, "Crackers", new BigDecimal("2.99")); // 200g

		//ScanItem and RemoveItems
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode1, milk);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, soymilk);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode3, bread);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode4, eggs);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode5, blackbeans);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode6, crackers);

	}
	/*
	 * To store the weights of BarcodedItems
	 */
	public void initBarcodedItemsDatabase() {
		
		Barcode barcode1 = new Barcode("1");
		Barcode barcode2 = new Barcode("2");
		Barcode barcode3 = new Barcode("3");
		Barcode barcode4 = new Barcode("4");
		Barcode barcode5 = new Barcode("5");
		Barcode barcode6 = new Barcode("6");

		BarcodedItem milk = new BarcodedItem(barcode1, 4800);
		BarcodedItem soymilk = new BarcodedItem(barcode2, 3890);
		BarcodedItem bread = new BarcodedItem(barcode3, 350); 
		BarcodedItem eggs = new BarcodedItem(barcode4, 300);
		BarcodedItem blackbeans = new BarcodedItem(barcode5, 450);
		BarcodedItem crackers = new BarcodedItem(barcode6, 200); 

		BarcodedItems.put(barcode1, milk);
		BarcodedItems.put(barcode2, soymilk);
		BarcodedItems.put(barcode3, bread);
		BarcodedItems.put(barcode4, eggs);
		BarcodedItems.put(barcode5, blackbeans);
		
		BarcodedItems.put(barcode6, crackers);
		
	}

}
