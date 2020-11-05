package main;

import javax.sound.midi.SysexMessage;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.Entity;
import engine.Window;
import engine.graph.Mesh;
import engine.graph.Texture;
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

        image.setOffset(0, 0);

        Text text = new Text("hello");

        text.setCentered(true);

        entities = new UIElement[]{ image };
    }

    @Override
    public UIElement[] getElements() {
        return entities;
    }

    // @Override
    // public void update(Window window) {
    //     center = new Vector2f((float)window.getWidth()/2, (float)window.getHeight()/2);

    //     image.setPosition(window.getWidth()/2, center.y, 0);
    //     System.out.println("udpate");
    // }
    
    // public void updateSize(Window window) {
    //     this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
    //     screenSize.x = window.getWidth();
    //     screenSize.y = window.getHeight();
    // }
    
}