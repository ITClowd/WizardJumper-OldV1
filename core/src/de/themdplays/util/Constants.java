package de.themdplays.util;

import com.badlogic.gdx.Gdx;

/**
 * Stores some important constants of the game
 */
public class Constants {
	
    public static int FONT_SIZE_BIG= Gdx.graphics.getHeight()/30;
    public static int FONT_SIZE_SMALL = Gdx.graphics.getHeight()/70;

    public static final int TILE_SIZE = 16;

	public static final int TILES_PER_WIDTH = 8;

	public static final int PIXELS_TO_METERS = TILE_SIZE; //equals 1 meter

	public static void recalcFontSizes() {
		FONT_SIZE_BIG= Gdx.graphics.getHeight()/30;
		FONT_SIZE_SMALL = Gdx.graphics.getHeight()/70;
	}


}
