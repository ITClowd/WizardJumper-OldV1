package de.themdplays.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BackgroundHandler {

    final float parallaxLayer1 = 0.8f, parallaxLayer2 = 0.7f, parallaxLayer3 = 0.6f;

    private List<ParallaxObject> layer1, layer2, layer3;

    public BackgroundHandler() {
        layer1 = new ArrayList<ParallaxObject>();
        layer2 = new ArrayList<ParallaxObject>();
        layer3 = new ArrayList<ParallaxObject>();

    }

    public void render(SpriteBatch batch, float speed) {
//        renderParallax(speed);


    }

//    private void renderParallax(float speed) {
//        for(ParallaxObject object: layer1) {
//            object.pos.setX(object.pos.getX()+speed*parallaxLayer1);
//        }
//
//        for(ParallaxObject object: layer2) {
//            object.pos.setX(object.pos.getX()+speed*parallaxLayer2);
//        }
//
//        for(ParallaxObject object: layer3) {
//            object.pos.setX(object.pos.getX()+speed*parallaxLayer3);
//        }
//    }

}
