package Software;
import java.math.BigDecimal;

import org.lsmr.selfcheckout.Item;

import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.listeners.AbstractDeviceListener;
import org.lsmr.selfcheckout.devices.listeners.ElectronicScaleListener;

public class BaggingArea {

	
	public ElectronicScale baggingArea;
	
	private BigDecimal totalWeightInBagging = new BigDecimal(0.00);
	private BigDecimal totalWeightScanned = new BigDecimal(0.00);
	
	private boolean weightChanged = false;
	private boolean overload= false;
	private boolean isEnabled = false;

	/**
	 * Creates and initializes the electronic scale (as bagging area) from a given selfcheckout station
	 * 
	 * @param SelfCheckoutStation
	 *            The self checkout station with the bagging area.
	 * @throws OverloadException
	 *             If the current weight on the bagging area is higher than the weight limit.
	 * @throws SimulationException
	 * 			   If the self checkout station being passed in the parameter is null.          
	 */
	public BaggingArea(SelfCheckoutStation selfCheckout) throws OverloadException {
		if (selfCheckout == null) {
			throw new SimulationException(new NullPointerException("Self checkout station is null."));
		}
		this.baggingArea = selfCheckout.baggingArea;
		//this.baggingArea.enable();
		
		updateWeight();
		
		startListener();
		
		this.totalWeightInBagging = new BigDecimal(0.0);
		this.totalWeightScanned = new BigDecimal(0.0);

	}
	
	/**
	 * Creates and initializes the electronic scale (as bagging area) from a given electronic scale
	 * 
	 * @param electronicScale
	 *            The electronic scale to be set as the bagging area.
	 * @throws SimulationException
	 * 			   If the electronic scale being passed in the parameter is null.              
	 */
	public BaggingArea(ElectronicScale electronicScale){
		if (electronicScale == null) {
			throw new SimulationException(new NullPointerException("Electronic scale is null."));
		}
		
		this.baggingArea = electronicScale;
		
		startListener();
		
	}

	/**
	 * Creating and registering a listener for the class.
	 *               
	 */
	private void startListener() {
		baggingArea.register(new ElectronicScaleListener(){
			
			//Overriding the methods from the listener and setting the booleans accordingly
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
				weightChanged = true;
			}

			@Override
			public void overload(ElectronicScale scale) {
				overload = true;
			}

			@Override
			public void outOfOverload(ElectronicScale scale) {
				overload = false;
			}
		});
	}
	
	/**
	 * Adds an item to the bagging area.
	 * 
	 * @param item
	 *            The item to be added to the bagging area.
	 * @throws DisabledException
	 *             If the electronic scale is disabled when trying to add the item.
	 * @throws SimulationException
	 * 			   If the item being added is null.
	 * @throws SimulationException
	 *             If the same item is added more than once.
	 * @throws SimulationException
	 *             If more items in the bagging are than total items scanned.
	 * @throws SimulationException
	 *             If the item added has caused the total weight to go over limit or item was not added correctly
	 */
	public void addItem(Item item) throws DisabledException {
		//Checking if the bagging area is disabled/ if the item is null and total weight is valid
		//before adding the item
		if(baggingArea.isDisabled()) {
			throw new DisabledException();
		}
		if(item == null) {
			throw new SimulationException(new NullPointerException("Null item being added."));
		}

		if(totalWeightInBagging.add(BigDecimal.valueOf(item.getWeight())).compareTo(totalWeightScanned) == 1) {
			throw new SimulationException("More items in bagging area than scanned. Unable to add item.");
		}
		
		baggingArea.add(item);
		totalWeightInBagging = totalWeightInBagging.add(new BigDecimal(item.getWeight()));
		
		//Checking if the bagging area has overloaded, in which case the exception is thrown
		if(overload) {
			throw new SimulationException("Please remove items, weight limit exceeded.");
		}
		//Checking if the weightChanged boolean is false, in which case the item may not have been added properly
		else if(!weightChanged) {
			throw new SimulationException("Item not added to bagging area succesfully.");
		}
		else {
			weightChanged = false;
		}
	}

	/**
	 * Removes an item to the bagging area.
	 * 
	 * @param item
	 *            The item to be removed from the bagging area.
	 * @throws DisabledException
	 *             If the electronic scale is disabled when trying to remove the item.
	 * @throws OverloadException 
	 * 			   If there is more weight on the scale than the allowed weight limit.
	 * @throws SimulationException
	 * 			   If the item being removed is null.
	 * @throws SimulationException
	 *             If the item is not on the scale.
	 * @throws SimulationException
	 *             If there are no items on the scale.
	 */
	public void removeItem(Item item) throws DisabledException, OverloadException {
		//checking if bagging area is disabled/item is null/ no items in bagging area before removing the item
		if(baggingArea.isDisabled()) {
			throw new DisabledException();
		}
		if(item == null) {
			throw new SimulationException(new NullPointerException("Null item being removed."));
		}
		if(totalWeightInBagging.compareTo(new BigDecimal(0.0)) == 0) {
			throw new SimulationException("No items in the bagging area.");
		}
		
		baggingArea.remove(item);
		updateWeight();
		
		weightChanged = false;
		
	}
	
	/**
	 * Updates the weight of totalWeight to the electronicScale's weight
	 * 
	 * @throws OverloadException
	 * 				If there is more weight on the scale than the allowed weight limit.
	 */
	public void updateWeight() throws OverloadException {
		this.totalWeightInBagging = BigDecimal.valueOf(baggingArea.getCurrentWeight());
	}
	
	/**
	 * Sets the weight of totalWeight to the given parameter
	 * 
	 * @param weight - the weight of items in the bagging area
	 * 
	 * @throws SimulationException
	 * 			If the weight being set is negative.
	 */
	public void setWeightBaggingArea(BigDecimal weight) {
		if(weight.compareTo(BigDecimal.valueOf(0.0)) < 0) {
			throw new SimulationException("Invalid weight.");
		}
		this.totalWeightInBagging = weight;
	}

	/**
	 * Gets the weight of all items currently in the bagging area
	 * 
	 * @return The total weight currently in the bagging area
	 */
	public BigDecimal getWeightBaggingArea() {
		return totalWeightInBagging;
	}
	
	/**
	 * 
	 * Sets the weight of the total weight of scanned items to the given parameter
	 * 
	 * @param weight - the total weight of all items scanned so far
	 * @throws SimulationException
	 * 			If the weight being set is negative.
	 * @throws SimulationException
	 * 			If the weight being set is greater than the total weight of scanned items.
	 */
	public void setWeightScanned(BigDecimal weight) {
		if(totalWeightInBagging.compareTo(totalWeightScanned) > 0 && weight.compareTo(BigDecimal.valueOf(0.0)) >= 0) {
			throw new SimulationException("More items in bagging area than scanned.");
		}
		
		if(weight.compareTo(BigDecimal.valueOf(0.0)) < 0) {
			throw new SimulationException("Invalid weight.");
		}
		this.totalWeightScanned = this.totalWeightScanned.add(weight);
	}
	
	/**
	 * Subtracts the weight from the total weight of scanned items by the given parameter
	 * @param weight
	 * @throws SimulationException
	 * 			If the weight being set is negative.
	 */
	public void removeWeightScanned(BigDecimal weight) {
		if(weight.compareTo(BigDecimal.valueOf(0.0)) < 0 || weight == null) {
			throw new SimulationException("Invalid weight.");
		}
		this.totalWeightScanned = this.totalWeightScanned.subtract(weight);
	}
	
	/**
	 * Gets the weight of all items currently scanned which may not have been bagged yet.
	 * 
	 * @return The total weight of all items scanned
	 */
	public BigDecimal getWeightScanned() {
		return totalWeightScanned;
	}
	
	/**
	 * Gets the bagging area with all of the items bagged
	 * 
	 * @return The bagging area.
	 */
	public ElectronicScale getBaggingArea() {
		return baggingArea;
	}
	
	/**
	 * Setter for the weightChanged boolean
	 * @param b 
	 * 		The boolean we are setting weightChanged to
	 */
	
	public void setweightChanged(boolean b) {
		weightChanged = b;
	}
	
	/**
	 * Setting for the overload boolean
	 * @param b
	 * 		The boolean we are setting overload to 
	 */
	
	public void setoverload(boolean b) {
		overload = b;
	}
	/**
	 * Getter for the weight changed
	 * 
	 * @return weightChanged boolean
	 */
	
	public boolean getweightChanged() {
		return weightChanged;
	}
	
	/**
	 * Getter for the overload boolean
	 * 
	 * @return overload boolean
	 */
	public boolean getoverload() {
		return overload;
	}
	
	/**
	 * Getter for the isEnabled boolean
	 * 
	 * @return isEnabled boolean
	 */
	public boolean getIsEnabled() {
		return isEnabled;
	}
	
}