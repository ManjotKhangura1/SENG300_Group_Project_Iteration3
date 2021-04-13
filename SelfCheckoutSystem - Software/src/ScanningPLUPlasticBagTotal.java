import java.util.ArrayList;

//calculates the Total of scanned items, total of PLU scanned items, and total of platic bags used
public class ScanningPLUPlasticBagTotal {
	private ScanItem scanItem;
	private AddPLUItem AddPLU;

	
	private double TOTAL_CURRENT_PRICE;
	private ArrayList<String> TOTAL_CURRENT_LIST;

	//initializes AddPLU, scanItem and the arrayList
	//also adds the scannedItem list and PLU-scanned item lists into a total list
	public ScanningPLUPlasticBagTotal (ScanItem scanItem, AddPLUItem addPLU){
		this.AddPLU = addPLU;
		this.scanItem = scanItem;
		this.TOTAL_CURRENT_LIST = new ArrayList<String>();
		
		this.TOTAL_CURRENT_PRICE = this.scanItem.getTotalPrice() + AddPLU.getTotalPrice();
		if (scanItem.getTotalList() != null) {
			for(String scannedItem : scanItem.getTotalList()) {
				TOTAL_CURRENT_LIST.add(scannedItem);
			}
		}
		if (AddPLU.getTotalList() != null) {
			for(String PLUItem : AddPLU.getTotalList()) {
				TOTAL_CURRENT_LIST.add(PLUItem);
			}
		}
	}
	
	//getter for price
	public double getTotalCurrentPrice() {
		return TOTAL_CURRENT_PRICE;
	}
	
	//getter for the list
	public ArrayList<String> getTotalCurrentList() {
		return TOTAL_CURRENT_LIST;
	}
	
	//adds plastic bags into the total price and total list after initializing EntersPlasticBagsUsed class
	public void addPlasticBag(int bagsUsed) {
		EntersPlasticBagsUsed epbu = new EntersPlasticBagsUsed(bagsUsed);
		TOTAL_CURRENT_PRICE += epbu.calculateTotalPlasticBagsUsedPrice();
		TOTAL_CURRENT_LIST.add("Plastic Bags: " + epbu.getTotalPlasticBagsUsed());
	}
}
