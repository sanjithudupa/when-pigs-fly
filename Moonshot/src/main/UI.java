package main;

import org.joml.Vector4f;

import engine.Entity;
import engine.Window;
import engine.graph.Mesh;
import engine.graph.Texture;
import engine.ui.Canvas;
import engine.ui.Image;
import engine.ui.TextItem;

public class UI implements Canvas {

    private static final int FONT_COLS = 16;
    
    private static final int FONT_ROWS = 16;

    private static final String FONT_TEXTURE = "Moonshot/src/resources/textures/font_texture.png";

    private final Entity[] entities;

    private final TextItem statusTextItem;

    public UI(String statusText) throws Exception {
        this.statusTextItem = new TextItem(statusText, FONT_TEXTURE, FONT_COLS, FONT_ROWS);
        entities = new Entity[]{statusTextItem};
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
