package main.canvases;

import engine.Mouse;
import engine.ui.*;
import engine.ui.UIElement;

public class TestCanvas2 implements Canvas {

    private final UIElement[] elements;

    public TestCanvas2() throws Exception {
        Image image = new Image("Moonshot/src/resources/textures/tex_pig.png");
        image.setScale(10);
        image.setCentered(true);

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
