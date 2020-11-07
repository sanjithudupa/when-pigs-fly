package main.canvases;

import org.joml.Vector3f;

import engine.Mouse;
import engine.ui.*;
import engine.ui.UIElement;

public class GameCanvas implements Canvas {

    private final UIElement[] entities;

    Panel black = new Panel(new Vector3f(0.0f, 0.0f, 0.0f), 1920, 1080);

    public GameCanvas() throws Exception {
        Image image = new Image("Moonshot/src/resources/textures/tex_pig.png");
        image.setScale(10);
        image.setCentered(true);

        black.setOpacity(0.0f);

        entities = new UIElement[] { black, image };
    }

    public void setTransitionOpacity(float opacity){
        black.setOpacity(opacity);
    }



    @Override
    public UIElement[] getElements() {
        return entities;
    }

    @Override
    public void input(Mouse input) {
        for(UIElement element : getElements()){
            element.input(input);
        }

    }
    
}
