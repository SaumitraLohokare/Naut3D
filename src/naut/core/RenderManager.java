package naut.core;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import naut.scenes.Scene;

public class RenderManager {

	private void renderStart() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private void renderEnd() {
		glfwSwapBuffers(WindowManager.getWindow());
	}
	
	public void render() {
		renderStart();
		
		Scene.currentScene().render();
		// UI Rendering
		
		renderEnd();
	}
}
