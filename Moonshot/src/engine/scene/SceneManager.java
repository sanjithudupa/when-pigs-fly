package engine.scene;

import engine.Window;

public class SceneManager {
    private Scene[] scenes;
    private int index = 0;

    private Window window;

    public void init(Scene[] scenes, Window window) throws Exception {
        this.scenes = scenes;
        this.window = window;
        loadScene(0);
    }

    public void loadScene(int index) throws Exception {
        this.index = index;
        scenes[0].init(window);
    }

    public Scene getActiveScene(){
        return scenes[0]; 
    }

    public void cleanup(){
        for(Scene scene : scenes){
            scene.cleanup();
        }
    }

    // public void getScene()

}
