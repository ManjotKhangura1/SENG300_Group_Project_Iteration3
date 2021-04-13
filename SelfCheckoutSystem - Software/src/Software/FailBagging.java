package Software;
import org.lsmr.selfcheckout.Item;

import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.ElectronicScaleListener;

/**
 * This control software is used when an item is scanned. Once it is scanned, the scanners lock and do not unlock
 * until the item is put in the bagging area. The weight of the bagging area is compared to the item weight +
 * the previous weight of the bagging area and if this is what we get then the scanners unlock and we can
 * go to the next item (if there is a next item). Most of the other cases to fail in putting an item in the
 * bagging area was covered in the BaggingArea class from iteration 1.
 * 
 * @author manjot
 *
 */


public class FailBagging {

	private ElectronicScale baggingArea;	
	private SelfCheckoutStation selfCheckout;	
	private Item item;
	private double itemWeight = 0;				//Weight of item being put in bagging area
	private boolean lock;						//Checks if scanner is locked
	private double weightBeforeChange = 0;		//Weight of bagging area before item is added
	
	
	/**
	 * Constructor for FailBagging
	 * 
	 * @param selfCheckout - self checkout station object
	 */
	public FailBagging(SelfCheckoutStation selfCheckout)
	{
		//Gets bagging area from self checkout station
		this.baggingArea = selfCheckout.baggingArea;
		
		this.selfCheckout = selfCheckout;
		
		//Register the listener
		this.baggingArea.register(createBaggingListener());
		
	}
	

	/**
	 * Getting for checking if scanner is locked
	 * @return lock - boolean for checking if scanner is locked
	 */
	public boolean isLock() {
		return lock;
	}
	
	public void setLock(boolean changeLock)
	{
		lock = changeLock;
	}
	
	/**
	 * Creates a listener by returning the ElectronicScaleListener
	 * @return ElectonicScaleListener - Listener for the ElectronicScale class
	 */
	private ElectronicScaleListener createBaggingListener()
	{
		return new ElectronicScaleListener() {

			@Override
			public void enabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void disabled(AbstractDevice<? extends AbstractDeviceListener> device) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void weightChanged(ElectronicScale scale, double weightInGrams) {
				//Checks if the item was properly added to bagging area to unlock the scanners
				if ((itemWeight + weightBeforeChange) == weightInGrams)
				{
					if (isLock())
					{
						unlockScan();
					}
				}
				
				//Gets current weight of bagging area
				weightBeforeChange = weightInGrams;
				
			}

			@Override
			public void overload(ElectronicScale scale) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void outOfOverload(ElectronicScale scale) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	
	/**
	 * Locks the scanners
	 * @param item - item we are looking for to put in bagging area
	 * @throws OverloadException 
	 */
	public void lockScan(Item item) throws OverloadException
	{
		weightBeforeChange = baggingArea.getCurrentWeight();
		lock = true;
		itemWeight = item.getWeight();
		selfCheckout.mainScanner.disable();
		selfCheckout.handheldScanner.disable();
	}
	
	/**
	 * Unlocks the scanner and resets item weight to 0 for next item to be added
	 */
	private void unlockScan()
	{
		lock = false;
		itemWeight = 0;
		selfCheckout.mainScanner.enable();
		selfCheckout.handheldScanner.enable();
	}
	
	/**
	 * Used to override the scanners and unlock by admin for other potential reasons
	 */
	public void adminOverride()
	{
		if (isLock())
		{
			unlockScan();
		}
	}
	
	
}
