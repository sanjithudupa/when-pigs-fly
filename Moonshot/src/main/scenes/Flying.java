package main.scenes;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Entity;
import engine.Mouse;
import engine.Terrain;
import engine.Window;
import engine.graph.*;
import engine.graph.lighting.DirectionalLight;
import engine.graph.lighting.SceneLight;
import engine.scene.Scene;
import engine.ui.Canvas;
import main.canvases.TestCanvas;

import static org.lwjgl.glfw.GLFW.*;

public class Flying implements Scene {

    // required elements
    private Entity[] entities;
    private SceneLight sceneLight;
    private Canvas canvas;
    private Camera camera;

    // scene objects
    Entity pig, glider;

    // scene variables
    private Vector3f cameraOffset = new Vector3f(0, 5, -5);
    private Vector3f movement;

    // private float position = 8.92f;

    public Flying(Camera camera) {
        this.camera = camera;
        this.movement = new Vector3f(0, 0, 0.25f);
        // this.cameraMotion = new Vector3f();
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
        Mesh gliderMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/glider.obj");

        Mesh pigMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/pig.obj");
        Texture pigTexture = new Texture("Moonshot/src/resources/textures/pig.png");

        gliderMesh.setMaterial(new Material());
        glider = new Entity(gliderMesh);

        pigMesh.setMaterial(new Material(pigTexture));
        pig = new Entity(pigMesh);

        this.sceneLight = new SceneLight();

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(1, 1, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));

        float terrainScale = 50;
        int terrainSize = 1;
        float minY = -0.1f;
        float maxY = 0.3f;
        int textInc = 20;
        Entity terrain = new Terrain(terrainSize, terrainScale, minY, maxY,
                "Moonshot/src/resources/textures/height_maps/scale_hm.jpg",
                "Moonshot/src/resources/textures/grass.jpeg", textInc).getEntities()[0];

        Entity terrain2 = new Terrain(terrainSize, terrainScale, minY, maxY,
        "Moonshot/src/resources/textures/height_maps/scale_hm_i.jpg",
        "Moonshot/src/resources/textures/grass.jpeg", textInc).getEntities()[0];

        terrain2.setPosition(terrainScale, 0, 0);

        glider.setRotation(20, 180, 0);
        glider.setScale(1.15f);

        entities = new Entity[] { pig, glider, terrain, terrain2 };

        canvas = new TestCanvas();

        // camera.setPosition(0, 0, 0);
    }

    @Override
    public void input(Window window, Mouse mouseInput) {
        if (window.isKeyPressed(GLFW_KEY_W)) {
            pig.getRotation().x -= 1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            pig.getRotation().x += 1;
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            pig.getRotation().z += 1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            pig.getRotation().z -= 1;
        }

        // if (window.isKeyPressed(GLFW_KEY_Q)) {
        //     pig.getPosition().y += -0.1;
        // } else if (window.isKeyPressed(GLFW_KEY_E)) {
        //     pig.getPosition().y += 0.1;
        // }

        // if (window.isKeyPressed(GLFW_KEY_U)) {
        //     pig.getPosition().x += 0.1;
        // } else if (window.isKeyPressed(GLFW_KEY_Y)) {
        //     pig.getPosition().x -= 0.1;
        // }

        // if (window.isKeyPressed(GLFW_KEY_X)) {
        // } else if (window.isKeyPressed(GLFW_KEY_V)) {
        //     pig.getRotation().x -= 1;
        // }

        // if (mouseInput.isRightButtonPressed())
        //     rotVec = mouseInput.getDisplVec();
        // else
        //     rotVec = new Vector2f(0, 0);

    }

    @Override
    public void update(float interval, Mouse mouseInput) {
        pig.getPosition().x += movement.x;
        pig.getPosition().y += movement.y;
        pig.getPosition().z += movement.z;

        Vector3f pigPos = pig.getPosition();
        Vector3f pigRot = pig.getRotation();

        camera.setPosition(new Vector3f(0, pigPos.y + cameraOffset.y, pigPos.z + cameraOffset.z));

        Vector3f cameraPos = camera.getPosition();

        float cameraXLook = (float)Math.toDegrees(Math.atan2(pigPos.z - cameraPos.z, cameraPos.y - pigPos.y));
        
        camera.setRotation(cameraXLook, 180, 0);

        glider.setPosition(pig.getPosition());
        glider.setRotation(new Vector3f(pigRot.x + 20, pigRot.y + 180, -pigRot.z));
        
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
        return this.sceneLight;
    }
    
}
