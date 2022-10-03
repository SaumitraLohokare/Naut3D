package naut.models;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private List<Integer> vaos, vbos;
	private int vertexCount;
	
	public Model(float[] vertices, float[] normals, int[] indices, float[] texCoords) {
		vaos = new ArrayList<>();
		vbos = new ArrayList<>();
		vertexCount = indices.length;
		
		var vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		storeIndices(indices);
		storeAttribute(0, 3, vertices);
		if (texCoords != null) {
			storeAttribute(1, 2, texCoords);
		}
		storeAttribute(2, 3, normals);
		
		glBindVertexArray(0);
		vaos.add(vao);
	}
	
	private void storeIndices(int[] indices) {
		var vbo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
	}
	
	private void storeAttribute(int attributeNo, int vertexCount, float[] attribute) {
		var vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, attribute, GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNo, vertexCount, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		vbos.add(vbo);
	}
	
	public void bindVAO() {
		glBindVertexArray(vaos.get(0));
	}
	
	public void cleanUp() {
		for (var vao : vaos) {
			glDeleteVertexArrays(vao);
		}
		for (var vbo : vbos) {
			glDeleteBuffers(vbo);
		}
	}

	public int getVertexCount() {
		return vertexCount;
	}
	
}
