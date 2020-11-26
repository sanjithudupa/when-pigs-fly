package main.canvases;

import org.joml.Vector4f;

import engine.Mouse;
import engine.ui.*;

public class SummaryCanvas implements Canvas {

    private final UIElement[] elements;
    private final Text text;
    private final Image pigs, cant, fly;

    private final float moveAmount = 55;

    public SummaryCanvas() throws Exception {
        pigs = new Image("Moonshot/src/resources/textures/ui/pcfpigs.png");
        pigs.setScale(1.5f);
        pigs.setCentered(true);
        pigs.setOffset(-pigs.getWidth()/2 + moveAmount, -540 + pigs.getHeight()/2);
        pigs.setOpacity(0);

        cant = new Image("Moonshot/src/resources/textures/ui/pcfcant.png");
        cant.setScale(1.5f);
        cant.setCentered(true);
        cant.setOffset(-cant.getWidth()/2 + moveAmount, -540 + 2*pigs.getHeight());
        cant.setOpacity(0);

        fly = new Image("Moonshot/src/resources/textures/ui/pcffly.png");
        fly.setScale(1.5f);
        fly.setCentered(true);
        fly.setOffset(-fly.getWidth()/2 + moveAmount, -540 + 2*pigs.getHeight() + cant.getHeight() + 50);
        fly.setOpacity(0);

        text = new Text("25");
        text.setScale(2.5f);
        text.setCentered(true);
        text.setOpacity(0);
        
        elements = new UIElement[] { text, pigs, cant, fly};
    }
    
    public void setDistance(int distance) {
        text.setText(distance + " ft");
    }

    public void delayShow(float single, float curr) {
        float opacity = (curr) / (single);
        
        if(opacity <= 1) {
            pigs.setOpacity(opacity);
        }else if(opacity <= 2) {
            cant.setOpacity(opacity - 1);
        }else if(opacity <= 3) {
            fly.setOpacity(opacity - 2);
        }else if(opacity <= 4) {
            text.setOpacity(opacity - 3);
        }
    }

    @Override
    public UIElement[] getElements() {
        return elements;
    }

    @Override
    public void input(Mouse input) {
        for(UIElement element : getElements()){
            element.input(input);
        }
    }
    
}
