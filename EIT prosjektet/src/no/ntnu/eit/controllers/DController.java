package no.ntnu.eit.controllers;

import no.ntnu.eit.mainTypes.CarInstructions;
import no.ntnu.eit.mainTypes.CarStateData;
import no.ntnu.eit.mainTypes.Controller;

public class DController extends Controller{

	private final static float TICK_TIME = 1.0f/60;

	private static final float ACCELERATOR_FACTOR = 1.0f/1;
	private static final float BRAKE_FACTOR = 1.0f/10;
	
	float lastDifference = 0.0f;
	@Override
	public CarInstructions step(CarStateData csd) {
		
		CarInstructions ci = new CarInstructions();
		float derivative = (lastDifference - distanceDifference(csd))/TICK_TIME;
		if(derivative > 0){
			ci.setThrottle(derivative*ACCELERATOR_FACTOR);
		}
		if(derivative < 0){
			ci.setBrake(-derivative*BRAKE_FACTOR);
		}
		
		lastDifference = distanceDifference(csd);
		return ci;
	}
	
	
	private float distanceDifference(CarStateData csd){
		return getPreferredDistance(csd) - csd.getDistanceToObjectInFront();
	}

}
