package naut.scenes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import naut.core.Camera;
import naut.core.Input;
import naut.core.Loader;
import naut.entities.Entity;
import naut.lighting.DirectionalLight;
import naut.materials.Material;
import naut.models.Model;

public class TestScene extends Scene {

	List<Entity> entities;
	Model m, m2;
	
	private float lightAngle;
	private DirectionalLight directionalLight;

	public TestScene() {
		super(new Camera(new Vector3f(0, 0, 5f), new Vector3f()));
		
		m = Loader.loadOBJModel("src/models/cube.obj");
		m2 = Loader.loadOBJModel("src/models/bunny.obj");
		// var texture = Texture.loadTexture("src/textures/test.png");
		var material = new Material(new Vector4f(0.3f, 0.1f, 0.1f, 1f), 32f);
		
		lightAngle = -90;
		var lightIntensity = 1f;
		var lightPosition = new Vector3f(0, 1, 2);
		var lightColor = new Vector3f(0, 1, 0);
		directionalLight = new DirectionalLight(lightColor, lightPosition, lightIntensity);
		
		entities = new ArrayList<>();
		var e = new Entity(new Vector3f(0, 0, 0), new Vector3f(), 1);
		e.setModel(m2);
		// TODO cache textures
		e.setMaterial(material);
		entities.add(e);
		
		var lightEntity = new Entity(directionalLight.getDirection(), new Vector3f(), 0.2f);
		lightEntity.setModel(m);
		lightEntity.setMaterial(new Material(new Vector4f(1, 1, 1, 1), 2));
		entities.add(lightEntity);
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 10; j++) {
//				var e = new Entity(new Vector3f(i, 0, -j), new Vector3f(), 1);
//				e.setModel(m);
//				// TODO cache textures
//				e.setMaterial(material);
//				entities.add(e);
//			}
//		}
	}

	float direction = 0.01f;
	
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
		
		if (directionalLight.getDirection().x >= 10) {
			direction = -0.01f;
		} else if (directionalLight.getDirection().x <= -10) {
			direction = 0.01f;
		}
		directionalLight.getDirection().x += direction;
		
		entities.get(1).setPosition(directionalLight.getDirection());
		
		
//		lightAngle += 0.1f;
//		if (lightAngle > 90) {
//			directionalLight.setIntensity(0f);
//			if (lightAngle >= 360)
//				lightAngle = -90;
//		} else if (lightAngle <= -80 && lightAngle >= 80) {
//			var factor = 1 - (Math.abs(lightAngle) - 80) / 10f;
//			directionalLight.setIntensity(factor);
//			directionalLight.getColor().y = Math.max(factor, 0.9f);
//			directionalLight.getColor().z = Math.max(factor, 0.5f);
//		} else {
//			directionalLight.setIntensity(1);
//			directionalLight.getColor().x = 1;
//			directionalLight.getColor().y = 1;
//			directionalLight.getColor().z = 1;
//		}
//		var angleRad = Math.toRadians(lightAngle);
//		directionalLight.getDirection().x = (float) Math.sin(angleRad);
//		directionalLight.getDirection().y = (float) Math.cos(angleRad);
	}

	@Override
	public void render() {
		for (var e : entities) {			
			e.render(directionalLight, camera);
		}
	}

	@Override
	public void cleanUp() {
		for (var e : entities) {			
			e.cleanUp();
		}
	}

}
