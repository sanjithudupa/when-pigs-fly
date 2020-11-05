package main;

import org.joml.Vector4f;

import engine.Entity;
import engine.Window;
import engine.graph.Mesh;
import engine.graph.Texture;
import engine.ui.Canvas;
import engine.ui.Image;
import engine.ui.Text;

public class UI implements Canvas {

    private final Entity[] entities;

    private final Text statusTextItem;

    public UI(String text) throws Exception {
        this.statusTextItem = new Text(text);

        Image image = new Image("Moonshot/src/resources/textures/tex_pig.png");
        image.setScale(10);

        image.setPosition(10, 20, 0);
        entities = new Entity[]{image};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    @Override
    public Entity[] getEntities() {
        return entities;
    }
    
    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
    }
    
}
