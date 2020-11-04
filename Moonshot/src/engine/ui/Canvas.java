package engine.ui;

import engine.Entity;

public interface Canvas {
    Entity[] getEntities();

    default void cleanup(){
        Entity[] entities = getEntities();
        for(Entity entity : entities){
            entity.getMesh().cleanUp();
        }
    }
}
