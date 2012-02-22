package no.ntnu.eit;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestbedTest;

public abstract class Car {
	
	Body body;
	
	
	public Car(float x, float y, TestbedTest tbt) {
		
		PolygonShape shape = new PolygonShape();
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
//		body.createFixture(shape, 2.0f);
	}
	
	private Body getBody() {
		return body;

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
