package naut.shaders;

import java.util.HashMap;
import java.util.Map;

public class ShaderLoader {
	public static Map<String, Shader> shaders;
	
	public static void init() {
		shaders = new HashMap<>();
		
		shaders.put("static", new StaticShader());
		shaders.put("textured", new TexturedShader());
	}
	
	public static void cleanUp() {
		for (var entry : shaders.entrySet()) {
			entry.getValue().cleanUp();
		}
	}
}
