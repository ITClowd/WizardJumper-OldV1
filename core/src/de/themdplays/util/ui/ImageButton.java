package de.themdplays.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Moritz on 11/04/2017.
 */
public class ImageButton extends Actor {

    private Sprite look;

    public ImageButton(Sprite sprite) {
        this.look = sprite;
        Gdx.input.setInputProcessor(getStage());
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(look, getX(), getY(), getWidth(), getHeight());
    }


    public Sprite getLook() {
        return look;
    }
}
