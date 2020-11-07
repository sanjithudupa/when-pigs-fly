package main.canvases;

import org.joml.Vector3f;

import engine.Mouse;
import engine.ui.*;
import engine.ui.UIElement;

public class Overlay implements Canvas{
    private UIElement[] elements;

    Panel overlay = null;

    public Overlay() throws Exception {
        overlay = new Panel(new Vector3f(0.0f, 0.0f, 0.0f), 1920, 1080);
        overlay.setOpacity(0.0f);
        elements = new UIElement[] { overlay };
    }

    public void setOpacity(float opacity){
        overlay.setOpacity(opacity);
    }

    @Override
    public UIElement[] getElements() {
        return elements;
    }

    @Override
    public void input(Mouse input) { }
    
}
