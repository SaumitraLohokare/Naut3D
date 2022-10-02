package naut.scenes;

import org.joml.Vector3f;

import naut.core.Camera;
import naut.entities.Entity;
import naut.models.Model;
import naut.shaders.StaticShader;

public class TestScene extends Scene {

	Entity e;
	Model m;
	StaticShader shader;
	
	public TestScene() {
		super(new Camera());

		float[] vertices = new float[] {
			-0.25f, 0.25f, -1f,
			0.25f, 0.25f, -1f,
			-0.25f, -0.25f, -1f,
			0.25f, -0.25f, -1f,
		};
		
		int[] indices = new int[] {
			0, 1, 2,
			1, 2, 3
		};
		
		m = new Model(vertices, indices);
		e = new Entity(new Vector3f(), new Vector3f(), 1, m);
		shader = new StaticShader();
	}

	@Override
	public void update(double dt) {

		
	}

	@Override
	public void render() {
		System.out.println("Called");
		shader.start(e.getTransformationMatrix());
		e.model().render();
		shader.stop();
	}

}
