package no.ntnu.eit;

import no.ntnu.eit.CarStateData;

import org.jbox2d.collision.DistanceInput;
import org.jbox2d.collision.DistanceOutput;
import org.jbox2d.collision.Distance.SimplexCache;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestbedTest;

public abstract class Car {
	
	Body body;
	CarStateData csd;
	TestbedTest tbt;
	PolygonShape shape;
	
	
	public Car(float x, float y, TestbedTest tbt) {
		
		this.tbt = tbt;
//		PolygonShape shape = new PolygonShape();
		shape = new PolygonShape();
		shape.setAsBox(getWidth()/2, getHeight()/2);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(x, y);
		body = tbt.getWorld().createBody(bd);
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.restitution = getRestitution();
		fixDef.density = getMass() / getHeight() / getWidth();
		System.out.println(fixDef.friction);
		body.createFixture(fixDef);

		csd = new CarStateData();
//		body.createFixture(shape, 2.0f);
	}
	
	public void step(int updateFrequency){
		// gererate current speed
		csd.setSpeed(body.getLinearVelocity().x);
		csd.setUpdateFrequency(updateFrequency);
		csd.setDistanceToObjectInFront(calculateLengthToCarInFront());
	}
	
	public float calculateLengthToCarInFront(){
		//TODO: IMPLEMENT IT
		return 0;
	}
	public float distanceHorisontalBetween(Car car){
		
		DistanceInput input = new DistanceInput();
		SimplexCache cache = new SimplexCache();
		DistanceOutput output = new DistanceOutput();
		
//		input.transformA.set(this.body.m_xf);
//		input.transformB.set(car.body.m_xf);
		input.transformA.set(this.body.getTransform());
		input.transformB.set(car.body.getTransform());
		input.proxyA.set(this.shape);
		input.proxyB.set(car.shape);
		input.useRadii = true;
		cache.count = 0;
		tbt.getWorld().getPool().getDistance().distance(output, cache, input);
		
		return output.distance;
//		return 0;
		
	}
	
	/**
	 * Returns the height of the car. Should be divisible by 2 for best accuracy. Can't be zero
	 * @return Height of car
	 */
	protected abstract float getHeight();
	/**
	 * Returns the height of the car. Should be divisible by 2 for best accuracy. Can't be zero
	 * @return Width of car
	 */
	protected abstract float getWidth();
	/**
	 * Returns the total mass of the car
	 * @return Mass of car
	 */
	protected abstract float getMass();
	/**
	 * Returns the restitution of the car (bounceness of the car). 0 is 0%, 0.5 is 50% and 1.2 is 120%.
	 * @return Restitution of car
	 */
	protected abstract float getRestitution();
	

}
