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

public class TestScene implements Scene {

    // required elements
    private Entity[] entities;
    private SceneLight sceneLight;
    private Canvas canvas;
    private Camera camera;

    // scene objects
    Entity pig, pig2, pig3;

    // scene variables
    private final Vector3f cameraMotion;
    private Vector2f rotVec = new Vector2f(0, 0);

    // private float position = 8.92f;

    public TestScene(Camera camera) {
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
        Mesh pigMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/pig.obj");
        Texture pigTexture = new Texture("Moonshot/src/resources/textures/Tex_Pig.png");

        pigMesh.setMaterial(new Material(pigTexture));
        pig = new Entity(pigMesh);
        // pig2 = new Entity(pigMesh);
        // pig3 = new Entity(pigMesh);
        // // pig.setRotation(180, 0, 0);

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
        int textInc = 1;
        Entity terrain = new Terrain(terrainSize, terrainScale, minY, maxY,
                "Moonshot/src/resources/textures/scale_hm.jpg",
                "Moonshot/src/resources/textures/softgreen.png", textInc).getEntities()[0];

        Entity terrain2 = new Terrain(terrainSize, terrainScale, minY, maxY,
        "Moonshot/src/resources/textures/scale_hm_i.jpg",
        "Moonshot/src/resources/textures/softgreen.png", textInc).getEntities()[0];

        terrain2.setPosition(terrainScale, 0, 0);

        // List<Entity> allEntities = new ArrayList<Entity>();
        
        // for(Entity e : terrain.getEntities()){
        //     allEntities.add(e);
        // }

        // for(Entity e : terrain2.getEntities()){
        //     e.setPosition(10, 0, 0);
        //     allEntities.add(e);
        // }

        // System.out.println(allEntities.size());

        entities = new Entity[] { pig, terrain, terrain2 };

        canvas = new TestCanvas();

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

        // if (window.isKeyPressed(GLFW_KEY_U)) {
        //     position += 0.01;
        // } else if (window.isKeyPressed(GLFW_KEY_Y)) {
        //     position -= 0.01;
        // }

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

        // pig2.setPosition(position, 0, 0);
        // pig3.setPosition(-position, 0, 0);

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
