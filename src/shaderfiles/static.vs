#version 330 core

in vec3 position;

out vec3 color;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void){
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
	color = vec3(1.0, 0.0, 0.0);
}