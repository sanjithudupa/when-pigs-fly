package engine.ui;

import org.joml.Vector3f;

import engine.Entity;
import engine.Window;

public interface Canvas {
    UIElement[] getEntities();

    default void cleanup(){
        Entity[] entities = getEntities();
        for(Entity entity : entities){
            entity.getMesh().cleanUp();
        }
    }

    default public void update(Window window){
        
    }
}
