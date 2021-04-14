package Software;
import java.util.Scanner;

import org.lsmr.selfcheckout.devices.SimulationException;

public class EntersPlasticBagsUsed {
		

	private double pricePerPlasticBag = 0.10; //10 cents charge per plastic bag 
	private int totalPlasticBagsUsed;
	private double totalPlasticBagsUsedPrice;

	//sets the number of bags used
	public void setTotalPlasticBagsUsed(int bagsUsed) {
		if(bagsUsed >= 0) {
			this.totalPlasticBagsUsed = bagsUsed;
		}
		//if the inputed number is negative set bags to zero
		else {
			this.totalPlasticBagsUsed = 0;
		}
	}
	
	public int getTotalPlasticBagsUsed() {
		return totalPlasticBagsUsed;
	}
	
	
	/*
	 * This function will return the total charge for this respective # of the plastic bags used by the customer
	 * Use of this --> Increment this amount into the Total Price of all customer items in the GUI
	 */
	public double calculateTotalPlasticBagsUsedPrice() {
	
		totalPlasticBagsUsedPrice = pricePerPlasticBag * this.totalPlasticBagsUsed;
		return totalPlasticBagsUsedPrice;
	
	}
	
	

}
