package naut.core;

import naut.scenes.Scene;
import naut.scenes.TestScene;

public class GameLogic {
	
	public GameLogic() {
		init();
	}
	
	public void init() {
		Scene.init();
		// create scenes here.
		var testScene = new TestScene();
		Scene.addScene("test", testScene);
		Scene.changeScene("test");
	}
	
	public void update() {
		
	}

}
