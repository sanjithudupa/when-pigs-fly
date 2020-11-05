package main;

import engine.Entity;
import engine.GameLogic;
import engine.Mouse;
import engine.Timer;
import engine.Window;
import engine.graph.Camera;
import engine.graph.Mesh;
import engine.graph.ModelLoader;
import engine.graph.Texture;
import engine.graph.Renderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;


import org.joml.Vector2f;
import org.joml.Vector3f;

public class Game implements GameLogic {
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float CAMERA_POS_STEP = 0.05f;

    private int direction = 0;
    private float color = 0.0f;

    private final Vector3f cameraInc;
    private final Camera camera;
    private final Renderer renderer;

    private UI ui;

    private Entity[] entities;
    
    public Game() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f();
    }

    Entity entity;
    
    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        // float[] positions = new float[] {
        //     // V0
        //     -0.5f, 0.5f, 0.5f,
        //     // V1
        //     -0.5f, -0.5f, 0.5f,
        //     // V2
        //     0.5f, -0.5f, 0.5f,
        //     // V3
        //     0.5f, 0.5f, 0.5f,
        //     // V4
        //     -0.5f, 0.5f, -0.5f,
        //     // V5
        //     0.5f, 0.5f, -0.5f,
        //     // V6
        //     -0.5f, -0.5f, -0.5f,
        //     // V7
        //     0.5f, -0.5f, -0.5f,
            
        //     // For text coords in top face
        //     // V8: V4 repeated
        //     -0.5f, 0.5f, -0.5f,
        //     // V9: V5 repeated
        //     0.5f, 0.5f, -0.5f,
        //     // V10: V0 repeated
        //     -0.5f, 0.5f, 0.5f,
        //     // V11: V3 repeated
        //     0.5f, 0.5f, 0.5f,

        //     // For text coords in right face
        //     // V12: V3 repeated
        //     0.5f, 0.5f, 0.5f,
        //     // V13: V2 repeated
        //     0.5f, -0.5f, 0.5f,

        //     // For text coords in left face
        //     // V14: V0 repeated
        //     -0.5f, 0.5f, 0.5f,
        //     // V15: V1 repeated
        //     -0.5f, -0.5f, 0.5f,

        //     // For text coords in bottom face
        //     // V16: V6 repeated
        //     -0.5f, -0.5f, -0.5f,
        //     // V17: V7 repeated
        //     0.5f, -0.5f, -0.5f,
        //     // V18: V1 repeated
        //     -0.5f, -0.5f, 0.5f,
        //     // V19: V2 repeated
        //     0.5f, -0.5f, 0.5f,
        // };

        // float[] positions2 = new float[]{
        //     -0.5f,  0.5f, 0.0f,
        //     -0.5f, -0.5f, 0.0f,
        //      0.5f, -0.5f, 0.0f,
        //      0.5f,  0.5f, 0.0f,
        // };
        // int[] indices2 = new int[]{
        //     0, 1, 3, 3, 1, 2,
        // };

        // float[] textCoords = new float[]{
        //     0.0f, 0.0f,
        //     0.0f, 0.5f,
        //     0.5f, 0.5f,
        //     0.5f, 0.0f,
            
        //     0.0f, 0.0f,
        //     0.5f, 0.0f,
        //     0.0f, 0.5f,
        //     0.5f, 0.5f,
            
        //     // For text coords in top face
        //     0.0f, 0.5f,
        //     0.5f, 0.5f,
        //     0.0f, 1.0f,
        //     0.5f, 1.0f,

        //     // For text coords in right face
        //     0.0f, 0.0f,
        //     0.0f, 0.5f,

        //     // For text coords in left face
        //     0.5f, 0.0f,
        //     0.5f, 0.5f,

        //     // For text coords in bottom face
        //     0.5f, 0.0f,
        //     1.0f, 0.0f,
        //     0.5f, 0.5f,
        //     1.0f, 0.5f,
        // };

        // int[] indices = new int[]{
        //     // Front face
        //      0, 1, 3, 3, 1, 2,
        //     // Top Face
        //     8, 10, 11, 9, 8, 11,
        //     // Right face
        //     12, 13, 7, 5, 12, 7,
        //     // Left face
        //     14, 15, 6, 4, 14, 6,
        //     // Bottom face
        //     16, 18, 19, 17, 16, 19,
        //     // Back face
        //     4, 6, 7, 5, 4, 7,
        // };

        // Texture texture = new Texture("Moonshot/src/resources/textures/texture.png");
        // Mesh cube = new Mesh(positions2, textCoords, new float[0], indices2);

        // cube.setTexture(texture);

        // Entity entity = new Entity(cube);
        // entity.setPosition(0, 0, -2);

        // Texture texture2 = new Texture("Moonshot/src/resources/textures/texture2.png");
        // Mesh cube2 = new Mesh(positions, textCoords, indices, texture2);

        // Entity entity1 = new Entity(cube2);
        // entity.setPosition(0, 1, -1);

        //Mesh mesh = OBJLoader.loadMesh("/models/bunny.obj");
        Mesh pig = ModelLoader.loadMesh("Moonshot/src/resources/models/pig.obj");
        Texture pigTexture = new Texture("Moonshot/src/resources/textures/Tex_Pig.png");

        pig.setTexture(pigTexture);
        
        // entity = new Entity(cube);
        Entity pigE = new Entity(pig);

        entities = new Entity[]{ pigE };

        ui = new UI("Moonshot/src/resources/textures/Tex_Pig.png");

        camera.setPosition(0, 2, 0);
    }
    
    @Override
    public void input(Window window, Mouse mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_E)) {
            cameraInc.y = 1;
        }

        // if () {
        //     System.out.println("hi");
        //     // Vector2f rotVec = mouse.getDisplVec();
        //     // camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        // }
    }

    // int iterations = 0;
    // boolean growing = true;

    @Override
    public void update(float interval, Mouse mouseInput) {
         // Update camera position
         camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        
         // Update camera based on mouse            
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            System.out.println(rotVec.x + "," + rotVec.y);
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        // int d = 5;
        // float theta = camera.getRotation().y();

        // float x = (float)(d * Math.toDegrees(Math.sin(theta)));
        // float z = (float)(d * Math.toDegrees(Math.cos(theta)));

        // entity.setRotation(0, camera.getPosition().y, z);
        // entity.setPosition(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z - d);
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, entities, ui);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for(Entity entity : entities){
            entity.getMesh().cleanUp();
        }
    }
}