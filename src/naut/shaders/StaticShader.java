package naut.shaders;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import main.Launcher;
import naut.entities.Entity;
import naut.materials.Material;
import naut.scenes.Scene;

public class StaticShader extends Shader {

	private static final String VS = "src/shaderfiles/static.vs";
	private static final String FS = "src/shaderfiles/static.fs";
	
	private static final Vector3f AMBIENT_LIGHT = new Vector3f(1f, 1f, 1f);
	
	public StaticShader() {
		super(VS, FS);
		
		createUniform("transformationMatrix");
		createUniform("viewMatrix");
		createUniform("projectionMatrix");
		createUniform("ambientLight");
		createMaterialUniform("material");
	}
	
	public void start(Matrix4f t, Material material) {
		super.start();
		
		// setup uniforms
		setUniform("projectionMatrix", Launcher.getWindow().getProjectionMatrix());
		setUniform("viewMatrix", Scene.currentScene().getCameraViewMatrix());
		setUniform("transformationMatrix", t);
		
		setUniform("ambientLight", AMBIENT_LIGHT);
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
