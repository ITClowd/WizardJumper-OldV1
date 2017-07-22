package de.themdplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.scenario.effect.Flood;
import de.themdplays.map.Cell;
import de.themdplays.map.Tile;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.screens.menu.MainMenu;
import de.themdplays.util.*;
import de.themdplays.util.ui.EditorTools;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Moritz on 30.12.2016.
 */
public class Editor extends InputAdapter implements Screen {

    private SpriteBatch batch;
    private static WizardJumperMap map;
    private static EditorTools tool;
    private static Tile currentTile;
    private static boolean changedTool = false;

    //RENDER
    private EditorUIRenderer editorUIRenderer;
    private LevelRenderer levelRenderer;
    private Location maplocation;
    private boolean down = false;
    private int middleX, middleY;
    private Tile filltmp;


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
        Gdx.gl.glClearColor(255, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //WORLD RENDERER
        levelRenderer.render(batch, map, false); //TO NOT CONVERT IT TO BOX2D
        //UI RENDERING
        editorUIRenderer.render(batch);

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

        float tileSize = Constants.TILE_SIZE*levelRenderer.getZoom();

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getY()<=Gdx.graphics.getHeight()&&
        //BOUNDS
                ((Gdx.graphics.getHeight()-Gdx.input.getY()-(int)levelRenderer.getMapLoc().getY())/(int)tileSize)>=0&&
                ((Gdx.graphics.getHeight()-Gdx.input.getY()-(int)levelRenderer.getMapLoc().getY())/(int)tileSize)<map.getHeight()&&
                ((Gdx.input.getX()-(int)levelRenderer.getMapLoc().getX())/(int)tileSize>=0)&&
                ((Gdx.input.getX()-(int)levelRenderer.getMapLoc().getX())/(int)tileSize)<map.getWidth()&&
                Gdx.input.getX()>=editorUIRenderer.getButtons().getWidth()+editorUIRenderer.getButtons().getX()+Gdx.graphics.getWidth()*0.04f&&
                Gdx.input.getY()<=Gdx.graphics.getHeight()-(editorUIRenderer.getChooser().getHeight()+editorUIRenderer.getChooser().getY()+Gdx.graphics.getWidth()*0.04f)) {

            switch(tool) {
                case PENCIL:
                    Cell[] [] tmp = map.getCells();
                    tmp[(Gdx.graphics.getHeight()-Gdx.input.getY()-(int)levelRenderer.getMapLoc().getY())/(int)tileSize]
                            [(Gdx.input.getX()-(int)levelRenderer.getMapLoc().getX())/(int)tileSize] = new Cell(currentTile);
                    map.setCells(tmp);
                    break;
                case ERASER:
                    Cell[] [] eraser_tmp = map.getCells();
                    eraser_tmp[(Gdx.graphics.getHeight()-Gdx.input.getY()-(int)levelRenderer.getMapLoc().getY())/(int)tileSize]
                            [(Gdx.input.getX()-(int)levelRenderer.getMapLoc().getX())/(int)tileSize] = new Cell(Tile.AIR);
                    map.setCells(eraser_tmp);
                    break;
                case FILL:

                    int x = (Gdx.input.getX()-(int)levelRenderer.getMapLoc().getX())/(int)tileSize;
                    int y = (Gdx.graphics.getHeight()-Gdx.input.getY()-(int)levelRenderer.getMapLoc().getY())/(int)tileSize;


                    Tile tile =   map.getCells()[y][x].getTile();
                    filltmp = tile;
                    map.setCells(floodFill(map.getCells(), x, y, tile));
                    break;
            }
        }
        ButtonHandler.backFunc(new MainMenu());
    }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * Sets the editor Tool to tool
     * @param tool
     */
    public static void setTool(EditorTools tool) {
        Editor.tool = tool;
        Gdx.app.log("INFO", "Current Tool " + tool.name());
    }

    /**
     * @return Current Editor tool
     */
    public static EditorTools getCurrentTool() {
        return tool;
    }

    /**
     * Sets the tile to draw with
     * @param currentTile
     */
    public static void setCurrentTile(Tile currentTile) {
        Editor.currentTile = currentTile;
    }

    /**
     * @return the current tile to draw with
     */
    public static Tile getCurrentTile() {
        return currentTile;
    }

    /**
     * Floodfill function //TODO FLOODFILL WITH QUEUES
     * @param map
     * @param x
     * @param y
     * @param clickedTile
     * @return updated 2D Cellarray
     */
    @Deprecated
    private Cell[][] floodFill(Cell[][] map, int x, int y, final Tile clickedTile) {

        float tileSize = Constants.TILE_SIZE*levelRenderer.getZoom();

        if(x>=0&&x<this.map.getWidth()&&y>=0&&y<this.map.getHeight()) {



            final Tile tile = clickedTile;


            if (map[y][x].getTile() == filltmp && map[y][x].getTile() != currentTile) {
                map[y][x] = new Cell(currentTile);
                map = floodFill(map, x - 1, y, filltmp);
                map = floodFill(map, x + 1, y, filltmp);
                map = floodFill(map, x, y - 1, filltmp);
                map = floodFill(map, x, y + 1, filltmp);
            }
        }
        return map;
    }

    /**
     * Saves the map
     * @param name
     */
    public static void saveMap(String name) {
        map.save(name);
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

    //endregion


}
