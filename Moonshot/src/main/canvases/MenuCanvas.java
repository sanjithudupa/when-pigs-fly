package main.canvases;

import engine.Mouse;
import engine.ui.*;

public class MenuCanvas implements Canvas {

    private final UIElement[] elements;

    private final int optionsBottom = 750;
    private final int optionsTop = -120;

    private final Image options;

    public MenuCanvas() throws Exception {
        Image logo = new Image("res/textures/ui/logo.png");
        logo.setScale(2);
        logo.setCentered(true);
        logo.setOffset(-logo.getWidth()/2, -540+logo.getHeight());

        Image buttons = new Image("res/textures/ui/wood.png");
        buttons.setScale(1.1f);
        buttons.setCentered(true);
        buttons.setOffset(-buttons.getWidth()/4, -120);

        options = new Image("res/textures/ui/options.png");
        options.setScale(1.1f);
        options.setCentered(true);
        options.setOffset(-options.getWidth()/4, optionsBottom);

        elements = new UIElement[] { options, logo, buttons };
    }

    public void moveOptions(int totalCount, int count, boolean top) {
        float fullDist = (float)optionsBottom - (float)optionsTop;
        float ratio = (float)count/totalCount;

        if(top)
            ratio = 1 - ratio;
        
        if(ratio < 0 || ratio > 1)
            return;
        
        float moveDist = fullDist * ratio;
        float newPos = optionsBottom - moveDist;

        options.setOffset(options.getOffset().x, newPos);
    }

    @Override
    public UIElement[] getElements() {
        return elements;
    }

    @Override
    public void input(Mouse input) {

    }
    
}
