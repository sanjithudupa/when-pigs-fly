package main.canvases;

import engine.Mouse;
import engine.ui.*;

public class FlyingCanvas implements Canvas {

    private final UIElement[] elements;
    private final Text text;
    private final Image red;

    public FlyingCanvas() throws Exception {
        text = new Text("");

        red = new Image("Moonshot/src/resources/textures/ui/red.jpg");
        red.setOpacity(0.0f);
        red.setScale(10);

        text.setScale(2.5f);

        elements = new UIElement[] { text, red };
    }

    public void danger(boolean in) {
        red.setOpacity(in ? 0.1f : 0);
    }
    
    public void setDistance(int distance) {
        text.setText(distance + " ft");
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
