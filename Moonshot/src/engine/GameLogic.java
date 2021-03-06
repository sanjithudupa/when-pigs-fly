package engine;

public interface GameLogic {
    void init(Window window) throws Exception;

    void input(Window window, Mouse mouseInput) throws Exception;

    void update(float interval, Mouse mouseInput);

    void render(Window window);

    void cleanup();
}