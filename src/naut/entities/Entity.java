package naut.entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import naut.models.Model;

public class Entity {
	
	private Vector3f position, rotation;
	private float scale;
	private Model model;
	
	public Entity (Vector3f position, Vector3f rotation, float scale, Model model) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
	
	public Matrix4f getTransformationMatrix() {
		var matrix = new Matrix4f();
		matrix.identity()
		.translate(position)
		.rotateX((float) Math.toRadians(rotation.x))
		.rotateY((float) Math.toRadians(rotation.y))
		.rotateZ((float) Math.toRadians(rotation.z))
		.scale(scale);
		return matrix;
	}
	
	public Model model() {
		return model;
	}

	public void cleanUp() {
		model.cleanUp();
	}
}
