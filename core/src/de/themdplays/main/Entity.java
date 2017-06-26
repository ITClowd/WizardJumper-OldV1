package de.themdplays.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import de.themdplays.util.Location;

public class Entity {

	Location loc;
	
	public int height, width;

    private boolean isOnGround = false, XColl = false;

    Vector2 velocity = new Vector2(0, 0);

    private TiledMapTileLayer collisionLayer;

    //CONSTRUCTOR
    Entity(Location location, int width, int height, TiledMapTileLayer collisionLayer) {
		this.loc = location;
		this.height = height;
		this.width = width;
		this.collisionLayer = collisionLayer;
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

		gravity();
		//Collitondetection

		// save old position
		float oldX = loc.getX(), oldY = loc.getY();
		boolean collisionX = false, collisionY = false;
		
		loc.setX(loc.getX() + velocity.x);
		
		if(velocity.x < 0) // going left
			collisionX = collidesLeft();
		else if(velocity.x > 0) // going right
			collisionX = collidesRight();

		// react to x collision
		if(collisionX) {
			loc.setX(oldX);
			velocity.x = 0;
		}

		loc.setY(loc.getY() + velocity.y*Gdx.graphics.getDeltaTime());
		
		if(velocity.y < 0) // going down
			isOnGround = collisionY = collidesBottom();
		else if(velocity.y > 0) // going up
			collisionY = collidesTop();
		
		// react to y collision
		if(collisionY) {
			loc.setY(oldY);
			velocity.y = 0;
		}
		
		//Bounding Collition
		if(loc.getX()<=0) loc.setX(0);
		else if(loc.getX()+width>=collisionLayer.getWidth()*collisionLayer.getTileWidth()) loc.setX(collisionLayer.getWidth()*collisionLayer.getTileWidth()-width);
		
	}

    //<-- Will be replaced by Box2D
    private void gravity() {
		velocity.y-=Gdx.graphics.getDeltaTime()*1200;
	}
	
	//<-- OLD -->
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
	}

    private boolean collidesRight() {
		for(float step = 0; step < height; step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(loc.getX() + width, loc.getY() + step))
				return true;
		return false;
	}

    private boolean collidesLeft() {
		for(float step = 0; step < height; step += collisionLayer.getTileHeight() / 2)
			if(isCellBlocked(loc.getX(), loc.getY() + step))
				return true;
		return false;
	}

    private boolean collidesTop() {
		for(float step = 0; step < width; step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(loc.getX() + step, loc.getY() + height))
				return true;
		return false;
	}

    private boolean collidesBottom() {
		for(float step = 0; step < width; step += collisionLayer.getTileWidth() / 2)
			if(isCellBlocked(loc.getX() + step, loc.getY()))
				return true;
		return false;
	}
	//<-- OLD -->
    // Will be replaced by box2D -->


    /**
     * Lets the entity jump
     */
    void jump() {
		if(isOnGround) {
			velocity.y = 400;
			isOnGround = false;
		}
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
     * @return
     */
	public int getHeight() {
		return height;
	}

}
