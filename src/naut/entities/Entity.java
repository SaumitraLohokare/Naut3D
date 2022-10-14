package naut.entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import naut.core.Camera;
import naut.lighting.DirectionalLight;
import naut.materials.Material;
import naut.models.Model;
import naut.shaders.ShaderLoader;
import naut.textures.Texture;

public class Entity {
	
	private Vector3f position, rotation;
	private float scale;
	private Model model;
	private Material material;
	private String shaderType;
	
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
		if (this.material.hasTexture()) {
			shaderType = "textured";
		} else {
			shaderType = "static";
		}
//		shaderType = "textured";
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
	
	public void render(DirectionalLight light, Camera c) {
		var shader = ShaderLoader.shaders.get(shaderType);
		if (shader == null) 
			throw new IllegalStateException("Invalid shader " + shaderType);
		
		shader.start(getTransformationMatrix(), material, light, c);
		shader.renderEntity(this);
		shader.stop();
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

	public void setPosition(Vector3f direction) {
		position = direction;
		
	}
}
