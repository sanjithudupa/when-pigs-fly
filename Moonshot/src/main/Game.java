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
    // private static final float MOUSE_SENSITIVITY = 0.2f;
    // private static final float CAMERA_POS_STEP = 0.05f;

    private final Camera camera;
    private final Renderer renderer;
    private final Timer timer;

    private final SceneManager sceneManager;

    private Overlay overlay;
    // private GameCanvas ui;

    // private Entity pig;
    // private Entity[] entities;

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
        // scene.init(window);
        // scene2.init(window);

        // Mesh pigMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/pig.obj");
        // Texture pigTexture = new
        // Texture("Moonshot/src/resources/textures/Tex_Pig.png");

        // pigMesh.setTexture(pigTexture);
        // pig = new Entity(pigMesh);

        // entities = new Entity[]{ pig };

        // ui = new GameCanvas();

        // camera.setPosition(0, 0, 0);

        timer.init();
    }

    @Override
    public void input(Window window, Mouse mouseInput) throws Exception {
        // cameraMotion.set(0, 0, 0);

        // if (window.isKeyPressed(GLFW_KEY_W)) {
        // cameraMotion.z = -1;
        // } else if (window.isKeyPressed(GLFW_KEY_S)) {
        // cameraMotion.z = 1;
        // }

        // if (window.isKeyPressed(GLFW_KEY_A)) {
        // cameraMotion.x = -1;
        // } else if (window.isKeyPressed(GLFW_KEY_D)) {
        // cameraMotion.x = 1;
        // }

        // cameraMotion.y = 1;
        // }

        if (window.isKeyPressed(GLFW_KEY_R) && !transitioning) {
            transitionStart = timer.getTimePassed();
            transitioning = true;
        }

        // if (mouseInput.isRightButtonPressed())
        // rotVec = mouseInput.getDisplVec();
        // else
        // rotVec = new Vector2f(0, 0);

        sceneManager.getActiveScene().input(window, mouseInput);

    }

    boolean switched = false;

    @Override
    public void update(float interval, Mouse mouseInput) {
        sceneManager.getActiveScene().update(interval, mouseInput);
        // camera.movePosition(cameraMotion.x * Mouse.CAMERA_POS_STEP, cameraMotion.y *
        // Mouse.CAMERA_POS_STEP, cameraMotion.z * Mouse.CAMERA_POS_STEP);
        // camera.moveRotation(rotVec.x * Mouse.MOUSE_SENSITIVITY, rotVec.y *
        // Mouse.MOUSE_SENSITIVITY, 0);

        // ui.input(mouseInput);

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

        // iterations++;


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
        // for(Entity entity : entities){
        //     entity.getMesh().cleanUp();
        // }
    }
}