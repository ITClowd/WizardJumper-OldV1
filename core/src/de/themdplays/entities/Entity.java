package de.themdplays.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.themdplays.util.Location;

public abstract class Entity {

	Location loc;
	
	public int height, width;

	public Body body;

    public Vector2 velocity = new Vector2();

    public World world;

    //CONSTRUCTOR
    Entity(Location location, int width, int height, World world) {
		this.loc = location;
		this.height = height;
		this.width = width;
		this.world = world;
	}

    /**
     * Sets the location of the entity to loc
     * @param loc
     */
	public void setLoc(Location loc) {
		this.loc = loc;
	}

    /**
     * Returns the location of the entity
     * @return loc
     */
	public Location getLocation() {
		return loc;
	}

	public void render(float delta) {

	}

    /**
     * Returns the width of the hitbox
     * @return width
     */
	public int getWidth() {
		return width;
	}

    /**
     * Returns the height of the hitbox
     * @return height of the hitbox
     */
	public int getHeight() {
		return height;
	}

    void jump() {
        if(isOnGround()) {
            body.applyLinearImpulse(0, 150, body.getWorldCenter().x, body.getWorldCenter().y, true);
        }
    }

	protected abstract Body createBody();

	private boolean isOnGround() {
	    return body.getLinearVelocity().y == 0;
    }

}
