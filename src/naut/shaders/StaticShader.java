package naut.shaders;

import org.joml.Matrix4f;

import main.Launcher;
import naut.scenes.Scene;

public class StaticShader extends Shader {

	private static final String VS = "src/shaderfiles/static.vs";
	private static final String FS = "src/shaderfiles/static.fs";
	
	public StaticShader() {
		super(VS, FS);
		
		createUniform("transformationMatrix");
		createUniform("viewMatrix");
		createUniform("projectionMatrix");
	}
	
	public void start(Matrix4f t) {
		super.start();
		
		// setup uniforms
		setUniform("projectionMatrix", Launcher.getWindow().getProjectionMatrix());
		setUniform("viewMatrix", Scene.currentScene().getCameraViewMatrix());
		setUniform("transformationMatrix", t);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
