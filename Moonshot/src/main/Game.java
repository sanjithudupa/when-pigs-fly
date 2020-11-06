package main;

import engine.Entity;
import engine.GameLogic;
import engine.Mouse;
import engine.Window;
import engine.graph.Camera;
import engine.graph.Mesh;
import engine.graph.ModelLoader;
import engine.graph.Texture;
import engine.graph.Renderer;

import static org.lwjgl.glfw.GLFW.*;


import org.joml.Vector2f;
import org.joml.Vector3f;

public class Game implements GameLogic {
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float CAMERA_POS_STEP = 0.05f;

    private final Vector3f cameraMotion;
    private Vector2f rotVec = new Vector2f(0, 0);

    private final Camera camera;
    private final Renderer renderer;

    private GameCanvas ui;

    private Entity pig;
    private Entity[] entities;
    
    public Game() {
        renderer = new Renderer();
        camera = new Camera();
        cameraMotion = new Vector3f();
    }
    
    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        
        Mesh pigMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/pig.obj");
        Texture pigTexture = new Texture("Moonshot/src/resources/textures/Tex_Pig.png");

        pigMesh.setTexture(pigTexture);
        pig = new Entity(pigMesh);
        
        entities = new Entity[]{ pig };

        ui = new GameCanvas();

        camera.setPosition(0, 0, 0);
    }
    
    @Override
    public void input(Window window, Mouse mouseInput) {
        cameraMotion.set(0, 0, 0);

        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraMotion.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraMotion.z = 1;
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraMotion.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraMotion.x = 1;
        }

        if (window.isKeyPressed(GLFW_KEY_Q)) {
            cameraMotion.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_E)) {
            cameraMotion.y = 1;
        }

        if (mouseInput.isRightButtonPressed())
            rotVec = mouseInput.getDisplVec();
        else
            rotVec = new Vector2f(0, 0);
        
    }

    // int iterations = 0;
    // boolean growing = true;

    @Override
    public void update(float interval, Mouse mouseInput) {
        camera.movePosition(cameraMotion.x * CAMERA_POS_STEP, cameraMotion.y * CAMERA_POS_STEP, cameraMotion.z * CAMERA_POS_STEP);
        camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);

        ui.input(mouseInput);
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, entities, ui);
        ui.update(window);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for(Entity entity : entities){
            entity.getMesh().cleanUp();
        }
    }
}