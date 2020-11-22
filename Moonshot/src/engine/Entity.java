package engine;

import org.joml.Vector3f;

import engine.graph.Mesh;

public class Entity {
    private Mesh mesh;

    private final Vector3f position;

    private float scale;

    private final Vector3f rotation;

    public Entity() {
        position = new Vector3f();
        scale = 1;
        rotation = new Vector3f();
    }

    public Entity(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vector3f pos) {
        this.position.x = pos.x;
        this.position.y = pos.y;
        this.position.z = pos.z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void setRotation(Vector3f rot) {
        this.rotation.x = rot.x;
        this.rotation.y = rot.y;
        this.rotation.z = rot.z;
    }

    public Mesh getMesh() {
        return mesh;
    }
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}