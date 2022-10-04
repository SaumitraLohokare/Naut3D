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
import org.joml.Vector3f;

import main.Launcher;
import naut.entities.Entity;
import naut.materials.Material;
import naut.scenes.Scene;

public class TexturedShader extends Shader {
	
	private static final String VS = "src/shaderfiles/textured.vs", FS = "src/shaderfiles/textured.fs";
	private static final Vector3f AMBIENT_LIGHT = new Vector3f(1f, 1f, 1f);
	
	public TexturedShader() {
		super(VS, FS);

		createUniform("transformationMatrix");
		createUniform("viewMatrix");
		createUniform("projectionMatrix");
		createUniform("textureSampler");
		createUniform("ambientLight");
		createMaterialUniform("material");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	public void start(Matrix4f t, Material material) {
		super.start();
		setUniform("textureSampler", 0);
		
		// setup uniforms
		setUniform("projectionMatrix", Launcher.getWindow().getProjectionMatrix());
		setUniform("viewMatrix", Scene.currentScene().getCameraViewMatrix());
		setUniform("transformationMatrix", t);
		
		setUniform("ambientLight", AMBIENT_LIGHT);
		setUniform("material", material);
	}

	public void renderEntity(Entity e) {
		e.model().bindVAO();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, e.getMaterial().getTexture().id());
		glDrawElements(GL_TRIANGLES, e.model().getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glBindVertexArray(0);
		
	}

}
