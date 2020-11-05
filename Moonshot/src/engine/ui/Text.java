package engine.ui;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import engine.Entity;
import engine.Utils;
import engine.graph.Mesh;
import engine.graph.Texture;

public class Text extends UIElement {

    private static final float ZPOS = 0.0f;
    private static final int FONT_COLS = 16;
    private static final int FONT_ROWS = 16;

    private Vector2f bbox;

    private static final String FONT_TEXTURE = "Moonshot/src/resources/textures/font_texture.png";

    private static final int VERTICES_PER_QUAD = 4;

    private String text;

    public Text(String text) throws Exception {
        super();
        this.text = text;
        Texture texture = new Texture(FONT_TEXTURE);
        this.setMesh(buildMesh(texture, FONT_COLS, FONT_ROWS));
    }

    private Mesh buildMesh(Texture texture, int numCols, int numRows) {
        byte[] chars = text.getBytes(Charset.forName("ISO-8859-1"));
        int numChars = chars.length;

        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        float[] normals = new float[0];
        List<Integer> indices = new ArrayList<>();

        float tileWidth = (float) texture.getWidth() / (float) numCols;
        float tileHeight = (float) texture.getHeight() / (float) numRows;

        for (int i = 0; i < numChars; i++) {
            byte currChar = chars[i];
            int col = currChar % numCols;
            int row = currChar / numCols;

            // Build a character tile composed by two triangles

            // Left Top vertex
            positions.add((float) i * tileWidth); // x
            positions.add(0.0f); // y
            positions.add(ZPOS); // z
            textCoords.add((float) col / (float) numCols);
            textCoords.add((float) row / (float) numRows);
            indices.add(i * VERTICES_PER_QUAD);

            // Left Bottom vertex
            positions.add((float) i * tileWidth); // x
            positions.add(tileHeight); // y
            positions.add(ZPOS); // z
            textCoords.add((float) col / (float) numCols);
            textCoords.add((float) (row + 1) / (float) numRows);
            indices.add(i * VERTICES_PER_QUAD + 1);

            // Right Bottom vertex
            positions.add((float) i * tileWidth + tileWidth); // x
            positions.add(tileHeight); // y
            positions.add(ZPOS); // z
            textCoords.add((float) (col + 1) / (float) numCols);
            textCoords.add((float) (row + 1) / (float) numRows);
            indices.add(i * VERTICES_PER_QUAD + 2);

            // Right Top vertex
            positions.add((float) i * tileWidth + tileWidth); // x
            positions.add(0.0f); // y
            positions.add(ZPOS); // z
            textCoords.add((float) (col + 1) / (float) numCols);
            textCoords.add((float) row / (float) numRows);
            indices.add(i * VERTICES_PER_QUAD + 3);

            // Add indices por left top and bottom right vertices
            indices.add(i * VERTICES_PER_QUAD);
            indices.add(i * VERTICES_PER_QUAD + 2);
        }
        
        float width = numChars * tileWidth;
        float height = tileHeight;

        bbox = new Vector2f(width, height);

        float[] posArr = Utils.listToArray(positions);
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i -> i).toArray();
        Mesh mesh = new Mesh(posArr, textCoordsArr, normals, indicesArr);
        mesh.setTexture(texture);
        return mesh;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        Texture texture = this.getMesh().getTexture();
        this.getMesh().deleteBuffers();
        this.setMesh(buildMesh(texture, FONT_COLS, FONT_ROWS));
    }

    @Override
    public Vector2f getBbox() {
        return bbox;
    }
}