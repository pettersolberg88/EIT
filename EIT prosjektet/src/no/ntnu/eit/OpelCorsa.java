package no.ntnu.eit;

import org.jbox2d.testbed.framework.TestbedTest;

import no.ntnu.eit.mainTypes.Car;
import no.ntnu.eit.mainTypes.Controller;

public class OpelCorsa extends Car{

	public OpelCorsa(float x, float y, Controller controller, TestbedTest tbt) {
		super(x, y, controller, tbt);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected float getHeight() {
		// TODO Auto-generated method stub
		return 1.5f;
	}

	@Override
	protected float getWidth() {
		// TODO Auto-generated method stub
		return 4.0f;
	}

	@Override
	protected float getMass() {
		// TODO Auto-generated method stub
		return 1200;
	}

	@Override
	protected float getRestitution() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected float getMaximunAccelerationForce() {
		// TODO Auto-generated method stub
		return 2800;
	}

	@Override
	protected float getMaximunBrakeForce() {
		// TODO Auto-generated method stub
		return 9500 / getMass();
	}

}
