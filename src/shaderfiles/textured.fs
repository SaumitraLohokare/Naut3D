#version 320 core

in vec2 fragTexCoords;
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

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform Material material;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColors(Material material, vec2 textCoords) {
	if (material.hasTexture == 1) {
		ambientC = texture(textureSampler, textCoords);
		diffuseC = ambientC;
		specularC = ambientC;
	} else {
		ambientC = material.ambient;
		diffuseC = material.diffuse;
		specularC = material.specular;
	}
}

void main(void) {

	setupColors(material, fragTexCoords);
	
	out_color = ambientC * vec4(ambientLight, 1);
}