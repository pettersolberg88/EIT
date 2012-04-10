package no.ntnu.eit.controllers;

import no.ntnu.eit.mainTypes.CarInstructions;
import no.ntnu.eit.mainTypes.CarStateData;
import no.ntnu.eit.mainTypes.Controller;

public class IController extends Controller {

	private final static float TICK_TIME = 1.0f/60;
	private static final float ACCELERATOR_FACTOR = 1.0f/1000;
	private static final float BRAKE_FACTOR = 1.0f/10;
	float area;
	
	boolean zeroSpeed = false;
	public IController() {
		area = 0.0f;
	}
	@Override
	public CarInstructions step(CarStateData csd) {
		
		CarInstructions ci = new CarInstructions();
		if(csd.getSpeed() == 0){
			if(zeroSpeed == false){
				area = 0.0f;				
			}
			zeroSpeed = true;
		}
		else{
			zeroSpeed = false;
		}
		area += distanceDifference(csd)*TICK_TIME;
		
		if(area < 0){
			ci.setThrottle(-area*ACCELERATOR_FACTOR);
		}
		if (area > 0) {
			ci.setBrake(area*BRAKE_FACTOR);
		}
		
		return ci;
	}
	
	private float distanceDifference(CarStateData csd){
		return getPreferredDistance(csd) - csd.getDistanceToObjectInFront();
	}

}
