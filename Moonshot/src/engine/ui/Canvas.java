package engine.ui;

import org.joml.Vector2f;

import engine.Entity;
import engine.Mouse;
import engine.Window;

public interface Canvas {
    UIElement[] getElements();

    default void cleanup(){
        Entity[] entities = getElements();
        for(Entity entity : entities){
            entity.getMesh().cleanUp();
        }
    }

    public void input(Mouse input);

    default public void update(Window window) {
        for(UIElement element : getElements()) {
            Vector2f position = new Vector2f();
            Vector2f offset = element.getOffset();

            if(element.isPercentage()) {
                offset.x = (element.getOffset().x * window.getWidth());
                offset.y = (element.getOffset().y * window.getHeight());
            }

            position.x = (element.isCentered() ? window.getWidth()/2 - element.getBbox().x/2 : 0) + (element.isCentered() ? offset.x : element.getPosition().x);
            position.y = (element.isCentered() ? window.getHeight()/2 - element.getBbox().y/2 : 0) + (element.isCentered() ? offset.y : element.getPosition().y);
            
            if(element.isCentered()) {
                element.setPosition(position.x, position.y, 0.0f);
            }
        }
    }

    default public void setOpacity(float opacity){ }
}
