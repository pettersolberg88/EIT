package no.ntnu.eit.mainTypes;


public abstract class Controller {
	
	public Controller() {
		
	}

	/**
	 * The controller gets tick'ed for every tick and returns commands to the car depending on current and previous input
	 * @param csd	The car state data
	 * @return	The car instructions
	 */
	
	protected float getPreferredDistance(CarStateData csd){
		
		return (float) (Math.pow(Math.abs(csd.getSpeed())/2,1) + 2);
//		return 2;
//		return 0;
		
	}
	public abstract CarInstructions step(CarStateData csd);
}
