package main.canvases;

import engine.Mouse;
import engine.ui.*;

public class MenuCanvas implements Canvas {

    private final UIElement[] elements;

    public MenuCanvas() throws Exception {
        Image logo = new Image("res/textures/ui/logo.png");
        logo.setScale(2);
        logo.setCentered(true);
        logo.setOffset(-logo.getWidth()/2, -540+logo.getHeight());

        Image buttons = new Image("res/textures/ui/wood.png");
        buttons.setScale(1.1f);
        buttons.setCentered(true);
        buttons.setOffset(-buttons.getWidth()/4, -120);

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
