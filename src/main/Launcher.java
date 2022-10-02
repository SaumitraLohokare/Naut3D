package main;

import org.lwjgl.Version;

import naut.core.GameLogic;
import naut.core.RenderManager;
import naut.core.WindowManager;

public class Launcher {
	
	private static WindowManager window;
	private static RenderManager renderer;
	private static GameLogic game;

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
		game = new GameLogic();
	}
	
	private void run() {
		while (!window.shouldClose()) {
			game.update();
			renderer.render();
			window.update();
		}
	}
	
	private void cleanUp() {
		window.cleanUp();
	}
	
	public static WindowManager getWindow() {
		return window;
	}
	
}
