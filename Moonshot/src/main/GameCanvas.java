package main;

import javax.sound.midi.SysexMessage;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.Entity;
import engine.Mouse;
import engine.Window;
import engine.graph.Mesh;
import engine.graph.Texture;
import engine.ui.Button;
import engine.ui.Canvas;
import engine.ui.Image;
import engine.ui.Text;
import engine.ui.UIElement;

public class GameCanvas implements Canvas {

    private final UIElement[] entities;
    private static Vector2f center = new Vector2f(0, 0);

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
