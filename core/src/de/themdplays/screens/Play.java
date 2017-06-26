package de.themdplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.themdplays.main.Player;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.util.BackgroundHandler;
import de.themdplays.util.CamHandler;
import de.themdplays.util.Constants;
import de.themdplays.util.Location;

public class Play implements Screen {

	private Player p;
	
	private SpriteBatch batch, hudBatch;

	//<--OLD-->
	private   TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
    //<--OLD-->

    //MAP
	private WizardJumperMap map;

	//FONT
	private BitmapFont font;

	private boolean DEBUG = true;

	//HANDLER
	private BackgroundHandler backgroundHandler;
	private CamHandler camHandler;

	public Play(TiledMap tiledMap) {
		this.tiledMap = tiledMap;
	}

	@Override
	public void show() {

	    //TODO CHANGING FONT GENERATION + REFACTORING
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Test.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 16;
        font = fontGenerator.generateFont(fontParameter);

		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
        camHandler = new CamHandler();
		tiledMap = new TmxMapLoader().load("Test_Map.tmx");
		
		backgroundHandler = new BackgroundHandler(Constants.VIEWPORT_WIDTH, CamHandler.getPlayCam().viewportHeight);

		
		p = new Player(new Location(((RectangleMapObject) tiledMap.getLayers().get(1).getObjects().get(0)).getRectangle().getX(), 
				((RectangleMapObject) tiledMap.getLayers().get("Objektebene 1").getObjects().get(0)).getRectangle().getY()),
				(TiledMapTileLayer) tiledMap.getLayers().get(0));
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		tiledMapRenderer.setView(CamHandler.getPlayCam());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camHandler.getPlayCam().combined);
		hudBatch.setProjectionMatrix(camHandler.getHudCam().combined);
		
		backgroundHandler.render(Gdx.graphics.getDeltaTime(), batch);

		
		tiledMapRenderer.render();
		batch.begin();
	
        tiledMapRenderer.setView(camHandler.getPlayCam());
		p.render(batch, delta);
		batch.end();
		
		hudBatch.begin();
		if(DEBUG) {
			font.draw(hudBatch, "FPS:" + Gdx.graphics.getFramesPerSecond() + "\nPX: " + p.getLocation().getX()/16 + "\nPY: " + p.getLocation().getY()/16 + "\nCX: " + camHandler.getPlayCam().position.x + "\nCY: " + camHandler.getPlayCam().position.y, camHandler.getHudCam().position.x-camHandler.getHudCam().viewportWidth/2f, camHandler.getHudCam().position.y+camHandler.getHudCam().viewportHeight/2f);
		}
		hudBatch.end();
		camHandler.updateCam(tiledMap, p);
	}

	@Override
	public void resize(int width, int height) {
		camHandler.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
        hudBatch.dispose();
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        font.dispose();
	}

	
}
