package engine.scene;

import engine.Timer;
import engine.Window;
import engine.ui.Canvas;

public class SceneManager {
    private Scene[] scenes;
    private int index = 0;

    public static SceneManager instance = null;

    private Window window;
    private boolean switched = false;
    private boolean fixed = false;

    public boolean showFinalScene = false;
    public int distance = 0;

    public void init(Scene[] scenes, Window window) throws Exception {
        this.scenes = scenes;
        this.window = window;

        // for(Scene scene : this.scenes){
        //     scene.init(window);
        // }
        loadScene(0);
    }

    public void loadScene(int index) throws Exception {
        this.index = index;
        
        // for(Scene scene : scenes){
        //     scene.cleanup();
        // }

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

    public void changeScene(int scene, boolean[] changing, float start, float totalTime, Canvas overlay, Timer timer){
        if(!fixed) {
            switched = false;
            fixed = true;
        }
        if (changing[0]) {
            float opacity = (timer.getTimePassed() - start) / (totalTime);
            if (opacity < totalTime) {
                overlay.setOpacity(opacity);
            }else if(opacity <= totalTime*2){
                if(!switched){
                    try {
                        this.loadScene(scene);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    switched = true;
                }
                overlay.setOpacity(2 - (opacity));
            }else{
                overlay.setOpacity(0.0f);
                changing[0] = false;
                fixed = false;
            }
        }
    }

    // public void getScene()

}
