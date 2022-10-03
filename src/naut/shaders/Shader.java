package naut.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import naut.entities.Entity;
import naut.materials.Material;

public abstract class Shader {
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private final Map<String, Integer> uniforms;
	
	public Shader(String vertexFile,String fragmentFile){
		uniforms = new HashMap<>();
		
		vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
	}
	
	public abstract void renderEntity(Entity e);
	
	public void createUniform(String name) {
		int location = GL20.glGetUniformLocation(programID, name);
		if (location < 0)
			throw new IllegalStateException("Uniform " + name + " not foind in shader.");
		uniforms.put(name, location);
	}
	
	public void createMaterialUniform(String name) {
		createUniform(name + ".ambient");
		createUniform(name + ".diffuse");
		createUniform(name + ".specular");
		createUniform(name + ".hasTexture");
		createUniform(name + ".reflectance");
	}
	
	public void setUniform(String name, int value) {
		GL20.glUniform1i(uniforms.get(name), value);
	}
	
	public void setUniform(String name, float value) {
		GL20.glUniform1f(uniforms.get(name), value);
	}
	
	public void setUniform(String name, boolean value) {
		GL20.glUniform1f(uniforms.get(name), value ? 1 : 0);
	}
	
	public void setUniform(String name, Vector3f value) {
		try (var stack = MemoryStack.stackPush()) {
			GL20.glUniform4fv(uniforms.get(name), value.get(stack.mallocFloat(3)));
		}
	}
	
	public void setUniform(String name, Vector4f value) {
		try (var stack = MemoryStack.stackPush()) {
			GL20.glUniform4fv(uniforms.get(name), value.get(stack.mallocFloat(4)));
		}
	}
	
	public void setUniform(String name, Material material) {
		setUniform(name + ".ambient", material.getAmbientColor());
		setUniform(name + ".diffuse", material.getDiffuseColor());
		setUniform(name + ".specular", material.getSpecularColor());
		setUniform(name + ".hasTexture", material.hasTexture() ? 1 : 0);
		setUniform(name + ".reflectance", material.getReflectance());
	}
	
	public void setUniform(String name, Matrix4f value) {
		try (var stack = MemoryStack.stackPush()) {
			GL20.glUniformMatrix4fv(uniforms.get(name), false, value.get(stack.mallocFloat(16)));
		}
	}
	
	protected void start(){
		GL20.glUseProgram(programID);
	}
	
	public void stop(){
		GL20.glUseProgram(0);
	}
	
	public void cleanUp(){
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName){
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine())!=null){
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}

}
