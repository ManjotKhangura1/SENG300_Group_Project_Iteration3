import java.util.Scanner;

public class EntersPlasticBagsUsed {
		
	
	public double pricePerPlasticBag = 0.10; //10 cents charge per plastic bag 
	public int totalPlasticBagsUsed;
	public double totalPlasticBagsUsedPrice;

	//Prompts the user for the number of bags used
	public int askNumOfPlasticBagsUsed() {
		
		System.out.println("Enter the total number of plastic bags used: ");
		Scanner scan= new Scanner(System.in);
		totalPlasticBagsUsed = scan.nextInt();
		return totalPlasticBagsUsed;
	}
	
	/*
	 * This function will return the total charge for this respective # of the plastic bags used by the customer
	 * Use of this --> Increment this amount into the Total Price of all customer items in the GUI
	 */
	public double calculateTotalPlasticBagsUsedPrice() {
	
		totalPlasticBagsUsedPrice = 0.10 * this.totalPlasticBagsUsed;
		return totalPlasticBagsUsedPrice;
	
	}
}
