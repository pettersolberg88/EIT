package no.ntnu.eit;

import org.jbox2d.testbed.framework.TestbedTest;

public class SportsCar extends Car {

	public SportsCar(float x, float y, TestbedTest tbt) {
		super(x, y, tbt);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected float getHeight() {
		// TODO Auto-generated method stub
		return 2.0f;
	}

	@Override
	protected float getWidth() {
		// TODO Auto-generated method stub
		return 4.0f;
	}

	@Override
	protected float getMass() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	protected float getRestitution() {
		// TODO Auto-generated method stub
		return (float) ((float) Math.random()*1);
	}

}
