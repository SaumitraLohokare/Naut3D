package naut.textures;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import java.nio.ByteBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {
	private int id;
	
	public Texture(int id) {
		this.id = id;
	}
	
	public static Texture loadTexture(String fileName) {
		int width, height;
		ByteBuffer buffer;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			var w = stack.mallocInt(1);
			var h = stack.mallocInt(1);
			var c = stack.mallocInt(1);
			
			buffer = STBImage.stbi_load(fileName, w, h, c, 4);
			if (buffer == null) {
				throw new IllegalStateException("Failed to load texture: " + fileName
						+ " reason:\n" + STBImage.stbi_failure_reason());
			}
			
			width = w.get();
			height = h.get();
		}
		
		var textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glGenerateMipmap(GL_TEXTURE_2D);
		STBImage.stbi_image_free(buffer);
		
		return new Texture(textureID);
	}

	public int id() {
		return id;
	}
}
