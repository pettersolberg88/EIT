package no.ntnu.eit;

import no.ntnu.eit.cars.OpelCorsa;
import no.ntnu.eit.controllers.DummyController;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Mat22;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.testbed.framework.TestbedTest;

public class PettersTivoli extends TestbedTest{

	private RevoluteJoint m_joint1;
	private PrismaticJoint m_joint2;
	private Body kk;
	Body m_body;
	
	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return "Petter's Tivoli";
	}
	
	@Override
	public void initTest(boolean argDeserialized) {
	  if(argDeserialized){
	    return;
	  }

	  for (int i = 0; i < 10; i++) {
		new OpelCorsa(4.0f * i - 20, 20.0f ,new DummyController(), this);
	}
		{
			BodyDef bd = new BodyDef();
			Body ground = getWorld().createBody(bd);

			PolygonShape shape = new PolygonShape();
			shape.setAsEdge(new Vec2(-40.0f, 0.0f),new Vec2(40.0f, 0.0f));
			ground.createFixture(shape, 0.0f);
		}

		
	}

}
