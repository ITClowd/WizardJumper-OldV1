package de.themdplays.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import de.themdplays.screens.Play;
import de.themdplays.util.Assets;
import de.themdplays.util.Location;

public class Player extends Entity {

	private Sprite look;
	
	private final static float PLAYERSPEED = 200;

	private TextureAtlas player_atlas;

	private Animation walk_right;
	private Animation walk_left;
	private Sprite standing;

	private World world;

	public Player(Location location, World world) {
		super(location, 32, 32, world);
		player_atlas = Assets.manager.get(Assets.playerAtlas);
		initSprites();
		initAnimations();
		this.world = world;
		super.body = createBody();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {
                switch(keycode) {
                    case Keys.W:
                        jump();
                        break;
                    case Keys.A:
                        velocity.x = -PLAYERSPEED;
                        break;
                    case Keys.D:
                        velocity.x = PLAYERSPEED;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    case Keys.A:
                    case Keys.D:
                        velocity.x = 0;
                        body.setLinearVelocity(0, body.getLinearVelocity().y);
                        break;
                }
                return  true;
            }
        });
	}

    private float elapsedTime = 0f;

    private void update() {

        float lerp = 2f;
        Vector3 position = Play.camera.position;
        position.x += (body.getPosition().x - position.x)* lerp * Gdx.graphics.getDeltaTime();
        position.y += (body.getPosition().y - position.y) * lerp * Gdx.graphics.getDeltaTime();
        Play.camera.position.set(position);

        Play.camera.update();

        super.body.applyForceToCenter(velocity, true);

        if(body.getLinearVelocity().x >= 10) body.setLinearVelocity(10, body.getLinearVelocity().y);
        if(body.getLinearVelocity().x <= -10) body.setLinearVelocity(-10, body.getLinearVelocity().y);

        //Spriteanimation
		elapsedTime += Gdx.graphics.getDeltaTime() * body.getLinearVelocity().x*10/8;

		if(body.getLinearVelocity().x > 0) {
			look = new Sprite(walk_right.getKeyFrame(elapsedTime, true));
		} else if(body.getLinearVelocity().x < 0) {
			look = new Sprite(walk_left.getKeyFrame(elapsedTime, true));
		} else {
			look = standing;
		}

        look.setSize(2, 2);
        look.setOriginCenter();
		body.setUserData(look);

	}

	public void render(SpriteBatch batch, float delta) {
		render(delta);
		update();

		Sprite sprite = (Sprite) body.getUserData();
        sprite.setPosition(body.getPosition().x-sprite.getWidth()*0.5f, body.getPosition().y-sprite.getHeight()*0.5f);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);

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

    @Override
    protected Body createBody() {
        //body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getLocation().getX(), getLocation().getY());
        bodyDef.fixedRotation = true;

        //ballshape
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.9f,0.9f);

        //fixture def
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 5f;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;

        Body body = Play.world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        box.dispose();
        return body;
    }
}
