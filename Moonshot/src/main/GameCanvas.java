package main;

import engine.Mouse;
import engine.ui.*;
import engine.ui.UIElement;

public class GameCanvas implements Canvas {

    private final UIElement[] entities;

    public GameCanvas() throws Exception {

        Image image = new Image("Moonshot/src/resources/textures/tex_pig.png");
        image.setScale(10);
        image.setCentered(true);

        entities = new UIElement[] { image };
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
