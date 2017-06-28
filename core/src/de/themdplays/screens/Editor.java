package de.themdplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
public class Editor implements Screen {

    private SpriteBatch batch;

    private WizardJumperMap map;

    private static EditorTools tool;
    private static Tile currentTile;

    private static boolean changedTool = false;

    //RENDER
    private EditorUIRenderer editorUIRenderer;
    private LevelRenderer levelRenderer;

    private Location maplocation;


    private boolean down = false;
    private int middleX, middleY;

    @Override
    public void show() {
        batch = new SpriteBatch();

        map = new WizardJumperMap(100, 100);

        maplocation = new Location(0, 0);

        tool = EditorTools.PENCIL;
        currentTile = Tile.DIRT;

        //INTI RENDER
        levelRenderer = new LevelRenderer();
        editorUIRenderer = new EditorUIRenderer();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //WORLD RENDERER
        //UI RENDERING
        levelRenderer.render(batch, map);
        editorUIRenderer.render();


        if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)&&!down) {
            middleX = Gdx.input.getX()-(int)levelRenderer.getMapLoc().getX();
            middleY = Gdx.graphics.getHeight()-Gdx.input.getY()-(int)levelRenderer.getMapLoc().getY();

            down = true;
        } else if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)&&down) {
            levelRenderer.setMapLoc(new Location(Gdx.input.getX()-middleX, Gdx.graphics.getHeight()-Gdx.input.getY()-middleY));
        } else if(!Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            down=false;
        }

        batch.end();

        //TODO ADD BOUNDS TO PENCIL FUNCTION
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getY()<=Gdx.graphics.getHeight()) {
            float tileSize = Constants.TILE_SIZE*levelRenderer.getZoom();
            switch(tool) {
                case PENCIL:
                    Cell[] [] tmp = map.getCells();
                    tmp[(Gdx.graphics.getHeight()-Gdx.input.getY()-(int)levelRenderer.getMapLoc().getY())/(int)tileSize]
                            [(Gdx.input.getX()-(int)levelRenderer.getMapLoc().getX())/(int)tileSize] = new Cell(currentTile);
                    map.setCells(tmp);
                    break;
            }
        }
    }

    public static void setTool(EditorTools tool) {
        Editor.tool = tool;
        Gdx.app.log("INFO", "Current Tool " + tool.name());
    }

    public static EditorTools getCurrentTool() {
        return tool;
    }

    //region Unnecessary Override stuff
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
    //endregion

}
