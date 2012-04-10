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

public class CopyOfPettersRaceTrack extends TestbedTest{

	
	ArrayList<Car> cars;
	
	
	@Override
	public String getTestName() {
		return "Petter's Racetrack";
	}

	@Override
	public void initTest(boolean arg0) {
		
		cars = new ArrayList<Car>();
		
		NaturalSelection ns = new NaturalSelection();
		
		
		this.setCamera(new Vec2(0, 0), 3f);
		
		// ADD RACES
		for (int r = 0; r < 10; r++) {
			// ADD GROUND
			{
				BodyDef bd = new BodyDef();
				Body ground = getWorld().createBody(bd);
				
				PolygonShape shape = new PolygonShape();
				shape.setAsEdge(new Vec2(-4000.0f, 5.0f*r), new Vec2(4000.0f, 5.0f*r));
				ground.createFixture(shape, 0.0f);
				
				
			}
			int numberOfCars = 20;
			// ADD CARS
			cars.add(new OpelCorsa(25.0f, 1.5f/2 + 5*r, new SineController(), this));
			for (int i =1; i < numberOfCars; i++) {
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new DummyController(), this));
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new PController(), this));
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new IController(), this));
//			cars.add(new OpelCorsa(25.0f - i * 10 * i + (int)( Math.random()* 5), 3, new DController(), this));
				cars.add(new OpelCorsa(25.0f - i * 6  + (int)( Math.random()* 0), 1.5f/2 + 5*r, new PIDController(ns.valueList[r][0],ns.valueList[r][1],ns.valueList[r][2]), this));
				if(i > 0){
					cars.get(i+r*numberOfCars).setCarInFront(cars.get(i+r*numberOfCars-1));				
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
		
		
	}
	

}
