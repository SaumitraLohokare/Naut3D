package naut.shaders;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.joml.Matrix4f;

import main.Launcher;
import naut.core.Camera;
import naut.core.Constants;
import naut.entities.Entity;
import naut.lighting.DirectionalLight;
import naut.materials.Material;
import naut.scenes.Scene;

public class TexturedShader extends Shader {
	
	private static final String VS = "src/shaderfiles/textured.vs", FS = "src/shaderfiles/textured.fs";
	
	public TexturedShader() {
		super(VS, FS);

		createUniform("transformationMatrix");
		createUniform("viewMatrix");
		createUniform("projectionMatrix");
		createUniform("textureSampler");
		createUniform("ambientLight");
		createMaterialUniform("material");
		createUniform("specularPower");
		createDirectionalLightUniform("directionalLight");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	@Override
	public void start(Matrix4f t, Material material, DirectionalLight light, Camera c) {
		super.start(t, material, light, c);
		setUniform("textureSampler", 0);
		
		// setup uniforms
		setUniform("projectionMatrix", Launcher.getWindow().getProjectionMatrix());
		setUniform("viewMatrix", Scene.currentScene().getCameraViewMatrix());
		setUniform("transformationMatrix", t);
		
		setUniform("ambientLight", Constants.AMBIENT_LIGHT);
		setUniform("material", material);
		
		setUniform("specularPower", Constants.SPECULAR_POWER);
		setUniform("directionalLight", light);
	}

	public void renderEntity(Entity e) {
		e.model().bindVAO();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		if (e.getMaterial().hasTexture()) {			
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, e.getMaterial().getTexture().id());
		}
		glDrawElements(GL_TRIANGLES, e.model().getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glBindVertexArray(0);
		
	}

}
