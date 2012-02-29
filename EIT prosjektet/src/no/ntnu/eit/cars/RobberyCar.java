package no.ntnu.eit.cars;

import no.ntnu.eit.mainTypes.Car;
import no.ntnu.eit.mainTypes.Controller;

import org.jbox2d.testbed.framework.TestbedTest;

public class RobberyCar extends Car{

	public RobberyCar(float x, float y,Controller controller, TestbedTest tbt) {
		super(x, y, controller, tbt);
	}

	@Override
	protected float getHeight() {
		return 5.0f;
	}

	@Override
	protected float getWidth() {
		return 3.0f;
	}

	@Override
	protected float getMass() {
		
		return 100.0f;
	}

	@Override
	protected float getRestitution() {
		return 0;
	}

	@Override
	protected float getMaximunAccelerationForce() {
		// TODO Auto-generated method stub
		return 1000;
	}

	@Override
	protected float getMaximunBrakeForce() {
		// TODO Auto-generated method stub
		return 0;
	}

}
