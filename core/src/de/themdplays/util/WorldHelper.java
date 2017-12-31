package de.themdplays.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.themdplays.map.Cell;
import de.themdplays.map.Tile;
import de.themdplays.map.WJMap;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.screens.Play;

/**
 * Created by Moritz on 22/07/2017.
 */
public class WorldHelper {

    private WizardJumperMap oldMap;
    private WJMap map;
    private Body level;

    public WorldHelper(WJMap map) {
        this.map = map;
        createBody();
        createMapBodiesNewSystem();
    }

    @Deprecated
    private void createMapBodiesOld() {
        PolygonShape shape = new PolygonShape();

        for(int y = 0; y< oldMap.getHeight(); y++) {
            for(int x = 0; x< oldMap.getWidth(); x++) {
                if(oldMap.getCells()[y][x] != null) {
                    Tile tile = oldMap.getCells()[y][x].getTile();
                    if(tile != Tile.AIR) {
                        shape.setAsBox(.5f, .5f, new Vector2(x+.5f, y+.5f), 0);
                        level.createFixture(shape, 0);
                    }
                }
            }
        }
        shape.dispose();
    }


    private void createMapBodiesNewSystem() {
        PolygonShape shape = new PolygonShape();

        for(Cell c : map.getCellHash().values()) {
            shape.setAsBox(.5f, .5f, new Vector2(c.getLocation().x+.5f, c.getLocation().y+.5f), 0);
            level.createFixture(shape, 0);
        }
        shape.dispose();
    }

    private void createMapBodies() {
        PolygonShape shape = new PolygonShape();
        for(int y = 0; y< oldMap.getHeight(); y++) {
            int oldX = -1, startX = -1;
            for(int x = 0; x< oldMap.getWidth(); x++) {
                if(oldMap.getCells()[y][x] != null) {
                    Tile tile = oldMap.getCells()[y][x].getTile();
                    if(tile != Tile.AIR) {
                        oldX = x;
                    } else {
                        if(x-1 == oldX) {
                            shape.setAsBox((oldX-startX)/2f, .5f, new Vector2(oldX-(oldX-startX)/2f+1, y+.5f), 0);
                            level.createFixture(shape, 0);
                        }
                        startX = x;
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
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        level = Play.world.createBody(bodyDef);
    }

}
