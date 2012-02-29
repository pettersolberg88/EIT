package no.ntnu.eit;

import no.ntnu.eit.mainTypes.CarInstructions;
import no.ntnu.eit.mainTypes.CarStateData;
import no.ntnu.eit.mainTypes.Controller;

public class DummyController extends Controller{

	@Override
	public CarInstructions step(CarStateData csd) {
		CarInstructions ci = new CarInstructions();
		
		if (csd.getDistanceToObjectInFront() < 1) {
			ci.setBrake(1.0f);
		}
		else if (csd.getDistanceToObjectInFront() < 5 && csd.getSpeed() > 0.5) {
			ci.setBrake(1.0f);
		}
		else if (csd.getDistanceToObjectInFront() < 15 && csd.getSpeed() > 2) {
			ci.setBrake(0.9f);
		}
		else if (csd.getDistanceToObjectInFront() < 20 && csd.getSpeed() > 4) {
			ci.setBrake(0.7f);
		}
		else if (csd.getDistanceToObjectInFront() < 25 && csd.getSpeed() > 5) {
			ci.setBrake(0.7f);
		}
		else if (csd.getDistanceToObjectInFront() < 70 && csd.getSpeed() > 10) {
			ci.setBrake(0.7f);
		}
		else if (csd.getDistanceToObjectInFront() < 70 && csd.getSpeed() > 5) {
			ci.setBrake(0.2f);
		}
		else {
			ci.setBrake(0.0f);
		}
		if(csd.getDistanceToObjectInFront() <= 25 && csd.getSpeed() < 2f){
			ci.setThrottle(0.90f);			
		}
		else if(csd.getDistanceToObjectInFront() <= 50 && csd.getSpeed() < 2f){
			ci.setThrottle(0.80f);			
		}
		else if(csd.getDistanceToObjectInFront() > 50){
			ci.setThrottle(1.0f);			
		}
		else{
			ci.setThrottle(0.0f);
		}
		System.out.println(csd.getSpeed());
		return ci;
	}

}
