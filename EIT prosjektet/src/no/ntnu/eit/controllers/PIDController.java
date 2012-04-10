package no.ntnu.eit.controllers;

import no.ntnu.eit.mainTypes.CarInstructions;
import no.ntnu.eit.mainTypes.CarStateData;
import no.ntnu.eit.mainTypes.Controller;

public class PIDController extends Controller{

	private float THROTTLE_FACTOR = 1.0f;
	private float BRAKE_FACTOR = 1.0f;
	private float TOTAL_FACTOR = 0.10f;
	
//	private float P_CONSTANT = 1.0f;
//	private float I_CONSTANT = 0.1f;
//	private float D_CONSTANT = 1.0f;
//	
	private float P_CONSTANT = 1.0f;
	private float I_CONSTANT = 0.1f;
	private float D_CONSTANT = 5.0f;
	
	
	private float integral = 0.0f;
	boolean zeroSpeed = false;
	
	float lastError = 0.0f;
	
	
	public PIDController() {
		// TODO Auto-generated constructor stub
	}
	public PIDController(float p, float i, float d){
		P_CONSTANT = p;
		I_CONSTANT = i;
		D_CONSTANT = d;
	}
	public PIDController(float factor){
//		D_CONSTANT = factor;
	}
	
	@Override
	public CarInstructions step(CarStateData csd) {
		// TODO: IMPLEMENT IT
		
		float error = csd.getDistanceToObjectInFront() - getPreferredDistance(csd) ;
		// PROPOTIONAL
		float prop = P_CONSTANT * error;
		
		//INTEGRAL
		if(csd.getSpeed() == 0){
			if(zeroSpeed == false){
				integral = 0.0f;				
			}
			zeroSpeed = true;
		}
		else{
			zeroSpeed = false;
		}
		integral += error/csd.getUpdateFrequency();
		
		float integr = integral * I_CONSTANT;
		
		// DERIVATIVE
		
		float delta = (error - lastError)* csd.getUpdateFrequency();
		lastError = error;
		float derivative = delta * D_CONSTANT;
		
		
		// TOTAL
		
		CarInstructions ci = new CarInstructions();
		float pid = prop + integr + derivative;
		
		if(pid > 0){
			ci.setThrottle(pid*THROTTLE_FACTOR*TOTAL_FACTOR);
		}
		else{
			ci.setBrake(-pid*BRAKE_FACTOR*TOTAL_FACTOR);
		}
		
		return ci;
	}

}
