package de.themdplays.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import de.themdplays.util.Location;

public class Player extends Entity {

	private Sprite look;
	
	private final static float PLAYERSPEED = 120;
	
	private TextureAtlas player_atlas;
	
	private Animation walk_right;
	private Animation walk_left;
	private Sprite standing;
	
	public Player(Location location, TiledMapTileLayer collisionLayer) {
		super(location, 32, 32, collisionLayer);
		player_atlas = new TextureAtlas(Gdx.files.internal("entity/player/player.atlas"));
		initSprites();
		initAnimations();
	}

	private float elapsedTime = 0f;

    private void update() {
		
		if(Gdx.input.isKeyPressed(Keys.A)) velocity.x = Gdx.graphics.getDeltaTime() * PLAYERSPEED * -1;
		if(Gdx.input.isKeyPressed(Keys.D)) velocity.x = Gdx.graphics.getDeltaTime() * PLAYERSPEED;
		if(Gdx.input.isKeyPressed(Keys.W)) jump();
		if(!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) velocity.x = 0;

		//Spriteanimation
		elapsedTime += Gdx.graphics.getDeltaTime() * PLAYERSPEED/8;
		
		if(velocity.x > 0) {
			look = new Sprite(walk_right.getKeyFrame(elapsedTime, true));
		} else if(velocity.x < 0) {
			look = new Sprite(walk_left.getKeyFrame(elapsedTime, true));
		} else {
			look = standing;
		}
		
	}
	
	public void render(SpriteBatch batch, float delta) {
		render(delta);
		update();
		batch.draw(look, loc.getX(), loc.getY());
		
	}

    /**
     * Init of Sprites
     */
    private void initSprites() {
		standing = player_atlas.createSprite("Standing");
	}

    /**
     * Creates all sprites of the player animation
     */
    private void initAnimations() {
		Sprite tmp;
		Array<Sprite> tmparray = new Array<Sprite>();
		
		//INIT WALK_LEFT
		tmp = player_atlas.createSprite("Left_Left");
		tmparray.add(tmp);
		tmp = player_atlas.createSprite("Left_Standing");
		tmparray.add(tmp);
		tmp = player_atlas.createSprite("Left_Right");
		tmparray.add(tmp);
		tmp = player_atlas.createSprite("Left_Standing");
		tmparray.add(tmp);
		walk_left = new Animation(3, tmparray);
		
		//INIT WALK_RIGHT
		tmparray.clear();
		tmp = player_atlas.createSprite("Right_Left");
		tmparray.add(tmp);
		tmp = player_atlas.createSprite("Right_Standing");
		tmparray.add(tmp);
		tmp = player_atlas.createSprite("Right_Right");
		tmparray.add(tmp);
		tmp = player_atlas.createSprite("Right_Standing");
		tmparray.add(tmp);
		walk_right = new Animation(3, tmparray);
	}
	
}
