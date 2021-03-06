package no.ntnu.eit.mainTypes;

public class CarInstructions {
	
	private float throttle;
	private float brake;
	
	public boolean error = false;
	
	public float getThrottle() {
		return throttle;
	}
	public void setThrottle(float throttle) {
		if(throttle > 1){
//			System.err.println("Throttle to big. Got: " + throttle);
			error = true;
			throttle = 1.0f;
		}
		else if(throttle < 0){
//			System.err.println("Throttle to small. Got: " + throttle);
			error = true;
			throttle = 0f;
		}
		this.throttle = throttle;
	}
	public float getBrake() {
		return brake;
	}
	public void setBrake(float brake) {
		if(brake > 1){
//			System.err.println("Brake to big. Got: " + brake);
			error = true;
			brake = 1.0f;
		}
		else if(brake < 0){
//			System.err.println("Brake to small. Got: " + brake);
			error = true;
			brake = 0.0f;
		}
		this.brake = brake;
	}

}
