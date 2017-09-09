package de.themdplays.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.themdplays.map.Tile;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.screens.Play;

/**
 * Created by Moritz on 22/07/2017.
 */
public class WorldHelper {

    private WizardJumperMap map;

    private Body level;

    public WorldHelper(WizardJumperMap map) {
        this.map = map;

        createBody();
        PolygonShape shape = new PolygonShape();

        for(int y = 0; y<map.getHeight(); y++) {
            for(int x = 0; x<map.getWidth(); x++) {
                if(map.getCells()[y][x] != null) {
                    Tile tile = map.getCells()[y][x].getTile();
                    if(tile != Tile.AIR) {
                        shape.setAsBox(.5f, .5f, new Vector2(x+.5f, y+.5f), 0);
                        level.createFixture(shape, 0);
                    }
                }
            }
        }

        shape.dispose();
    }

    private void createBody() {

        //body def
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;

        //fixture def
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;

        //ground
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        //Ground shape
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(0, map.getHeight()), new Vector2(0, 0), new Vector2(map.getWidth(), 0), new Vector2(map.getWidth(), map.getHeight()),new Vector2(0, map.getHeight())});

        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;

        level = Play.world.createBody(bodyDef);
        level.createFixture(fixtureDef);

        groundShape.dispose();
    }



}
