package main;

import engine.GameLogic;
import engine.Mouse;
import engine.Timer;
import engine.Window;
import engine.graph.Camera;
import engine.scene.*;
import main.canvases.*;
import main.scenes.*;
import engine.graph.Renderer;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements GameLogic {

    private final Camera camera;
    private final Renderer renderer;
    private final Timer timer;

    private final SceneManager sceneManager;

    private Overlay overlay;

    private float transitionStart;
    private float transitionTime = 1.0f;
    private boolean transitioning = false;

    public Game() {
        renderer = new Renderer();
        camera = new Camera();
        timer = new Timer();

        sceneManager = new SceneManager();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        Scene[] scenes = new Scene[] { new TestScene(camera), new TestScene2(camera) };
        overlay = new Overlay();

        sceneManager.init(scenes, window);

        timer.init();
    }

    @Override
    public void input(Window window, Mouse mouseInput) throws Exception {

        if (window.isKeyPressed(GLFW_KEY_R) && !transitioning) {
            transitionStart = timer.getTimePassed();
            transitioning = true;
        }

        sceneManager.getActiveScene().input(window, mouseInput);

    }

    boolean switched = false;

    @Override
    public void update(float interval, Mouse mouseInput) {
        sceneManager.getActiveScene().update(interval, mouseInput);

        if (transitioning) {
            float opacity = (timer.getTimePassed() - transitionStart) / (transitionTime);
            if (opacity < transitionTime) {
                overlay.setOpacity(opacity);
            }else if(opacity <= transitionTime*2){
                if(!switched){
                    try {
                        sceneManager.loadScene(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    switched = true;
                }
                overlay.setOpacity(2 - (opacity));
            }else{
                overlay.setOpacity(0.0f);
                transitioning = true;
            }
        }

    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, sceneManager.getActiveScene().getEntities(), sceneManager.getActiveScene().getCanvas(), overlay);
        sceneManager.getActiveScene().getCanvas().update(window);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        sceneManager.cleanup();
    }
}