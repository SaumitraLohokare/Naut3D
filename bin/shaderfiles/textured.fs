#version 400 core

in vec2 fragTexCoords;
out vec4 out_color;

uniform sampler2D textureSampler;

void main(void) {
	out_color = texture(textureSampler, fragTexCoords);
}