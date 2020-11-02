package main;

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
import org.lwjgl.system.MemoryUtil;

import engine.Entity;
import engine.Utils;
import engine.Window;
import engine.graph.Mesh;
import engine.graph.ShaderProgram;
import engine.graph.Transformation;

public class Renderer {
  
    private int vboId;

    private int vaoId;

    private ShaderProgram shaderProgram;

    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private Matrix4f projectionMatrix;

    private Transformation transformation;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.readFile("Moonshot/src/resources/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.readFile("Moonshot/src/resources/frag.fs"));
        shaderProgram.link();

        // Create projection matrix
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().setPerspective(Renderer.FOV, aspectRatio, Renderer.Z_NEAR, Renderer.Z_FAR);
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
        shaderProgram.createUniform("texture_sampler");

    
        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, Entity[] entities) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());

            // float aspectRatio = (float) window.getWidth() / window.getHeight();
            // projectionMatrix = new Matrix4f().setPerspective(Renderer.FOV, aspectRatio, Renderer.Z_NEAR, Renderer.Z_FAR);
            
            window.setResized(false);
        }

        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);

        shaderProgram.bind();
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        shaderProgram.setUniform("texture_sampler", 0);

        // Render each entity
        for(Entity entity : entities) {
            // Set world matrix for this item
            Matrix4f worldMatrix =
                transformation.getWorldMatrix(
                    entity.getPosition(),
                    entity.getRotation(),
                    entity.getScale());
            shaderProgram.setUniform("worldMatrix", worldMatrix);
            // Render the mes for this game item
            entity.getMesh().render();
        }

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}