package main.scenes;

import org.joml.Vector3f;

import engine.Entity;
import engine.Mouse;
import engine.Window;
import engine.graph.*;
import engine.graph.lighting.DirectionalLight;
import engine.graph.lighting.SceneLight;
import engine.scene.Scene;
import engine.ui.Canvas;
import main.canvases.SummaryCanvas;

public class Summary implements Scene {

    // required elements
    private Entity[] entities;
    private SceneLight sceneLight;
    private SummaryCanvas canvas;
    private Camera camera;

    // scene objects
    private Entity pig, barn, tractor;
    private int count = 0;

    public Summary(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void init(Window window) throws Exception {
        camera.setPosition(0, 0, 0);

        Mesh barnMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/barn.obj");
        Texture barnTexture = new Texture("Moonshot/src/resources/textures/barn.png");

        barnMesh.setMaterial(new Material(barnTexture));
        barn = new Entity(barnMesh);

        barn.setPosition(0, -2, -10);
        barn.setRotation(0, 35, 0);

        this.sceneLight = new SceneLight();

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightPosition = new Vector3f(1, 1, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
        
        this.canvas = new SummaryCanvas();
        this.entities = new Entity[] { barn };
    }

    @Override
    public void input(Window window, Mouse mouseInput) {

    }

    @Override
    public void update(float interval, Mouse mouseInput) {
        barn.getRotation().y += 0.025;
        this.canvas.delayShow(30, count);
        count++;
    }

    public void setDistance(int distance) {

    }

    @Override
    public void cleanup() {
        for (Entity entity : entities) {
            entity.getMesh().cleanUp();
        }
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
    public SceneLight getSceneLight() {
        return sceneLight;
    }
    
}
