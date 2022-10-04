#version 320 core

in vec3 position;
in vec2 texCoords;
in vec3 normal;

out vec2 fragTexCoords;
out vec3 fragNormal;
out vec3 fragPos;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldPos;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz;
	
	fragTexCoords = texCoords;
}
