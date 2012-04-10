package no.ntnu.eit.controllers;

import no.ntnu.eit.mainTypes.CarInstructions;
import no.ntnu.eit.mainTypes.CarStateData;
import no.ntnu.eit.mainTypes.Controller;

public class LeadTestController extends Controller {

	
	private static final float THROTTLE_FACTOR = 1.0f;
	@SuppressWarnings("unused")
	private static final float BRAKE_FACTOR = 0.5f;
	int steps = 0;
	@Override
	public CarInstructions step(CarStateData csd) {
		CarInstructions ci = new CarInstructions();
		if (steps%1500 < 60){
			
		}
		else if (steps >= 360 && steps < 720){
			ci.setThrottle(THROTTLE_FACTOR);
		}
		else if (steps%1500 >= 360 && steps%1500 < 960) {
			
		}
//		else if (steps%1500 >= 960 && steps%1500 < 1260) {
//			ci.setBrake(BRAKE_FACTOR);
//		}
		steps++;
		return ci;
	}

}
