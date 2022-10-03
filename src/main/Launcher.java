package main;

import org.lwjgl.Version;

import naut.core.GameLogic;
import naut.core.Input;
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
		long last = System.currentTimeMillis();
		int frames = 0;
		long lastFrameTime = System.nanoTime();
		long currentFrameTime = 0;
		
		while (!window.shouldClose()) {
			frames += 1;
			if (System.currentTimeMillis() - last >= 1000) {
				last = System.currentTimeMillis();
				WindowManager.setTitle(String.valueOf(frames));
				frames = 0;
			}
			
			currentFrameTime = System.nanoTime();
			var delta = (currentFrameTime - lastFrameTime) / 1000000000d;
			lastFrameTime = currentFrameTime;
			
			game.update(delta);
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
