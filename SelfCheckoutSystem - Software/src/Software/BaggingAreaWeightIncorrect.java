package Software;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;


//baggingArea weight != scaleArea
//itemWeight != baggingAreaWeight
public class BaggingAreaWeightIncorrect {
	private SelfCheckoutStation scs;
	private ElectronicScale baggingArea;
	private ScanItem scanItem;
	
	private boolean weightCheck;
	private double scanWeight;
	private double bagAreaWeight;
	private double bagAreaSens;

	
	public BaggingAreaWeightIncorrect(SelfCheckoutStation selfCheckoutStation, ScanItem scanItem) {
		this.scs = selfCheckoutStation;
		this.baggingArea = selfCheckoutStation.baggingArea;
		this.scanItem = scanItem;
		this.bagAreaSens = baggingArea.getSensitivity();
		

	}
	
	public void calculate() throws OverloadException {
		bagAreaWeight = baggingArea.getCurrentWeight();
		scanWeight = scanItem.getTotalWeight();
		double difference = bagAreaWeight - scanWeight;
		//if negative make it positive
		if(difference <= 0) {
			difference = difference * -1;
		}
		if(difference <= bagAreaSens) {
			weightCheck = true;
		}
		else {
			weightCheck = false;
			scs.mainScanner.disable();
			scs.handheldScanner.disable();
		}
	}
	
	public boolean getWeightCheck() {
		return weightCheck;
	}
}
