package main;

import engine.Entity;
import engine.ui.Canvas;
import engine.ui.Image;

public class UI implements Canvas {

    private final Entity[] uiElements;

    private final Image image;

    public UI(String imageFile) throws Exception {
        this.image = new Image("Moonshot/src/resources/textures/Tex_Pig.png");
        uiElements = new Entity[]{this.image};
    }

    @Override
    public Entity[] getEntities() {
        return uiElements;
    }
    
}
