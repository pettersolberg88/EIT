package no.ntnu.eit.mainTypes;

public class CarStateData {
	
	private int updateFrequency;
	private float speed;
	private float distanceToObjectInFront;
	public int getUpdateFrequency() {
		return updateFrequency;
	}
	public void setUpdateFrequency(int updateFrequency) {
		this.updateFrequency = updateFrequency;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getDistanceToObjectInFront() {
		return distanceToObjectInFront;
	}
	public void setDistanceToObjectInFront(float distanceToObjectInFront) {
		this.distanceToObjectInFront = distanceToObjectInFront;
	}	
	
	
}
