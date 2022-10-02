package testbed;

import org.lwjgl.Version;

import naut.core.RenderManager;
import naut.core.WindowManager;

public class Launcher {
	
	private WindowManager window;
	private RenderManager renderer;

	public static void main(String[] args) {
		System.out.println(Version.getVersion());
		
		var launcher = new Launcher();
		launcher.init();
		launcher.run();
		launcher.cleanUp();
	}

	private void init() {
		window = new WindowManager(1280, 720, "Naut Engine");
		renderer = new RenderManager();
	}
	
	private void run() {
		while (!window.shouldClose()) {
			renderer.render();
			window.update();
		}
	}
	
	private void cleanUp() {
		window.cleanUp();
	}
}
