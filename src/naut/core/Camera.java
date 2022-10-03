package naut.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

	private Vector3f position, rotation;
	private static final float MOVE_SPEED = 1f, ROTATION_SPEED = 0.1f;
	
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
		if (x != 0) {
			position.x += Math.sin(Math.toRadians(rotation.y - 90)) * -x * MOVE_SPEED;
			position.z += Math.cos(Math.toRadians(rotation.y - 90)) * x * MOVE_SPEED;
		}
		if (z != 0) {
			position.x += Math.sin(Math.toRadians(rotation.y)) * -z * MOVE_SPEED;
			position.z += Math.cos(Math.toRadians(rotation.y)) * z * MOVE_SPEED;
		}
		position.y += y * MOVE_SPEED;
	}
	
	public void rotate(float x, float y, float z) {
		rotation.x += x * ROTATION_SPEED;
		rotation.y += y * ROTATION_SPEED;
		rotation.z += z * ROTATION_SPEED;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
	
	
}
