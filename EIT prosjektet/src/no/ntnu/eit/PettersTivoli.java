package no.ntnu.eit;

import no.ntnu.eit.cars.OpelCorsa;
import no.ntnu.eit.controllers.DummyController;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.testbed.framework.TestbedTest;

public class PettersTivoli extends TestbedTest{

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
