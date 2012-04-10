package no.ntnu.eit;

import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.eit.cars.OpelCorsa;
import no.ntnu.eit.controllers.PIDController;
import no.ntnu.eit.controllers.SineController;
import no.ntnu.eit.dawkins.NaturalSelection;
import no.ntnu.eit.mainTypes.Car;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class PettersRaceTrack extends TestbedTest{

	private static final int NUMBER_OF_CARS = 10;
	private static final int NUMBER_OF_RACES = 20;
	private static final float RACE_LENGTH = 75;
	private static final int RACE_MAXIMUM_TIME = 3*60*60;
	
	ArrayList<Car> cars;
	
	private boolean[] isFinished;
	private float[] raceCosts;
	private int ticks = 0;
	private NaturalSelection ns;
	
	
	@Override
	public String getTestName() {
		return "Petter's Racetrack";
	}

	@Override
	public void initTest(boolean arg0) {
		
		cars = new ArrayList<Car>();
		
		ns = new NaturalSelection();
		
		isFinished = new boolean[NUMBER_OF_RACES];
		raceCosts = new float[NUMBER_OF_RACES];
		
		
		this.setCamera(new Vec2(0, 0), 3f);
		
		// ADD RACES
		for (int r = 0; r < NUMBER_OF_RACES; r++) {
			// ADD GROUND
			{
				BodyDef bd = new BodyDef();
				Body ground = getWorld().createBody(bd);
				
				PolygonShape shape = new PolygonShape();
				shape.setAsEdge(new Vec2(-4000.0f, 5.0f*r), new Vec2(4000.0f, 5.0f*r));
				ground.createFixture(shape, 0.0f);
				
				
			}
			
			// ADD CARS
			cars.add(new OpelCorsa(25.0f, 1.5f/2 + 5*r, new SineController(), this));
			for (int i =1; i < NUMBER_OF_CARS; i++) {
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new DummyController(), this));
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new PController(), this));
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new IController(), this));
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new DController(), this));
				cars.add(new OpelCorsa(25.0f - i * 6  + (int)( Math.random()* 0), 1.5f/2 + 5*r, new PIDController(ns.valueList[r][0],ns.valueList[r][1],ns.valueList[r][2]), this));
				if(i > 0){
					cars.get(i+r*NUMBER_OF_CARS).setCarInFront(cars.get(i+r*NUMBER_OF_CARS-1));				
				}
			}
			
//			new OpelCorsa(-50f - r%5, 1.5f/2 + 5*r, new DummyController(), this);
			
		}
	}
	

	@Override
	public synchronized void step(TestbedSettings tbs) {
		super.step(tbs);
		
		for (Iterator<Car> iterator = cars.iterator(); iterator.hasNext();) {
			Car car = iterator.next();
			car.step(tbs.getSetting("Hz").value);
		}
		
		checkIfFinished();
		ticks++;
		if(ticks > RACE_MAXIMUM_TIME || isAllFinished()){
			// SAVE THE BEST ONE;
			int best = -1;
			float bestValue = Float.MAX_VALUE;
			for (int i = 0; i < isFinished.length; i++) {
				if(isFinished[i]){
					if(bestValue > raceCosts[i]){
						best = i;
						bestValue = raceCosts[i];
					}
				}
			}
			if(best == -1){
				System.err.println("NO ONE FINISHED");
				best = (int) (Math.random()* NUMBER_OF_RACES);
			}
			System.err.println("DEN BESTE BLE NR " + best);
			ns.saveBest(best);
			ticks = 0;
			reset();
		}
		addTextLine("Ticks left: " + (RACE_MAXIMUM_TIME - ticks));
		for (int i = NUMBER_OF_RACES - 1; i >= 0; i--) {
			addTextLine(i+1 + " - " +  raceIsFinished(i) + " " + getRaceCost(i));
		}
		
	}
	
	private boolean raceIsFinished(int raceNumber){
		if(cars.get((raceNumber+1)*NUMBER_OF_CARS - 1).body.getPosition().x > RACE_LENGTH ){
			return true;
		}
		return false;
	}
	private float getRaceCost(int raceNumber){
		
		float rowCost = 0;
		for (int j = 1; j < NUMBER_OF_CARS - 1; j++) {
			rowCost += cars.get(raceNumber*NUMBER_OF_CARS + j).getCost();
		}
		return rowCost;
	}
	
	private void checkIfFinished(){
		for (int i = 0; i < isFinished.length; i++) {
			if(!isFinished[i]){
				if(raceIsFinished(i)){
					isFinished[i] = true;
					raceCosts[i] = getRaceCost(i);
				}
			}
		}
	}
	private boolean isAllFinished(){
		for (int i = 0; i < isFinished.length; i++) {
			if(!isFinished[i]){
				return false;
			}
		}
		return true;
	}
	
	

}
