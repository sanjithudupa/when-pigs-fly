package engine.scene;

import engine.Window;

public class SceneManager {
    private Scene[] scenes;
    private int index = 0;

    private Window window;

    public void init(Scene[] scenes, Window window) throws Exception {
        this.scenes = scenes;
        this.window = window;

        for(Scene scene : this.scenes){
            scene.init(window);
        }
        loadScene(0);
    }

    public void loadScene(int index) throws Exception {
        this.index = index;
        
        for(Scene scene : scenes){
            scene.cleanup();
        }

        scenes[index].init(this.window);
    }

    public Scene getActiveScene(){
        return scenes[this.index]; 
    }

    public void cleanup(){
        for(Scene scene : scenes){
            scene.cleanup();
        }
    }

    // public void getScene()

}
