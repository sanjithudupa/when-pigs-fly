package engine.ui;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.Entity;

public class UIElement extends Entity {
    private Vector2f offset = new Vector2f(0.0f, 0.0f);
    private boolean centered;
    private Vector2f bbox;

    public Vector2f getOffset() {
        return offset;
    }

    public void setOffset(float x, float y) {
        this.offset = new Vector2f(x, y);
    }

    public Vector2f getBbox() {
        return bbox;
    }

    public void setBbox(Vector2f bbox) {
        this.bbox = bbox;
    }
    
    public boolean isCentered() {
        return centered;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }
}
