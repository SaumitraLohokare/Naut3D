package naut.materials;

import org.joml.Vector4f;

import naut.textures.Texture;

public class Material {
	
	private static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
	
	private Vector4f ambientColor, diffuseColor, specularColor;
	private float reflectance;
	private Texture texture;
	
	public Material() {
		ambientColor = DEFAULT_COLOR;
		diffuseColor = DEFAULT_COLOR;
		specularColor = DEFAULT_COLOR;
		
		reflectance = 0f;
		
		texture = null;
	}
	
	public Material(Vector4f color, float reflectance) {
		ambientColor = color;
		diffuseColor = color;
		specularColor = color;
		
		this.reflectance = reflectance;
		
		texture = null;
	}
	
	public Material(Vector4f color, float reflectance, Texture texture) {
		ambientColor = color;
		diffuseColor = color;
		specularColor = color;
		
		this.reflectance = reflectance;
		
		this.texture = texture;
	}
	
	public Material(Texture texture) {
		ambientColor = DEFAULT_COLOR;
		diffuseColor = DEFAULT_COLOR;
		specularColor = DEFAULT_COLOR;
		
		reflectance = 0f;
		
		this.texture = texture;
	}
	
	public Material(Texture texture, float reflectance) {
		ambientColor = DEFAULT_COLOR;
		diffuseColor = DEFAULT_COLOR;
		specularColor = DEFAULT_COLOR;
		
		this.reflectance = reflectance;
		
		this.texture = texture;
	}
	
	public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, float reflectance,
			Texture texture) {
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseColor;
		this.specularColor = specularColor;
		this.reflectance = reflectance;
		this.texture = texture;
	}

	public Vector4f getAmbientColor() {
		return ambientColor;
	}

	public Vector4f getDiffuseColor() {
		return diffuseColor;
	}

	public Vector4f getSpecularColor() {
		return specularColor;
	}

	public float getReflectance() {
		return reflectance;
	}

	public Texture getTexture() {
		return texture;
	}
	
	public boolean hasTexture() {
		return texture != null;
	}
}
