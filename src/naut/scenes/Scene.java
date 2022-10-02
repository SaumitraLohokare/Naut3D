package naut.scenes;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import naut.core.Camera;

public abstract class Scene {
	
	private Camera camera;
	public abstract void update(double dt);
	public abstract void render();
	public Scene(Camera camera) {
		this.camera = camera;
	}
	
	// ----- STATIC -------------
	
	private static Map<String, Scene> scenes;
	private static Scene currentScene = null;
	
	public static void init() {
		scenes = new HashMap<>();
	}
	
	public static void addScene(String name, Scene scene) {
		scenes.put(name, scene);
	}
	
	public static void changeScene(String s) {
		if (!scenes.containsKey(s))
			throw new IllegalStateException("Scene " + s + " does not exist.");
		
		currentScene = scenes.get(s);
	}
	
	public static Scene currentScene() {
		return currentScene;
	}
	
	public Matrix4f getCameraViewMatrix() {
		return camera.getViewMatrix();
	}
	
}