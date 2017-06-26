package de.themdplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.themdplays.map.Cell;
import de.themdplays.map.Tile;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.util.Constants;
import de.themdplays.util.EditorUIRenderer;
import de.themdplays.util.LevelRenderer;
import de.themdplays.util.Location;
import de.themdplays.util.ui.EditorTools;

/**
 * Created by Moritz on 30.12.2016.
 */
public class Editor implements Screen, InputProcessor {

    private SpriteBatch batch;

    private WizardJumperMap map;

    private static EditorTools tool;
    private static Tile currentTile;

    //RENDER
    private EditorUIRenderer editorUIRenderer;
    private LevelRenderer levelRenderer;

    private Location maplocation;

    @Override
    public void show() {
        batch = new SpriteBatch();

        map = new WizardJumperMap(100, 100);

        maplocation = new Location(0, 0);

        tool = EditorTools.PENCIL;
        currentTile = Tile.DIRT;

        //INTI RENDER
        editorUIRenderer = new EditorUIRenderer(this);
        levelRenderer = new LevelRenderer();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //WORLD RENDERER
        levelRenderer.render(batch, map);
        //UI RENDERING
        editorUIRenderer.render();

        batch.end();
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getY()<=Gdx.graphics.getHeight()) {
            float tileSize = Constants.TILE_SIZE*levelRenderer.getZoom();
            switch(tool) {
                case PENCIL:
                    Cell[] [] tmp = map.getCells();
                    tmp[(Gdx.graphics.getHeight()-Gdx.input.getY())/(int)tileSize]
                            [Gdx.input.getX()/(int)tileSize] = new Cell(currentTile);
                    map.setCells(tmp);
                    break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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
    }

    public static void setTool(EditorTools tool) {
        Editor.tool = tool;
        Gdx.app.log("INFO", "Current Tool " + tool.name());
    }

    public static EditorTools getCurrentTool() {
        return tool;
    }

    //region Unneccessary
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    //endregion

    @Override
    public boolean scrolled(int amount) {
        levelRenderer.setZoom(levelRenderer.getZoom() + -amount * 0.2f);
        return false;
    }
}
