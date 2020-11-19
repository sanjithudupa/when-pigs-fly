package main.scenes;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Entity;
import engine.Mouse;
import engine.Window;
import engine.graph.*;
import engine.graph.lighting.SceneLight;
import engine.scene.Scene;
import engine.ui.Canvas;
import main.canvases.*;

import static org.lwjgl.glfw.GLFW.*;

public class TestScene2 implements Scene {

    // required elements
    private Entity[] entities;
    private Canvas canvas;
    private Camera camera;

    // scene objects
    Entity pig;

    // scene variables
    private final Vector3f cameraMotion;
    private Vector2f rotVec = new Vector2f(0, 0);

    public TestScene2(Camera camera) {
        this.camera = camera;
        this.cameraMotion = new Vector3f();
        // timer = new Timer();
    }

    @Override
    public Entity[] getEntities() {
        return entities;
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void init(Window window) throws Exception {
        // Mesh pigMesh =
        // ModelLoader.loadMesh("Moonshot/src/resources/models/cube.obj");
        // Texture pigTexture = new
        // Texture("Moonshot/src/resources/textures/texture.png");

        // pigMesh.setTexture(pigTexture);
        // pig = new Entity(pigMesh);

        entities = new Entity[] {};

        canvas = new TestCanvas2();

        // camera.setPosition(0, 0, 0);
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

    @Override
    public void update(float interval, Mouse mouseInput) {
        camera.movePosition(cameraMotion.x * Mouse.CAMERA_POS_STEP, cameraMotion.y * Mouse.CAMERA_POS_STEP,
                cameraMotion.z * Mouse.CAMERA_POS_STEP);
        camera.moveRotation(rotVec.x * Mouse.MOUSE_SENSITIVITY, rotVec.y * Mouse.MOUSE_SENSITIVITY, 0);

        canvas.input(mouseInput);
    }

    @Override
    public void cleanup() {
        for (Entity entity : entities) {
            entity.getMesh().cleanUp();
        }
    }

    @Override
    public SceneLight getSceneLight() {
        return null;
    }
    
}
