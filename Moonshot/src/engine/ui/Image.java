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
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        this.setMesh(buildMesh(texture));
        this.setScale(10);
    }

    private Mesh buildMesh(Texture texture) {

        List<Float> positions = new ArrayList<>();
        List<Float> textCoords = new ArrayList<>();
        float[] normals   = new float[0];
        List<Integer> indices   = new ArrayList<>();

        float[] textCoordsA = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
        };

        // Left Top vertex
        positions.add((float)this.width); // x
        positions.add(0.0f); //y
        positions.add(2.0f); //z
        textCoords.add(1.0f);
        textCoords.add(0.0f);
        indices.add(4);
                    
        // Left Bottom vertex
        positions.add((float)this.width); // x
        positions.add((float)this.height); //y
        positions.add(2.0f); //z
        textCoords.add(0.0f);
        textCoords.add(1.0f);
        indices.add(5);

        // Right Bottom vertex
        positions.add((float)this.width); // x
        positions.add((float)this.height); //y
        positions.add(2.0f); //z
        textCoords.add(1.0f);
        textCoords.add(1.0f);
        indices.add(6);

        // Right Top vertex
        positions.add((float)this.width); // x
        positions.add(0.0f); //y
        positions.add(2.0f); //z
        textCoords.add(1.0f);
        textCoords.add(1.0f);
        indices.add(7);
        
        // Add indices por left top and bottom right vertices
        indices.add(4);
        indices.add(6);
        
        float[] posArr = Utils.listToArray(positions);
        float[] textCoordsArr = Utils.listToArray(textCoords);
        int[] indicesArr = indices.stream().mapToInt(i->i).toArray();
        Mesh mesh = new Mesh(posArr, textCoordsArr, normals, indicesArr);
        System.out.println(mesh.getVaoId());
        mesh.setTexture(texture);
        return mesh;
    }
    
}
