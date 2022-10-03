#version 330 core

in vec3 color;
in vec3 fragNormal;
in vec3 fragPos;

out vec4 out_color;

struct Material {
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	int hasTexture;
	float reflectance;
};

uniform vec3 ambientLight;
uniform Material material;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColors(Material material) {
	ambientC = material.ambient;
	diffuseC = material.diffuse;
	specularC = material.specular;
}

void main(void) {
	setupColors(material);
	
	// ambientC = material.ambient * material.specular * material.diffuse;
	if (ambientC.x == 0 || ambientC.y == 0 || ambientC.z == 0) {
		out_color = vec4(color, 1.0);
	} else {
		out_color = ambientC * vec4(ambientLight, 1.0);
	}
}