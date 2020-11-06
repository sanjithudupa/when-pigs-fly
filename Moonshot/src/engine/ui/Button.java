package engine.ui;

import org.joml.Vector2f;

import engine.Mouse;

public class Button extends Image {

    private boolean isPressed = false;
    private Vector2f topLeft = null;
    public Button(String filename) throws Exception {
        super(filename);
        topLeft = new Vector2f(this.getPosition().x - this.getBbox().x/2, this.getPosition().y - this.getBbox().y/2);
        
    }

    @Override
    public void input(Mouse mouse){
        this.getBbox();
    }
    
}
