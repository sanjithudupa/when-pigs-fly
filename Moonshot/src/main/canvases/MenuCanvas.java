package main.canvases;

import engine.Mouse;
import engine.ui.*;

public class MenuCanvas implements Canvas {

    private final UIElement[] elements;

    public MenuCanvas() throws Exception {
        Image image = new Image("Moonshot/src/resources/textures/ui/logo.png");
        image.setScale(2);
        image.setPosition(482, 0, 0);

        elements = new UIElement[] {  };
    }

    @Override
    public UIElement[] getElements() {
        return elements;
    }

    @Override
    public void input(Mouse input) {

    }
    
}
