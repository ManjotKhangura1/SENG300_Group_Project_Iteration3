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
import org.lsmr.selfcheckout.external.CardIssuer;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

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
	
	public BaggingArea baggingArea;
	public ScanItem scanItem;
	public EnterMembership enterMembership;
	public ScansMembershipCard scanMembership;
	public AddOwnBag addOwnBag;
	public BaggingAreaWeightIncorrect incorrectBaggingWeight;
	public ApproveWeightDiscrepency approveWeightDiscrepancy;
	public LookupProduct lookupProduct;
	public FinishesAddingItems finishesAddingItems;
	public RemoveItem removeItem;
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
	public Map<Barcode, BarcodedProduct> BarcodedProducts = new HashMap<>(); //ScanItem
	public Map<PriceLookupCode, Double> PLUCodedItems = new HashMap<>(); //removeItem
	public Map<PriceLookupCode, PLUCodedProduct> PLUProduct = new HashMap<>(); //EnterPLU and LookupItem
	public Map<String, PLUCodedProduct> PLULookup = new HashMap<>(); //cutomerLooksupItem
	public ArrayList<Barcode> barcodedItemList = new ArrayList<Barcode>();
	public String membershipNumber;
	public String PLUCode = "";
	public BigDecimal total;
	public double totalOwed = 0;
	
	JFrame frame = new JFrame(); //touchScreen.getFrame();

	public MainFrame()
	{
		initBarcodedItemsDatabase();
		initPLUCodedItems();
		initComponents();
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

		scanItem = new ScanItem(station, BarcodedProducts, finishesAddingItems, baggingArea);
		enterMembership = new EnterMembership(membershipNumber);
		scanMembership = new ScansMembershipCard(station);
		
		try {
			addOwnBag = new AddOwnBag(station);
			baggingArea = new BaggingArea(station);
			incorrectBaggingWeight = new BaggingAreaWeightIncorrect(station, finishesAddingItems);
			approveWeightDiscrepancy = new ApproveWeightDiscrepency(station, baggingArea);
			lookupProduct = new LookupProduct(PLUProduct);
			finishesAddingItems = new FinishesAddingItems(station, scanItem, baggingArea);
			removeItem = new RemoveItem(station, barcodedItemList, PLUCodedItems, total);
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

	}
	
	private void initPLUCodedItems() {
		PriceLookupCode plu1 = new PriceLookupCode("14040");
		PriceLookupCode plu2 = new PriceLookupCode("97310");
		PriceLookupCode plu3 = new PriceLookupCode("55589");
		PriceLookupCode plu4 = new PriceLookupCode("30897");

		PLUCodedItems.put(plu1, 2.00);
		PLUCodedItems.put(plu2, 1.97);
		PLUCodedItems.put(plu3, 4.37);
		PLUCodedItems.put(plu4, 2.50);
	}

	public void initBarcodedItemsDatabase() throws SimulationException {

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
		BarcodedProduct crackers = new BarcodedProduct(barcode6, "Crackers!", new BigDecimal("2.99"));

		BarcodedProducts.put(barcode1, milk);
		BarcodedProducts.put(barcode2, soymilk);
		BarcodedProducts.put(barcode3, bread);
		BarcodedProducts.put(barcode4, eggs);
		BarcodedProducts.put(barcode5, blackbeans);
		BarcodedProducts.put(barcode6, crackers);

	}

}
