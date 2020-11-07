package main.canvases;

import engine.Mouse;
import engine.ui.*;
import engine.ui.UIElement;

public class TestCanvas implements Canvas {

    private final UIElement[] elements;

    public TestCanvas() throws Exception {
        Image image = new Image("Moonshot/src/resources/textures/tex_pig.png");
        image.setScale(10);
        image.setCentered(false);

        elements = new UIElement[] { image };
    }


    @Override
    public UIElement[] getElements() {
        return elements;
    }

    @Override
    public void input(Mouse input) {
        for(UIElement element : getElements()){
            element.input(input);
        }

    }
    
}
