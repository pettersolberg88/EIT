package no.ntnu.eit;

import java.util.ArrayList;
import java.util.Iterator;

import no.ntnu.eit.mainTypes.Car;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class PettersRaceTrack extends TestbedTest{

	
	ArrayList<Car> cars;
	
	
	@Override
	public String getTestName() {
		return "Petter's Racetrack";
	}

	@Override
	public void initTest(boolean arg0) {
		
		cars = new ArrayList<Car>();
		
		// ADD GROUND
		{
			BodyDef bd = new BodyDef();
			Body ground = getWorld().createBody(bd);

			PolygonShape shape = new PolygonShape();
			shape.setAsEdge(new Vec2(-400.0f, 0.0f), new Vec2(400.0f, 0.0f));
			ground.createFixture(shape, 0.0f);
			
			
		}
		
		// ADD CARS
		cars.add(new OpelCorsa(60.0f, 3.0f, new DummyController(), this));
		for (int i =1; i < 20; i++) {
			cars.add(new OpelCorsa(25.0f - i * 10 + (int)( Math.random()* 5), 3, new DummyController(), this));
			if(i > 0){
				cars.get(i).setCarInFront(cars.get(i-1));				
			}
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
