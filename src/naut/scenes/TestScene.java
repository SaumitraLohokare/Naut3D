package naut.scenes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import naut.core.Camera;
import naut.core.Input;
import naut.entities.Entity;
import naut.materials.Material;
import naut.models.Model;
import naut.shaders.StaticShader;
import naut.shaders.TexturedShader;
import naut.textures.Texture;

public class TestScene extends Scene {

	List<Entity> entities;
	Model m;
	TexturedShader shader;

	public TestScene() {
		super(new Camera(new Vector3f(0, 0, 5f), new Vector3f()));

		var vertices = new float[] { -0.5f, 0.25f, 0.5f, -0.5f, -0.25f, 0.5f, 0.5f, 0.25f, 0.5f, 0.5f, -0.25f, 0.5f, -0.5f,
				0.25f, -0.5f, -0.5f, -0.25f, -0.5f, 0.5f, 0.25f, -0.5f, 0.5f, -0.25f, -0.5f, };
		var indices = new int[] { 0, 1, 2, 2, 1, 3,

				4, 5, 6, 6, 5, 7,

				4, 5, 0, 0, 5, 1,

				2, 3, 6, 6, 3, 7,

				0, 2, 4, 4, 2, 6,

				1, 3, 5, 5, 3, 7, };

		var texCoords = new float[] { 0, 0, 0, 0.5f, 0.5f, 0, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 1f, 1f, 0.5f, 1f, 1f, };
		var normals = new float[indices.length * 3];
		
		m = new Model(vertices, normals, indices, texCoords);
		var texture = Texture.loadTexture("src/textures/test.png");
		var material = new Material(texture, 1);
		
		entities = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				var e = new Entity(new Vector3f(i, 0, -j), new Vector3f(), 1);
				e.setModel(m);
				// TODO cache textures
				e.setMaterial(material);
				entities.add(e);
			}
		}
		
		shader = new TexturedShader();
	}

	@Override
	public void update(double dt) {

		// maybe some better design pattern for camera movement
		float moveAmt = (float) (10f * dt);
		if (Input.getKeyDown(GLFW.GLFW_KEY_W)) {
			camera.move(0, 0, -moveAmt);
		}
		if (Input.getKeyDown(GLFW.GLFW_KEY_A)) {
			camera.move(-moveAmt, 0, 0);
		}
		if (Input.getKeyDown(GLFW.GLFW_KEY_S)) {
			camera.move(0, 0, moveAmt);
		}
		if (Input.getKeyDown(GLFW.GLFW_KEY_D)) {
			camera.move(moveAmt, 0, 0);
		}
		if (Input.getKeyDown(GLFW.GLFW_KEY_SPACE)) {
			camera.move(0, moveAmt, 0);
		}
		if (Input.getKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			camera.move(0, -moveAmt, 0);
		}
		
		if (Input.isRightButtonPressed()) {
			var rotVec = Input.getDisplVec();
			camera.rotate(rotVec.x, rotVec.y, 0);
		}
//		if (Input.getKeyDown(GLFW.GLFW_KEY_Q)) {
//			camera.rotate(0, -1f, 0);
//		}
//		if (Input.getKeyDown(GLFW.GLFW_KEY_E)) {
//			camera.rotate(0, 1f, 0);
//		}
	}

	@Override
	public void render() {
		for (var e : entities) {			
			shader.start(e.getTransformationMatrix(), e.getMaterial());
			shader.renderEntity(e);
			shader.stop();
		}
	}

	@Override
	public void cleanUp() {
		shader.cleanUp();
		for (var e : entities) {			
			e.cleanUp();
		}
	}

}
