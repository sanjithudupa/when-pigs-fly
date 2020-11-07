package engine.ui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Mouse;
import engine.Utils;
import engine.graph.Mesh;

public class Panel extends UIElement {

    private final int width;
    private final int height;

    public Panel(Vector3f color, int width, int height) throws Exception {
        super();
        this.width = width;
        this.height = height;

        this.setMesh(buildMesh(color, width, height));
    }

    private Mesh buildMesh(Vector3f color, float width, float height) {

        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        float[] normals = new float[0];
        List<Integer> indices = new ArrayList<>();

        // Build a character tile composed by two triangles

        // Left Top vertex
        positions.add((float) 0 * width); // x
        positions.add(0.0f); // y
        positions.add(ZPOS); // z
        textCoords.add(0.0f);
        textCoords.add(0.0f);
        indices.add(0);

        // Left Bottom vertex
        positions.add((float) 0 * width); // x
        positions.add(height); // y
        positions.add(ZPOS); // z
        textCoords.add(0.0f);
        textCoords.add(1.0f);
        indices.add(1);

        // Right Bottom vertex
        positions.add((float) 0 * width + width); // x
        positions.add(height); // y
        positions.add(ZPOS); // z
        textCoords.add(1.0f);
        textCoords.add(1.0f);
        indices.add(2);

        // Right Top vertex
        positions.add((float) 0 * width + width); // x
        positions.add(0.0f); // y
        positions.add(ZPOS); // z
        textCoords.add(1.0f);
        textCoords.add(0.0f);
        indices.add(3);

        // Add indices por left top and bottom right vertices
        indices.add(0);
        indices.add(2);

        float[] posArr = Utils.listToArray(positions);
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i -> i).toArray();
        Mesh mesh = new Mesh(posArr, textCoordsArr, normals, indicesArr);
        mesh.setColor(color);
        return mesh;
    }

    @Override
    public Vector2f getBbox() {
        return new Vector2f(width * getScale()/2, height * getScale()/2);
    }

    @Override
    public void input(Mouse mouse) { }
    
}
