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
        // System.out.println(this.getPosition().x);
        topLeft = new Vector2f(this.getPosition().x - this.getBbox().x/2, this.getPosition().y - this.getBbox().y/2);
        // System.out.println(topLeft.x + ", " + topLeft.y);
        // System.out.println(this.getBbox().x + ", " + this.getBbox().y);
        boolean inX = mouse.getPosition().x > topLeft.x && mouse.getPosition().x < topLeft.x + this.getBbox().x;
        System.out.println(inX);

        // System.out.println(mouse.getPosition().x + ", " + mouse.getPosition().x);
        
        // System.out.println(mouse.getMousePosUI().x);
    }
    
}
