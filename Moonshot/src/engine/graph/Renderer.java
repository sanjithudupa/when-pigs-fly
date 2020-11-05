package engine.graph;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import engine.Entity;
import engine.Utils;
import engine.Window;
import engine.graph.Camera;
import engine.graph.Mesh;
import engine.graph.ShaderProgram;
import engine.graph.Transformation;
import engine.ui.Canvas;

public class Renderer {
  
    private int vboId;

    private int vaoId;

    private ShaderProgram sceneShaderProgram;
    private ShaderProgram uiShaderProgram;

    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private Matrix4f projectionMatrix;

    private Transformation transformation;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        setupSceneShader();
        setupUIShader();

        window.setClearColor(0.25f, 0.5f, 0.0f, 0.0f);
    }

    public void setupSceneShader() throws Exception {
        sceneShaderProgram = new ShaderProgram();
        sceneShaderProgram.createVertexShader(Utils.readFile("Moonshot/src/resources/shaders/vertex.vs"));
        sceneShaderProgram.createFragmentShader(Utils.readFile("Moonshot/src/resources/shaders/frag.fs"));
        sceneShaderProgram.link();

        sceneShaderProgram.createUniform("projectionMatrix");
        sceneShaderProgram.createUniform("modelViewMatrix");
        sceneShaderProgram.createUniform("texture_sampler");
        sceneShaderProgram.createUniform("color");
        sceneShaderProgram.createUniform("useColor");
    }

    private void setupUIShader() throws Exception {
        uiShaderProgram = new ShaderProgram();
        uiShaderProgram.createVertexShader(Utils.readFile("Moonshot/src/resources/shaders/ui_vertex.vs"));
        uiShaderProgram.createFragmentShader(Utils.readFile("Moonshot/src/resources/shaders/ui_fragment.fs"));
        uiShaderProgram.link();

        // Create uniforms for Ortographic-model projection matrix and base colour
        uiShaderProgram.createUniform("projModelMatrix");
        uiShaderProgram.createUniform("color");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Camera camera, Entity[] entities, Canvas ui) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        renderScene(window, camera, entities);
        renderUI(window, ui);
    }

    public void renderScene(Window window, Camera camera, Entity[] entities){

        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);

        sceneShaderProgram.bind();
        sceneShaderProgram.setUniform("projectionMatrix", projectionMatrix);

        sceneShaderProgram.setUniform("texture_sampler", 0);

        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        // Render each entity
        for(Entity entity : entities) {
            // Set world matrix for this item
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(entity, viewMatrix);
            
            sceneShaderProgram.setUniform("modelViewMatrix", modelViewMatrix);

            sceneShaderProgram.setUniform("color", entity.getMesh().getColor());
            sceneShaderProgram.setUniform("useColor", entity.getMesh().isTextured() ? 0 : 1);

            entity.getMesh().render();
        }

        sceneShaderProgram.unbind();
    }

    private void renderUI(Window window, Canvas ui) {
        uiShaderProgram.bind();

        Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
        for (Entity entity : ui.getEntities()) {
            Mesh mesh = entity.getMesh();
            // Set ortohtaphic and model matrix for this HUD item
            Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(entity, ortho);
            uiShaderProgram.setUniform("projModelMatrix", projModelMatrix);
            uiShaderProgram.setUniform("color", new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));

            // Render the mesh for this HUD item
            mesh.render();
        }

        uiShaderProgram.unbind();
    }

    public void cleanup() {
        if (sceneShaderProgram != null) {
            sceneShaderProgram.cleanup();
        }
        if (uiShaderProgram != null) {
            uiShaderProgram.cleanup();
        }
    }
}