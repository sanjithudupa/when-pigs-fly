package main.scenes;

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
    Entity pig, glider, terrain, terrain2;
    Terrain terrainObj, terrainObj2;

    //terrain config
    private final float terrainScale = 500;
    private final float terrainHalfLength = terrainScale/2;
    private final int terrainSize = 1;
    private final float minY = -0.1f;
    private final float maxY = 0.3f;
    private final int textInc = 20;

    // scene variables
    private Vector3f cameraOffset = new Vector3f(0, 5, -5);
    private float movementSpeed = 0.15f;

    private Vector3f movement;
    private float yRot;
    private float dive = -0.4f;
    private float gravity = 0.5f;

    public Flying(Camera camera) {
        this.camera = camera;
        this.movement = new Vector3f(0, 0, movementSpeed);
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

        // Mesh cubeMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/cube.obj");
        // Texture cubeTexture = new Texture("Moonshot/src/resources/textures/texture.png");

        gliderMesh.setMaterial(new Material());
        glider = new Entity(gliderMesh);

        pigMesh.setMaterial(new Material(pigTexture));
        pig = new Entity(pigMesh);

        // cubeMesh.setMaterial(new Material(cubeTexture));
        // cube = new Entity(cubeMesh);

        this.sceneLight = new SceneLight();

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(1, 1, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
        
        terrainObj = new Terrain(terrainSize, terrainScale, minY, maxY,
                "Moonshot/src/resources/textures/height_maps/scale_hm.jpg",
                "Moonshot/src/resources/textures/grass.jpeg", textInc);

        terrainObj2 = new Terrain(terrainSize, terrainScale, minY, maxY,
        "Moonshot/src/resources/textures/height_maps/scale_hm_i.jpg",
        "Moonshot/src/resources/textures/grass.jpeg", textInc);
        
        terrain = terrainObj.getEntities()[0];
        terrain2 = terrainObj2.getEntities()[0];

        terrain.getRotation().y = 90;
        terrain2.getRotation().y = 90;
        terrain2.setPosition(0, 0, terrainScale);

        glider.setRotation(-20, 180, 0);
        glider.setScale(1.15f);

        pig.setRotation(-40, 0, 0);

        entities = new Entity[] { pig, glider, terrain, terrain2 };

        canvas = new TestCanvas();
    }

    @Override
    public void input(Window window, Mouse mouseInput) {
        if (window.isKeyPressed(GLFW_KEY_W)) {
            pig.getRotation().x -= 1;
            dive -= 0.015;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            pig.getRotation().x += 1;
            if(dive < 0)
                dive += 0.01;
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            pig.getRotation().z += 1;
            yRot -= 1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            pig.getRotation().z -= 1;
            yRot += 1;
        }
    }

    @Override
    public void update(float interval, Mouse mouseInput) {
        Vector3f pigRot = pig.getRotation();

        movementSpeed = (90 - pig.getRotation().x)/90;
        
        boolean scannedTerrain =  ((int)(pig.getPosition().z/(terrainScale/2))) % 2 == 0;
        float zPos = pig.getPosition().z;

        float normalizedZ = zPos/terrainHalfLength;
        float adjustZ = normalizedZ - (int)normalizedZ;
        float newZ = ((terrainScale * adjustZ)/2);

        Vector3f adjustedPigPos = new Vector3f(pig.getPosition().x, pig.getPosition().y, newZ);

        float groundPos = (scannedTerrain ? terrainObj : terrainObj2).getHeight(adjustedPigPos);

        // cube.setPosition(adjustedPigPos);

        boolean collided = pig.getPosition().y -10 <= groundPos;
        // System.out.println(pig.getPosition().y);
        System.out.println(adjustedPigPos.x + ", " + adjustedPigPos.y + ", " + adjustedPigPos.z);

        movement.x = -movementSpeed * (float)Math.sin(Math.toRadians(yRot));
        movement.z = movementSpeed * (float)Math.cos(Math.toRadians(yRot));
        movement.y = movementSpeed * (float)Math.cos(Math.toRadians(90 - pigRot.x)) + dive - gravity;
        
        pig.getPosition().x += movement.x;
        pig.getPosition().y += movement.y;
        pig.getPosition().z += movement.z;

        Vector3f pigPos = pig.getPosition();

        camera.setPosition(new Vector3f(pigPos.x, pigPos.y + cameraOffset.y, pigPos.z + cameraOffset.z));

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