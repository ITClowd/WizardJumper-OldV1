package de.themdplays.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Moritz on 03.01.2017.
 */
public class SpriteActor extends Actor {

    private Sprite sprite;

    public SpriteActor(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                getScaleX(),
                getScaleY(), getRotation());
    }

}
