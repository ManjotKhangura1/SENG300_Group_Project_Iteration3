import java.util.Scanner;

public class EntersPlasticBagsUsed {
		
	
	public double pricePerPlasticBag = 0.10; //10 cents charge per plastic bag 
	public int totalPlasticBagsUsed;
	public double totalPlasticBagsUsedPrice;
	
	public int askNumOfPlasticBagsUsed() {
		
		System.out.println("Enter the total number of plastic bags used: ");
		Scanner scan= new Scanner(System.in);
		totalPlasticBagsUsed = scan.nextInt();
		return totalPlasticBagsUsed;
	}
	
	public double calculateTotalPlasticBagsUsedPrice() {
	
		totalPlasticBagsUsedPrice = 0.10 * this.totalPlasticBagsUsed;
		return totalPlasticBagsUsedPrice;
	
	}
}
