package de.themdplays.background;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class BackgroundObject {

    public Vector2 pos;
    public float speedMultiplier;

    protected abstract void render(SpriteBatch batch);

}
