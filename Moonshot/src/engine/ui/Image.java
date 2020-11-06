package engine.ui;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import engine.Mouse;
import engine.Utils;
import engine.graph.Mesh;
import engine.graph.Texture;

public class Image extends UIElement {

    private static final float ZPOS = 0.0f;
    private String filename;

    private final int width;
    private final int height;

    public Image(String filename) throws Exception {
        super();
        this.filename = filename;
        Texture texture = new Texture(filename);

        this.width = texture.getWidth();
        this.height = texture.getHeight();

        this.setMesh(buildMesh(texture));
    }

    private Mesh buildMesh(Texture texture) {

        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        float[] normals = new float[0];
        List<Integer> indices = new ArrayList<>();

        float tileWidth = (float) texture.getWidth();
        float tileHeight = (float) texture.getHeight();

        // Build a character tile composed by two triangles

        // Left Top vertex
        positions.add((float) 0 * tileWidth); // x
        positions.add(0.0f); // y
        positions.add(ZPOS); // z
        textCoords.add(0.0f);
        textCoords.add(0.0f);
        indices.add(0);

        // Left Bottom vertex
        positions.add((float) 0 * tileWidth); // x
        positions.add(tileHeight); // y
        positions.add(ZPOS); // z
        textCoords.add(0.0f);
        textCoords.add(1.0f);
        indices.add(1);

        // Right Bottom vertex
        positions.add((float) 0 * tileWidth + tileWidth); // x
        positions.add(tileHeight); // y
        positions.add(ZPOS); // z
        textCoords.add(1.0f);
        textCoords.add(1.0f);
        indices.add(2);

        // Right Top vertex
        positions.add((float) 0 * tileWidth + tileWidth); // x
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
        mesh.setTexture(texture);
        return mesh;
    }

    @Override
    public Vector2f getBbox() {
        return new Vector2f(width * getScale(), height * getScale());
    }

    @Override
    public void input(Mouse mouse) { }
    
}
