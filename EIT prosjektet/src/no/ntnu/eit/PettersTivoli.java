package no.ntnu.eit;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
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
		{
			BodyDef bd = new BodyDef();
			Body ground = getWorld().createBody(bd);

			PolygonShape shape = new PolygonShape();
			shape.setAsEdge(new Vec2(-40.0f, 0.0f),new Vec2(40.0f, 0.0f));
			ground.createFixture(shape, 0.0f);
		}

		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(13.0f, 0.25f);

			BodyDef bd = new BodyDef();
			bd.position.set(-4.0f, 22.0f);
			bd.angle = -0.25f;

			Body ground = getWorld().createBody(bd);
			ground.createFixture(shape, 0.0f);
		}

		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(0.25f, 1.0f);

			BodyDef bd = new BodyDef();
			bd.position.set(11.5f, 19.0f);

			Body ground = getWorld().createBody(bd);
			ground.createFixture(shape, 0.0f);
		}

		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(13.0f, 0.25f);

			BodyDef bd = new BodyDef();
			bd.position.set(4.0f, 14.0f);
			bd.angle = 0.25f;

			Body ground = getWorld().createBody(bd);
			ground.createFixture(shape, 0.0f);
		}

		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(0.25f, 1.0f);

			BodyDef bd = new BodyDef();
			bd.position.set(-10.5f, 11.0f);

			Body ground = getWorld().createBody(bd);
			ground.createFixture(shape, 0.0f);
		}

		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(13.0f, 0.25f);

			BodyDef bd = new BodyDef();
			bd.position.set(-4.0f, 6.0f);
			bd.angle = -0.35f;

			Body ground = getWorld().createBody(bd);
			ground.createFixture(shape, 0.0f);
		}

		{
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(0.5f, 0.5f);

			FixtureDef fd = new FixtureDef();
			fd.shape = shape;
			fd.density = 25.0f;

			float friction[] = {0.5f, 0.45f, 0.35f, 0.1f, 0.0f};

			for (int i = 0; i < 40; ++i)
			{
				BodyDef bd = new BodyDef();
				bd.type = BodyType.DYNAMIC;
				bd.position.set(-15.0f + 4.0f * (i % 5), 38.0f + (i / 5)*5);
				Body body = getWorld().createBody(bd);

				fd.friction = friction[i % 5];
				body.createFixture(fd);
			}
		}
		
		/**
		 * FRA CRANCTEST
		 */
		
		Body ground = null;
		{
			BodyDef bd = new BodyDef();
			ground = getWorld().createBody(bd);

			PolygonShape shape = new PolygonShape();
			shape.setAsEdge(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
			ground.createFixture(shape, 0.0f);
		}

		{
			Body prevBody = ground;

			// Define crank.
			{
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(0.5f, 2.0f);

				BodyDef bd = new BodyDef();
				bd.type = BodyType.DYNAMIC;
				bd.position.set(10.0f, 8.0f);
				Body body = getWorld().createBody(bd);
				body.createFixture(shape, 2.0f);

				RevoluteJointDef rjd = new RevoluteJointDef();
				rjd.initialize(prevBody, body, new Vec2(10.0f, 6.0f));
				rjd.motorSpeed = 0.70f * MathUtils.PI;
				rjd.maxMotorTorque = 50000.0f;
				rjd.enableMotor = true;
				m_joint1 = (RevoluteJoint)getWorld().createJoint(rjd);

				prevBody = body;
			}
			
			// Define follower.
			{
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(0.5f, 4.0f);

				BodyDef bd = new BodyDef();
				bd.type = BodyType.DYNAMIC;
				bd.position.set(10.0f, 13.0f);
				bd.angle = 1 * MathUtils.PI;
				Body body = getWorld().createBody(bd);
				body.createFixture(shape, 2.0f);

				RevoluteJointDef rjd = new RevoluteJointDef();
				rjd.initialize(prevBody, body, new Vec2(10.0f, 9.0f));
				rjd.enableMotor = false;
				getWorld().createJoint(rjd);

				prevBody = body;
			}
			/*
			// Define piston
			{
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(1.5f, 1.5f);

				BodyDef bd = new BodyDef();
				bd.type = BodyType.DYNAMIC;
				bd.position.set(10.0f, 17.0f);
				Body body = getWorld().createBody(bd);
				body.createFixture(shape, 2.0f);

				RevoluteJointDef rjd = new RevoluteJointDef();
				rjd.initialize(prevBody, body, new Vec2(0.0f, 17.0f));
				getWorld().createJoint(rjd);

				PrismaticJointDef pjd = new PrismaticJointDef();
				pjd.initialize(ground, body, new Vec2(0.0f, 17.0f), new Vec2(0.0f, 1.0f));

				pjd.maxMotorForce = 1000.0f;
				pjd.enableMotor = true;

				m_joint2 = (PrismaticJoint)getWorld().createJoint(pjd);
			}

			// Create a payload
			{
				PolygonShape shape = new PolygonShape();
				shape.setAsBox(1.5f, 1.5f);

				BodyDef bd = new BodyDef();
				bd.type = BodyType.DYNAMIC;
				bd.position.set(10.0f, 23.0f);
				Body body = getWorld().createBody(bd);
				kk = body;
				body.createFixture(shape, 2.0f);
			}
			*/
		}
		
	}

}
