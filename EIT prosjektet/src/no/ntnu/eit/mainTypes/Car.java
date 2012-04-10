package no.ntnu.eit.mainTypes;


import no.ntnu.eit.controllers.SineController;

import org.jbox2d.collision.DistanceInput;
import org.jbox2d.collision.DistanceOutput;
import org.jbox2d.collision.Distance.SimplexCache;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.testbed.framework.TestbedTest;

public abstract class Car {
	
	//// ASSUMING STATIC TICK TIME FOR HP
	private static final float THROTTLE_COST = 1.0f;
	private static final float BRAKE_COST = 1f;
	private static final float TIME_COST = 1f;
	private static final float CRASH_COST = 100000;
	
	
	public Body body;
	private CarStateData csd;
	private TestbedTest tbt;
	private PolygonShape shape;
	private Car carInFront;
	
	protected Controller controller;
	private FixtureDef fixDef;
	
	private float cost;
	private boolean wasCrashing = false;
	
	/**
	 * Creates a new car
	 * @param x Xpos
	 * @param y	Ypos
	 * @param tbt	TestBedTest
	 */
	public Car(float x, float y,Controller controller, TestbedTest tbt) {
		this.controller = controller;
		this.tbt = tbt;
//		PolygonShape shape = new PolygonShape();
		shape = new PolygonShape();
		shape.setAsBox(getWidth()/2, getHeight()/2);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(x, y);
		body = tbt.getWorld().createBody(bd);
		fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.restitution = getRestitution();
		fixDef.density = getMass() / getHeight() / getWidth();
//		System.out.println(fixDef.friction);
		body.createFixture(fixDef);

		csd = new CarStateData();
		brake(0);
//		body.createFixture(shape, 2.0f);
	}
	
	public void step(int updateFrequency){
		// gererate current speed
		csd.setSpeed(body.getLinearVelocity().x);
		csd.setUpdateFrequency(updateFrequency);
		csd.setDistanceToObjectInFront(distanceHorisontalBetween(carInFront));
		
		CarInstructions ci = controller.step(csd);
		if(ci.error){
//			System.err.println("ERROR");
		}
		
		accelerate(ci.getThrottle());
		brake(ci.getBrake());
		
		// CALCULATE COST
		
		cost += ci.getThrottle()* THROTTLE_COST;
		cost += ci.getBrake()* BRAKE_COST;
		cost += TIME_COST;
		if(isCrashing()){
			if (!wasCrashing) {
				if(carInFront.controller instanceof SineController){
					cost += CRASH_COST*100;
				}
				cost += CRASH_COST;
				wasCrashing = true;
			}
		}
		else{
			wasCrashing = false;
		}
		
	}
	
	private void accelerate(float a){
		Vec2 f = body.getWorldVector(new Vec2(getMaximunAccelerationForce()* a, 0.0f));
		Vec2 p = body.getWorldPoint(body.getLocalCenter());
		body.applyForce(f, p);
	}
	
	
	private void brake(float b){
		float oldFriction = fixDef.friction;
		float newFriction = getMaximunBrakeForce() * b;
		if (oldFriction != newFriction) {
			fixDef.friction = newFriction;
//		fixDef.restitution = 1.1f;
			body.destroyFixture(body.getFixtureList());
			body.createFixture(fixDef);			
		}
	}
	/**
	 * Distance between this car and the given one
	 * @param car The given one
	 * @return	Distance in meters
	 */
	public float distanceHorisontalBetween(Car car){
		if(car == null){
			return 0;
		}
		
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
	
	public void setCarInFront(Car car){
		this.carInFront = car;
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
	/**
	 * Returns the maximum acceleration force the car can accelerate with. The force is given in Newton.
	 * @return
	 */
	protected abstract float getMaximunAccelerationForce();
	
	/**
	 * Returns the maximum brake force the car can brake with. The force is given in Newton.
	 * @return
	 */
	protected abstract float getMaximunBrakeForce();
	
	private boolean isCrashing(){
		if(distanceHorisontalBetween(carInFront) < 0.01f && carInFront != null){
			return true;
		}
		return false;
	}
	
	public float getCost(){
		return cost;
	}
	

}
