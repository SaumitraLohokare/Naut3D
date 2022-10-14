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
uniform vec3 lightDirection;
uniform vec3 cameraPosition;
uniform float specularPower;
uniform Material material;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColors(Material material) {
	ambientC = material.ambient;
	diffuseC = material.diffuse;
	specularC = material.specular;
}

float cellRound(float factor) {
	return round(factor * 2) / 2;
}

vec3 calculateDiffuse(vec3 light_dir) {
	float diffuseFactor = max(dot(fragNormal, light_dir), 0.0);
	
	// Cell shading: 
	// (x * 3) / 3 --> gives us more rings for cell shading
	// somewhere between 2 & 3 is a good number 
	// diffuseFactor = cellRound(diffuseFactor * 3) / 3;
	return ambientLight * diffuseFactor;
}

vec3 calculateSpecular(vec3 light_dir) {
	vec3 cameraDirection = normalize(cameraPosition - fragNormal);
	vec3 reflectDirection = normalize(reflect(-light_dir, fragNormal));
	float specularFactor = pow(max(dot(cameraDirection, reflectDirection), 0.0), material.reflectance);
	
	return specularPower * specularFactor * ambientLight;
}

void main(void) {
	setupColors(material);
	
	vec3 normalizedLightDirection = normalize(lightDirection);
	
	vec4 diffuseColor = vec4(calculateDiffuse(normalizedLightDirection), 1);
	vec4 specularColor = vec4(calculateSpecular(normalizedLightDirection), 1);
	vec4 overallColor = vec4(ambientLight, 1) + diffuseColor + specularColor;
	
	out_color = overallColor * ambientC;
}