package engine.ui;

import java.util.ArrayList;
import java.util.List;

import engine.Entity;
import engine.Utils;
import engine.graph.Mesh;
import engine.graph.Texture;

public class Image extends Entity {

    private String filename;

    private final int width;
    private final int height;


    public Image(String filename) throws Exception {
        super();
        this.filename = filename;
        Texture texture = new Texture(filename);
        this.setMesh(buildMesh(texture, FONT_COLS, FONT_ROWS));
    }

    private Mesh buildMesh(Texture texture) {
        byte[] chars = text.getBytes(Charset.forName("ISO-8859-1"));
        int numChars = chars.length;

        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        float[] normals   = new float[0];
        List<Integer> indices   = new ArrayList<>();
        
        float tileWidth = (float)texture.getWidth();
        float tileHeight = (float)texture.getHeight();
            
        // Build a character tile composed by two triangles
        
        // Left Top vertex
        positions.add((float)0*tileWidth); // x
        positions.add(0.0f); //y
        positions.add(ZPOS); //z
        textCoords.add((float)col / (float)numCols );
        textCoords.add((float)row / (float)numRows );
        indices.add(i*VERTICES_PER_QUAD);
                    
        // Left Bottom vertex
        positions.add((float)i*tileWidth); // x
        positions.add(tileHeight); //y
        positions.add(ZPOS); //z
        textCoords.add((float)col / (float)numCols );
        textCoords.add((float)(row + 1) / (float)numRows );
        indices.add(i*VERTICES_PER_QUAD + 1);

        // Right Bottom vertex
        positions.add((float)i*tileWidth + tileWidth); // x
        positions.add(tileHeight); //y
        positions.add(ZPOS); //z
        textCoords.add((float)(col + 1)/ (float)numCols );
        textCoords.add((float)(row + 1) / (float)numRows );
        indices.add(i*VERTICES_PER_QUAD + 2);

        // Right Top vertex
        positions.add((float)i*tileWidth + tileWidth); // x
        positions.add(0.0f); //y
        positions.add(ZPOS); //z
        textCoords.add((float)(col + 1)/ (float)numCols );
        textCoords.add((float)row / (float)numRows );
        indices.add(i*VERTICES_PER_QUAD + 3);
        
        // Add indices por left top and bottom right vertices
        indices.add(i*VERTICES_PER_QUAD);
        indices.add(i*VERTICES_PER_QUAD + 2);
    
        
        float[] posArr = Utils.listToArray(positions);
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i->i).toArray();
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
    
}
