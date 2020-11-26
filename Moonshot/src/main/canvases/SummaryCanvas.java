package main.canvases;

import engine.Mouse;
import engine.ui.*;

public class SummaryCanvas implements Canvas {

    private final UIElement[] elements;
    private final Text distance;
    private final Image pigs, cant, fly, rToReturn;

    private final float moveAmount = 55;

    public SummaryCanvas() throws Exception {
        pigs = new Image("res/textures/ui/pcfpigs.png");
        pigs.setScale(1.5f);
        pigs.setCentered(true);
        pigs.setOffset(-pigs.getWidth()/2 + moveAmount, -540 + pigs.getHeight()/2);
        pigs.setOpacity(0);

        cant = new Image("res/textures/ui/pcfcant.png");
        cant.setScale(1.5f);
        cant.setCentered(true);
        cant.setOffset(-cant.getWidth()/2 + moveAmount, -540 + 2*pigs.getHeight());
        cant.setOpacity(0);

        fly = new Image("res/textures/ui/pcffly.png");
        fly.setScale(1.5f);
        fly.setCentered(true);
        fly.setOffset(-fly.getWidth()/2 + moveAmount, -540 + 2*pigs.getHeight() + cant.getHeight() + 50);
        fly.setOpacity(0);

        distance = new Text("25");
        distance.setScale(2.5f);
        distance.setCentered(true);
        distance.setOpacity(0);
        distance.setOffset(-30, 0);

        rToReturn = new Image("res/textures/ui/return.png");
        rToReturn.setScale(1.5f);
        rToReturn.setCentered(true);
        rToReturn.setOffset(-rToReturn.getWidth()/2 + 110, 540 - rToReturn.getHeight() - 50);
        rToReturn.setOpacity(0);

        elements = new UIElement[] { distance, pigs, cant, fly, rToReturn };
    }
    
    public void setDistance(int d) {
        distance.setText(d + "ft");
        distance.setOffset(-15 * (Integer.toString(d).length() + 3), 0);
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
            distance.setOpacity(opacity - 3);
        }else if(opacity <= 5) {
            rToReturn.setOpacity(opacity - 4);
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
