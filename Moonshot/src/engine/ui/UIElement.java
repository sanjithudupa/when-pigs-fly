package engine.ui;

import org.joml.Vector2f;

import engine.Entity;
import engine.Mouse;

public abstract class UIElement extends Entity {
    public float ZPOS = 0.1f;
    private Vector2f offset = new Vector2f(0.0f, 0.0f);

    private float opacity = 1.0f;

    private boolean centered;
    private boolean percentage;

    UIElement(){

    }

    protected UIElement(float ZPOS) {
        this.ZPOS = ZPOS;
    }

    public Vector2f getOffset() {
        return offset;
    }

    public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public void setOffset(float x, float y) {
        this.offset = new Vector2f(x, y);
    }

    public abstract Vector2f getBbox();
    
    public boolean isCentered() {
        return centered;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public abstract void input(Mouse mouse);
}
