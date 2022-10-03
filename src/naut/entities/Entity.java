package naut.entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import naut.materials.Material;
import naut.models.Model;
import naut.textures.Texture;

public class Entity {
	
	private Vector3f position, rotation;
	private float scale;
	private Model model;
	private Material material;
	
	public Entity (Vector3f position, Vector3f rotation, float scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
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
	
	public Texture getTexture() {
		return material.getTexture();
	}

	public Material getMaterial() {
		return material;
	}
}
