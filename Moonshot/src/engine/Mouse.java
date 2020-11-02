package engine;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

public class Mouse {
    private Vector2d previousPos;
    private Vector2d currentPos;
    private Vector2f displVec;

    private GLFWCursorPosCallback posCallback;

    private boolean inWindow = true;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;

    public Mouse(){
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();

    }

    public void init(Window window) {
        
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    public void input(Window window) {

        leftButtonPressed = glfwGetMouseButton(window.getWindowHandle(), GLFW_MOUSE_BUTTON_1) == 1 ? true : false;
        rightButtonPressed = glfwGetMouseButton(window.getWindowHandle(), GLFW_MOUSE_BUTTON_2) == 1 ? true : false;
        
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(window.getWindowHandle(), x, y);
        x.rewind();
        y.rewind();

        currentPos.x = x.get();
        currentPos.y = y.get();

        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }

        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
}