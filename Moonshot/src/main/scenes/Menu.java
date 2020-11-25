package main.scenes;

import org.joml.Vector3f;

import engine.Entity;
import engine.Mouse;
import engine.Window;
import engine.graph.Camera;
import engine.graph.Material;
import engine.graph.Mesh;
import engine.graph.ModelLoader;
import engine.graph.Texture;
import engine.graph.lighting.DirectionalLight;
import engine.graph.lighting.SceneLight;
import engine.scene.Scene;
import engine.ui.Canvas;
import main.canvases.MenuCanvas;

public class Menu implements Scene {

    // required elements
    private Entity[] entities;
    private SceneLight sceneLight;
    private Canvas canvas;
    private Camera camera;

    // scene objects
    Entity logo, pig, glider;

    int direction = 1;

    public Menu(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void init(Window window) throws Exception {
        this.canvas = new MenuCanvas();
        this.sceneLight = new SceneLight();

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 0.5f;
        Vector3f lightPosition = new Vector3f(0, 90, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
        
        Mesh logoMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/logo.obj");
        Texture logoTexture = new Texture("Moonshot/src/resources/textures/logo_texture.png");

        logoMesh.setMaterial(new Material(logoTexture));
        logo = new Entity(logoMesh);

        logo.setRotation(80, 180, 180);
        logo.setPosition(0, 1.5f, -5);
        logo.setScale(0.8f);

        Mesh gliderMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/glider.obj");

        Mesh pigMesh = ModelLoader.loadMesh("Moonshot/src/resources/models/pig.obj");
        Texture pigTexture = new Texture("Moonshot/src/resources/textures/pig.png");

        gliderMesh.setMaterial(new Material());
        glider = new Entity(gliderMesh);

        pigMesh.setMaterial(new Material(pigTexture));
        pig = new Entity(pigMesh);

        camera.setPosition(0, 0, 0);
        
        pig.setPosition(0, -2.5f, -5);
        // pig.setRotation(-20, 0, 0);

        this.entities = new Entity[] { pig, glider };
    }

    @Override
    public void input(Window window, Mouse mouseInput) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(float interval, Mouse mouseInput) {
        logo.getRotation().z += 0.15;

        System.out.println(pig.getRotation().y);

        if(pig.getRotation().y >= 90 || pig.getRotation().y <= -40)
            direction *= -1;
        
        pig.getRotation().y += direction * 0.1;
        pig.getRotation().x -= direction * 0.05;
        pig.getRotation().z -= direction * 0.025;

        Vector3f pigRot = pig.getRotation();

        glider.setPosition(pig.getPosition());
        glider.setRotation(new Vector3f(pigRot.x+22.5f, pigRot.y + 180, -pigRot.z));
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

    @Override
    public void cleanup() {
        for (Entity entity : entities) {
            entity.getMesh().cleanUp();
        }
    }
    
}
