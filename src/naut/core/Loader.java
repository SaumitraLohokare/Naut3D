package naut.core;

import java.io.BufferedReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import naut.models.Model;

public class Loader {

	public static Model loadOBJModel(String file) {
		var lines = readLines(file);
		List<Vector3f> vertices = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		List<Vector2f> textures = new ArrayList<>();
		List<Vector3i> faces = new ArrayList<>();

		for (var line : lines) {
			String[] tokens = line.split("\\s");
			switch (tokens[0]) {
			case "v":
				var vec = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3]));
				vertices.add(vec);
				break;
			case "vt":
				var texVec = new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
				textures.add(texVec);
				break;
			case "vn":
				var normalsVec = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3]));
				normals.add(normalsVec);
				break;
			case "f":
				for (int i = 1; i < tokens.length; i++ ) {
					faces.add(processFace(tokens[i]));
				}
//				faces.add(processFace(tokens[1]));
//				faces.add(processFace(tokens[2]));
//				faces.add(processFace(tokens[3]));
				break;
			default:
			}
		}

		List<Integer> indices = new ArrayList<>();
		float[] verticesArray = new float[vertices.size() * 3];
		int i = 0;
		for (var pos : vertices) {
			verticesArray[i * 3] = pos.x;
			verticesArray[i * 3 + 1] = pos.y;
			verticesArray[i * 3 + 2] = pos.z;
			i++;
		}

		float[] texturesArray = new float[vertices.size() * 3];
		float[] normalsArray = new float[vertices.size() * 3];

		for (var face : faces) {
			processVertex(face.x, face.y, face.z, textures, normals, indices, texturesArray, normalsArray);
		}

		int[] indicesArray = indices.stream().mapToInt((Integer V) -> V).toArray();
		return new Model(verticesArray, normalsArray, indicesArray, texturesArray);

	}

	private static void processVertex(int pos, int texCoord, int normal, List<Vector2f> texCoordList,
			List<Vector3f> normalList, List<Integer> indicesList, float[] texCoords, float[] normalArr) {
		indicesList.add(pos);
		if (texCoord >= 0) {
			var texCoordVec = texCoordList.get(texCoord);
			texCoords[pos * 2] = texCoordVec.x;
			texCoords[pos * 2 + 1] = 1 - texCoordVec.y;
		}

		if (normal >= 0) {
			var normalVec = normalList.get(normal);
			normalArr[pos * 3] = normalVec.x;
			normalArr[pos * 3 + 1] = normalVec.y;
			normalArr[pos * 3 + 2] = normalVec.z;
		}
	}

	private static Vector3i processFace(String token) {
		String[] lineToken = token.split("/");
		int length = lineToken.length;
		int pos = -1, coords = -1, normal = -1;
		pos = Integer.parseInt(lineToken[0]) - 1;
		if (length > 1) {
			String texCoord = lineToken[1];
			coords = texCoord.length() > 0 ? Integer.parseInt(texCoord) - 1 : -1;
			if (length > 2) {
				normal = Integer.parseInt(lineToken[2]) - 1;
			}
		}
		return new Vector3i(pos, coords, normal);
	}

	private static List<String> readLines(String file) {
		var lines = new ArrayList<String>();

		try {
			var br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null)
				lines.add(line);
		} catch (Exception e) {
			System.err.println("Failed to read file " + file);
			e.printStackTrace();
			System.exit(-1);
		}

		return lines;
	}

}
