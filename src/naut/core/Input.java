package naut.core;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

	public static boolean getKeyDown(int key) {
		var action = glfwGetKey(WindowManager.getWindow(), key); 
		return action == GLFW_PRESS || action == GLFW_REPEAT;
	}
	
}
