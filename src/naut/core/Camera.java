package naut.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

	private Vector3f position, rotation;
	private static final float MOVE_SPEED = 0.05f, ROTATION_SPEED = 0.1f;
	
	public Camera() {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public Matrix4f getViewMatrix() {
		var mat = new Matrix4f();
		mat.identity();
		mat.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
		.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
		.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
		mat.translate(-position.x, -position.y, -position.z);
		return mat;
	}
	
	public void move(float x, float y, float z) {
		position.x += x * MOVE_SPEED;
		position.y += y * MOVE_SPEED;
		position.z += z * MOVE_SPEED;
	}
	
	// TODO: rotate

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
	
	
}
