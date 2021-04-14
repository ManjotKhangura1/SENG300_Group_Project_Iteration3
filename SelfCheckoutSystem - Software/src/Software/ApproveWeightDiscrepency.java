package Software;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;

public class ApproveWeightDiscrepency {
	public ElectronicScale bagging;
	public BaggingArea area;

	public ApproveWeightDiscrepency(SelfCheckoutStation station, BaggingArea problem) {
		if(station == null || problem == null)
			throw new SimulationException("station or bagging are are null");
		bagging = station.baggingArea;
		area = problem;
	}
	
	/**
	 * approve the weight discrepancy and set the expected weight to the current weight
	 * @throws Exception 
	 */
	public void approve() throws Exception {
		try {
			area.setWeightScanned(BigDecimal.valueOf(bagging.getCurrentWeight()));
		}catch(OverloadException e) {
			throw e; //from getCurrentWeight
		}
	}
}
