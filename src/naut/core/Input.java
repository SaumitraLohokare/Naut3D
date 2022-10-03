package naut.core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class Input {
	
	private static final float MOUSE_SENSITIVITY = 1f;
	
	private static Vector2d lastMousePos = new Vector2d(-1, -1), currMousePos = new Vector2d(0, 0);
	private static Vector2f displVec = new Vector2f();
	private static boolean inWindow = false, leftButtonPressed = false, rightButtonPressed = false;
	
	public static void setWindowCallbacks(long window) {
		GLFW.glfwSetKeyCallback(window, (w, key, scan, action, mods) -> {
			// ???
		});
		
		GLFW.glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {			
			currMousePos.x = xpos;
			currMousePos.y = ypos;
		});
		
		GLFW.glfwSetCursorEnterCallback(window, (w, entered) -> {
			inWindow = entered;
		});
		
		GLFW.glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
			leftButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
			rightButtonPressed = button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
		});
	}
	
	public static boolean getKeyDown(int key) {
		var action = glfwGetKey(WindowManager.getWindow(), key); 
		return action == GLFW_PRESS || action == GLFW_REPEAT;
	}
	
	public static void poll() {
		displVec.x = 0;
		displVec.y = 0;
		if (lastMousePos.x > 0 && lastMousePos.y > 0 && inWindow) {
			var x = currMousePos.x - lastMousePos.x;
			var y = currMousePos.y - lastMousePos.y;
			var rotateX = x != 0;
			var rotateY = y != 0;
			if (rotateX)
				displVec.y = (float)x * MOUSE_SENSITIVITY;
			if (rotateY)
				displVec.x = (float)y * MOUSE_SENSITIVITY;
		}
		lastMousePos.x = currMousePos.x;
		lastMousePos.y = currMousePos.y;
		
	}

	public static boolean isLeftButtonPressed() {
		return leftButtonPressed;
	}

	public static boolean isRightButtonPressed() {
		return rightButtonPressed;
	}

	public static Vector2f getDisplVec() {
		return displVec;
	}
	
}
