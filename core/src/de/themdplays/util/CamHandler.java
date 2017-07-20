package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import de.themdplays.entities.Player;

public class CamHandler {
	
	private static OrthographicCamera playCam, hudCam;
	
	public CamHandler() {
        playCam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        playCam.position.set(playCam.viewportWidth/2f, playCam.viewportHeight/2f, 0);
        playCam.update();
        
        hudCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        hudCam.position.set(hudCam.viewportWidth/2f, hudCam.viewportHeight / 2f, 0);
        hudCam.update();

	}

    /**
     * Updates the camera position to the player
     * @param tiledMap
     * @param p
     */
    @Deprecated
	public void updateCam(TiledMap tiledMap, Player p) {

		int mapWidth = tiledMap.getProperties().get("width", Integer.class);
		int tilePixelWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
		int mapHeight= tiledMap.getProperties().get("height", Integer.class);
		int tilePixelHeight = tiledMap.getProperties().get("tileheight", Integer.class);
		
		float minX, maxX, minY, maxY;
		
		minX = (int) (playCam.viewportWidth/2);
		maxX = (mapWidth*tilePixelWidth)-minX;
		
		minY = (int) (playCam.viewportHeight/2);
		maxY = (mapHeight*tilePixelHeight)-minY;
		
		float lerp = 2f;
		Vector3 position = playCam.position;
		position.x += (p.getLocation().getX()+16 - position.x)* lerp * Gdx.graphics.getDeltaTime();
		position.y += (p.getLocation().getY()+16 - position.y) * lerp * Gdx.graphics.getDeltaTime();

		
		if(position.x>minX && position.x<maxX) {
		} else {
			if(position.x<=minX) position.x = minX;
			else position.x = maxX;
		}
		
		if(position.y>minY && position.y<maxY) {
		} else {
			if(position.y<=minY) position.y = minY;
			else position.y = maxY;
		}
		playCam.update();
	}

    /**
     * @return playCam
     */
	public static OrthographicCamera getPlayCam() {
		return playCam;
	}

    /**
     * @return hudCam
     */
	public static OrthographicCamera getHudCam() {
		return hudCam;
	}
	
	public void resize(int width, int height) {
		playCam.viewportHeight = Constants.VIEWPORT_WIDTH * height/(float)width;
		playCam.position.set(playCam.viewportWidth/2f, playCam.viewportHeight / 2f, 0);
		playCam.update();
	
	}

}
