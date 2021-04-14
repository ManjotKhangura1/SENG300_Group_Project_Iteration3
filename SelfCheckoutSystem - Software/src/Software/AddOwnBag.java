package Software;
import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.ElectronicScaleListener;

public class AddOwnBag {
	private ElectronicScale baggingArea;
	private boolean isEnabled = true;
	
	/**
	 * 
	 * @param selfCheckout
	 * @throws OverloadException
	 */
	public AddOwnBag(SelfCheckoutStation selfCheckout) throws OverloadException {
		if (selfCheckout == null) {
			throw new SimulationException(new NullPointerException("Self checkout station is null."));
		}
		this.baggingArea = selfCheckout.baggingArea;
		selfCheckout.baggingArea.register(esl);
		
	}
	
	private ElectronicScaleListener esl = new ElectronicScaleListener() {

		@Override
		public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			isEnabled = true;
		}

		@Override
		public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
			isEnabled = false;
		}

		@Override
		public void weightChanged(ElectronicScale scale, double weightInGrams) {
			// TODO Auto-generated method stub
			//isPlaced = true;
		}

		@Override
		public void overload(ElectronicScale scale) {}

		@Override
		public void outOfOverload(ElectronicScale scale) {}
		
	};
	
	
	
	/**
	 * adds a bag to the scale
	 * @param bag
	 * @throws DisabledException
	 */
	public void addBag(Bag bag) throws DisabledException {
		if(!isEnabled) {
			throw new DisabledException();
		}
		if(bag == null) {
			throw new SimulationException(new NullPointerException("Null bag being added."));
		}
		baggingArea.add(bag);
		
	}
	
	/**
	 * removes a bag from the scale
	 * @param bag
	 * @throws DisabledException
	 * @throws OverloadException
	 */
	public void removeBag(Bag bag) throws DisabledException, OverloadException {
		if(!isEnabled) {
			throw new DisabledException();
		}
		if(bag == null) {
			throw new SimulationException(new NullPointerException("Null bag being removed."));
		}
		baggingArea.remove(bag);
		
		
	}
	

}
