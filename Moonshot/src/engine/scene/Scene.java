package engine.scene;

import engine.*;
import engine.graph.lighting.SceneLight;
import engine.ui.Canvas;

public interface Scene {
    public Entity[] getEntities();
    public Canvas getCanvas();
    public SceneLight getSceneLight();

    public void init(Window window) throws Exception;
    public void input(Window window, Mouse mouseInput);
    public void update(float interval, Mouse mouseInput);
    public void cleanup();
}
