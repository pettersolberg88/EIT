package no.ntnu.eit.controllers;

import no.ntnu.eit.mainTypes.CarInstructions;
import no.ntnu.eit.mainTypes.CarStateData;
import no.ntnu.eit.mainTypes.Controller;

public class SineController extends Controller {

	
	int ticks = 0;
	@Override
	public CarInstructions step(CarStateData csd) {
		CarInstructions ci = new CarInstructions();
		float curve = 0.5f*(float)Math.sin(ticks*2*3.14/csd.getUpdateFrequency()/20);
		if(curve >0){
			ci.setThrottle(curve);
		}
		if(curve < 0){
			ci.setBrake(-curve/2);
		}
		ticks++;
		// TODO Auto-generated method stub
		return ci;
	}

}
