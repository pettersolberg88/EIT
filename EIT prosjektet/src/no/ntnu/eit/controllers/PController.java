package no.ntnu.eit.controllers;

import no.ntnu.eit.mainTypes.CarInstructions;
import no.ntnu.eit.mainTypes.CarStateData;
import no.ntnu.eit.mainTypes.Controller;

public class PController extends Controller{
	
	private static final float ACCELERATOR_FACTOR = 1.0f;
	private static final float BRAKE_FACTOR = 1.0f;
	@Override
	public CarInstructions step(CarStateData csd) {
		
		CarInstructions ci = new CarInstructions();
		
		if(getPreferredDistance(csd) - csd.getDistanceToObjectInFront() >= 0){
			ci.setBrake((getPreferredDistance(csd) - csd.getDistanceToObjectInFront())* BRAKE_FACTOR);
		}
		else{
			ci.setThrottle((csd.getDistanceToObjectInFront() - getPreferredDistance(csd)) * ACCELERATOR_FACTOR);
		}
		
		return ci;
	}

}
