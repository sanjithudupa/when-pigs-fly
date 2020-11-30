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

import static org.lwjgl.glfw.GLFW.*;

public class Menu implements Scene {

    // required elements
    private Entity[] entities;
    private SceneLight sceneLight;
    private MenuCanvas canvas;
    private Camera camera;

    // scene objects
    Entity pig, glider, barn, tractor;

    int pigDir = 1;
    int tractorDir = -1;

    boolean optionsShowing = false;
    boolean atTop = false;
    int count = 0;

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
        Vector3f lightPosition = new Vector3f(90, 0, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity)); 

        Mesh gliderMesh = ModelLoader.loadMesh("res/models/glider.obj");

        Mesh pigMesh = ModelLoader.loadMesh("res/models/pig.obj");
        Texture pigTexture = new Texture("res/textures/pig.png");

        Mesh barnMesh = ModelLoader.loadMesh("res/models/barn.obj");
        Texture barnTexture = new Texture("res/textures/barn.png");

        Mesh tractorMesh = ModelLoader.loadMesh("res/models/tractor.obj");
        Texture tractorTexture = new Texture("res/textures/tractor.png");

        gliderMesh.setMaterial(new Material());
        glider = new Entity(gliderMesh);

        pigMesh.setMaterial(new Material(pigTexture));
        pig = new Entity(pigMesh);

        barnMesh.setMaterial(new Material(barnTexture));
        barn = new Entity(barnMesh);

        tractorMesh.setMaterial(new Material(tractorTexture));
        tractor = new Entity(tractorMesh);

        camera.setPosition(0, 0, 0);
        
        pig.setPosition(-2.5f, -1, -5);

        tractor.setPosition(2.5f, -1, -5);
        tractor.setScale(0.5f);

        barn.setPosition(0, -2, -10);
        barn.setRotation(0, 35, 0);
        // pig.setRotation(-20, 0, 0);

        this.entities = new Entity[] { pig, glider, barn, tractor };
    }

    @Override
    public void input(Window window, Mouse mouseInput) {
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            count = 0;
            optionsShowing = true;
        }
    }

    @Override
    public void update(float interval, Mouse mouseInput) {
        if(pig.getRotation().y >= 90 || pig.getRotation().y <= -40)
            pigDir *= -1;
        
        if(tractor.getRotation().y >= 90 || tractor.getRotation().y <= -40)
            tractorDir *= -1;
        
        pig.getRotation().y += pigDir * 0.1;
        pig.getRotation().x -= pigDir * 0.05;
        pig.getRotation().z -= pigDir * 0.025;

        tractor.getRotation().y += tractorDir * 0.1;
        tractor.getRotation().x -= tractorDir * 0.05;
        tractor.getRotation().z -= tractorDir * 0.025;

        barn.getRotation().y += 0.025;

        Vector3f pigRot = pig.getRotation();

        glider.setPosition(pig.getPosition());
        glider.setRotation(new Vector3f(pigRot.x+22.5f, pigRot.y + 180, -pigRot.z));
        
        if(optionsShowing) {
            canvas.moveOptions(20, count, atTop);
            if(count > 20) {
                atTop = !atTop;
                optionsShowing = false;
            }
        }

        // System.out.println(count + ", " + optionsShowing);

        // System.out.println(count + ", " + optionsShowing);

        count++;
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
