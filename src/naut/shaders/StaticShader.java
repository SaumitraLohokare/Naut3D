package naut.shaders;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
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

public class StaticShader extends Shader {

	private static final String VS = "src/shaderfiles/static.vs";
	private static final String FS = "src/shaderfiles/static.fs";
	
	public StaticShader() {
		super(VS, FS);
		
		createUniform("transformationMatrix");
		createUniform("viewMatrix");
		createUniform("projectionMatrix");
		createUniform("ambientLight");
		createUniform("lightDirection");
		createUniform("cameraPosition");
		createUniform("specularPower");
		createMaterialUniform("material");
	}
	
	@Override
	public void start(Matrix4f t, Material material, DirectionalLight light, Camera c) {
		super.start(t, material, light, c);
		
		// setup uniforms
		setUniform("projectionMatrix", Launcher.getWindow().getProjectionMatrix());
		setUniform("viewMatrix", Scene.currentScene().getCameraViewMatrix());
		setUniform("transformationMatrix", t);
		
		setUniform("ambientLight", Constants.AMBIENT_LIGHT);
		setUniform("lightDirection", light.getDirection());
		setUniform("cameraPosition", c.getPosition());
		setUniform("specularPower", Constants.SPECULAR_POWER);
		setUniform("material", material);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	public void renderEntity(Entity e) {
		e.model().bindVAO();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glDrawElements(GL_TRIANGLES, e.model().getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glBindVertexArray(0);
	}

}
