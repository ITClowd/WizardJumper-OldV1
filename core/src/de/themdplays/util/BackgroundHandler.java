package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class BackgroundHandler {

	private Sprite cloud0, cloud1;
	
	private float width, height;
	
	private Location[] locs;
	private float[] speeds;
	
	public BackgroundHandler(float width, float height) {
		this.width = width;
		this.height = height;

		locs = new Location[5];
		speeds = new float[locs.length];
		cloud0 = new Sprite(new Texture(Gdx.files.internal("background/cloud0.png")));
		cloud1 = new Sprite(new Texture(Gdx.files.internal("background/cloud1.png")));
	
		initVars();
	}

	public void render(float delta, SpriteBatch batch) {
		batch.begin();
		for(int i=0; i<locs.length; i++) {
			batch.draw(i%2==0?cloud0:cloud1, locs[i].getX(), locs[i].getY(),
					cloud1.getWidth()/2/(locs[i].getY()>420?1.5f:1),
					cloud1.getHeight()/2/(locs[i].getY()>420?1.5f:1));
		}
		batch.end();
		updateLocs();
	}

    /**
     * Updates the cloud positions
     */
	private void updateLocs() {
		for(int i=0; i<locs.length; i++) {
			if(locs[i].getX()>CamHandler.getPlayCam().position.x+CamHandler.getPlayCam().viewportWidth/2) //check whether the cloud is right of the screen
				locs[i].setX(CamHandler.getPlayCam().position.x-CamHandler.getPlayCam().viewportWidth/2-cloud0.getWidth()); //moves the cloud to the left of the screen
			else if(locs[i].getX()<CamHandler.getPlayCam().position.x-CamHandler.getPlayCam().viewportWidth/2-cloud0.getWidth())
				locs[i].setX(CamHandler.getPlayCam().position.x+CamHandler.getPlayCam().viewportWidth/2);
			else locs[i].setX(locs[i].getX()+speeds[i]); //Let the clouds move
		}
	}

    /**
     * Initializes all vars for backgroundhandler
     */
	private void initVars() {
		//LOCS
		for(int i=0; i<locs.length; i++) {
			locs[i] = new Location(width/locs.length*i+new Random().nextInt(50), height/4*3-100+new Random().nextInt(200));
		}
		//SPEEDS
		for(int i=0; i<speeds.length; i++) {
			speeds[i] = (new Random().nextInt(6)+1)*0.05f;
		}
		
	}
	
}
