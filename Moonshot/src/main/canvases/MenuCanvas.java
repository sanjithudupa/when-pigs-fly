package main.canvases;

import engine.Mouse;
import engine.ui.*;

public class MenuCanvas implements Canvas {

    private final UIElement[] elements;

    public MenuCanvas() throws Exception {
        Image logo = new Image("Moonshot/src/resources/textures/ui/logo.png");
        logo.setScale(2);
        logo.setPosition(482, 0, 0);

        Image buttons = new Image("Moonshot/src/resources/textures/ui/wood2.png");
        buttons.setScale(1.1f);
        buttons.setPosition(812.5f, 200, 0);

        elements = new UIElement[] { logo, buttons };
    }

    @Override
    public UIElement[] getElements() {
        return elements;
    }

    @Override
    public void input(Mouse input) {

    }
    
}
