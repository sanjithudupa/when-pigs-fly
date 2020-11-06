package engine.scene;

import engine.*;
import engine.ui.Canvas;

public interface Scene {
    public Entity[] getEntities();
    public Canvas getCanvas();

    public void init(Window window);
    public void input(Window window, Mouse mouseInput);
    public void update(float interval, Mouse mouseInput);
    public void render(Window window);
    public void cleanup();
}
