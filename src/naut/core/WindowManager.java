package naut.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class WindowManager {
	
	private static int WIDTH, HEIGHT;
	private static String TITLE;
	
	private static long window;
	
	private static final float FOV = (float) Math.toRadians(70); 
	private static final float NEAR_PLANE = 0.1f; 
	private static final float FAR_PLANE = 1000f;
	
	private Matrix4f projectionMatrix;
	
	public WindowManager(int width, int height, String title) {
		WIDTH = width;
		HEIGHT = height;
		TITLE = title;
		
		init();
		
		projectionMatrix = createProjectionMatrix();
	}
	
	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		// TODO
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(
			window,
			(vidmode.width() - WIDTH) / 2,
			(vidmode.height() - HEIGHT) / 2
		);
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
		
		createCapabilities();
		glClearColor(1, 1, 1, 1);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_STENCIL_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}
	
	public void update() {
		glfwPollEvents();
	}
	
	public void cleanUp() {
		glfwDestroyWindow(window);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	private Matrix4f createProjectionMatrix() {
		float aspectRatio = (float) WIDTH / (float) HEIGHT;
		
		var matrix = new Matrix4f().setPerspective(FOV, aspectRatio, NEAR_PLANE, FAR_PLANE);
		return matrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public static long getWindow() {
		if (window == NULL)
			throw new IllegalStateException("Window not created.");
		return window;
	}
}
