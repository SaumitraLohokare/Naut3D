#version 330 core

in vec3 position;
in vec3 normal;

out vec3 color;
out vec3 fragNormal;
out vec3 fragPos;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main(void){
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPos;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz;
	
	color = vec3(1.0, 0.0, 0.0);
}