package de.themdplays.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import de.themdplays.map.Cell;
import de.themdplays.map.Tile;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.screens.menu.MainMenu;
import de.themdplays.util.*;
import de.themdplays.util.ui.EditorTools;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    private int middleX, middleY, oldTranslatedX, oldTranslatedY;
    private Tile filltmp;
    private Queue<List<Cell>> changes = new LinkedList<List<Cell>>();

    private Queue<Point> tiles = new LinkedList<Point>();

    @Override
    public void show() {
        batch = new SpriteBatch();

        map = new WizardJumperMap(100, 50);

        maplocation = new Location(0, 0);

        tool = EditorTools.PENCIL;
        currentTile = Tile.DIRT;

        //INTI RENDER
        levelRenderer = new LevelRenderer();
        editorUIRenderer = new EditorUIRenderer();

        Gdx.input.setInputProcessor(new InputMultiplexer(this, editorUIRenderer.getStage()));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //WORLD RENDERER
        levelRenderer.render(null, map, false); //TO NOT CONVERT IT TO BOX2D
        //UI RENDERING

        batch.begin();
        editorUIRenderer.render(batch);
        handleMiddleClickMovement();
        batch.end();

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float tileSize = Constants.TILE_SIZE * levelRenderer.getZoom();
            int translatedX = (int) ((Gdx.input.getX() - levelRenderer.getMapLoc().getX()) / tileSize);
            int translatedY = (int) ((Gdx.graphics.getHeight() - Gdx.input.getY() - levelRenderer.getMapLoc().getY()) / tileSize);


            if(Gdx.input.getY() <= Gdx.graphics.getHeight() &&
                    //BOUNDS
                    translatedY >= 0 &&
                    translatedY < map.getHeight() &&
                    translatedX >= 0 &&
                    translatedX < map.getWidth() &&
                    Gdx.input.getX() >= editorUIRenderer.getButtons().getWidth() + editorUIRenderer.getButtons().getX() + Gdx.graphics.getWidth() * 0.04f &&
                    Gdx.input.getY() <= Gdx.graphics.getHeight() - (editorUIRenderer.getChooser().getHeight() + editorUIRenderer.getChooser().getY() + Gdx.graphics.getWidth() * 0.04f)) {

                double distance = Math.sqrt(Math.pow(Math.abs(translatedX-oldTranslatedX), 2)+ Math.pow(Math.abs(translatedY-oldTranslatedY), 2));
                if(distance>1 && oldTranslatedX!=-1) {
                    addLinePoints(translatedX, translatedY, oldTranslatedX, oldTranslatedY);
                } else tiles.add(new Point(translatedX, translatedY));

                Cell[][] tmp = map.getCells();

                switch(tool) {
                    case PENCIL:
                        if(map.getCells()[translatedY][translatedX].getTile() != currentTile) {
                            while(!tiles.isEmpty()) {
                                Point p = tiles.remove();
                                tmp[p.y][p.x] = new Cell(currentTile, new Vector2(p.y, p.x));
                                map.setCells(tmp);
                            }
                        }
                        break;
                    case ERASER:
                        while(!tiles.isEmpty()) {
                            Point p = tiles.remove();
                            tmp[p.y][p.x] = new Cell(Tile.AIR, new Vector2(p.y, p.x));
                            map.setCells(tmp);
                        }
                        break;
                    case FILL:
                        if(map.getCells()[translatedY][translatedX].getTile() != currentTile) {
                            Tile tile = map.getCells()[translatedY][translatedX].getTile();
                            filltmp = tile;
                            map.setCells(floodQueueFill(map.getCells(), translatedX, translatedY, tile));
                        }
                        break;
                }
            }
            oldTranslatedX = translatedX;
            oldTranslatedY = translatedY;
        } else {
            oldTranslatedX = -1;
        }

        ButtonHandler.backFunc(new MainMenu());
    }


    private void addLinePoints(int x0, int y0, int x1, int y1) {
        boolean steep = Math.abs(y1-y0) > Math.abs(x1-x0);

        if(steep) {
            //swap x and y
            int tmp = x0;
            x0 = y0;
            y0 = tmp;

            tmp = x1;
            x1 = y1;
            y1 = tmp;
        }
        if(x0>x1) {
            //swap 0 and 1
            int tmp = x0;
            x0 = x1;
            x1=tmp;

            tmp = y0;
            y0 = y1;
            y1=tmp;
        }

        int dx, dy;
        dx = x1 - x0;
        dy = Math.abs(y1-x0);

        int err = dx/2;
        int ystep;
        if(y0<y1)
            ystep = 1;
        else
            ystep = -1;

        for(; x0 <= x1; x0++) {
            if(y0>=0&&x0>=0)
                if(steep)
                    tiles.add(new Point(y0, x0));
                else
                    tiles.add(new Point(x0, y0));
        }
        err -= dy;
        if(err<0) {
            y0+= ystep;
            err+=dx;
        }

    }

    /**
     * Handles the middleclick movement
     */
    private void handleMiddleClickMovement() {
        if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE) && !down) {
            middleX = Gdx.input.getX() - (int) levelRenderer.getMapLoc().getX();
            middleY = Gdx.graphics.getHeight() - Gdx.input.getY() - (int) levelRenderer.getMapLoc().getY();

            down = true;
        } else if(Gdx.input.isButtonPressed(Input.Buttons.MIDDLE) && down) {
            levelRenderer.setMapLoc(new Location(Gdx.input.getX() - middleX, Gdx.graphics.getHeight() - Gdx.input.getY() - middleY));
        } else if(!Gdx.input.isButtonPressed(Input.Buttons.MIDDLE)) {
            down = false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.Z) {
            if(!changes.isEmpty()) {
                List<Cell> tmp = changes.remove();
                Cell[][] celltmp = map.getCells();
                for(Cell cell : tmp) {
                    celltmp[(int) cell.getLocation().y][(int) cell.getLocation().x] = cell;
                }
                map.setCells(celltmp);
            }

        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        float newZoom = levelRenderer.getZoom() + (-amount * 0.2f);

        float x = Gdx.graphics.getWidth() / 2 + (levelRenderer.getMapLoc().getX() - Gdx.graphics.getWidth() / 2) * (float) newZoom / (float) levelRenderer.getZoom();
        float y = Gdx.graphics.getHeight() / 2 + (levelRenderer.getMapLoc().getY() - Gdx.graphics.getHeight() / 2) * (float) newZoom / (float) levelRenderer.getZoom();

        levelRenderer.setMapLoc(new Location(x, y));

        levelRenderer.setZoom(newZoom);
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    /**
     * Sets the editor Tool to tool
     *
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
     *
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
     * Improved FloodFill function using queues
     *
     * @param map
     * @param x
     * @param y
     * @param clickedTile
     * @return updated 2D Cellarray
     */
    private Cell[][] floodQueueFill(Cell[][] map, int x, int y, final Tile clickedTile) {
        Queue<Point> queue = new LinkedList<Point>();
        if(map[y][x].getTile() != clickedTile)
            return map;

        queue.add(new Point(x, y));

        int iterations = 0;
        List<Cell> c = new ArrayList<Cell>();

        while(!queue.isEmpty()) {
            Point p = queue.remove();
            if(map[p.y][p.x].getTile() == clickedTile && map[p.y][p.x].getTile() != currentTile) {
                iterations++;
                c.add(map[p.y][p.x]);

                map[p.y][p.x] = new Cell(currentTile, new Vector2(p.x, p.y));
                if(p.x > 0) queue.add(new Point(p.x - 1, p.y));
                if(p.x < this.map.getWidth() - 1) queue.add(new Point(p.x + 1, p.y));
                if(p.y > 0) queue.add(new Point(p.x, p.y - 1));
                if(p.y < this.map.getHeight() - 1) queue.add(new Point(p.x, p.y + 1));

            }
        }
        Gdx.app.log("Fill", "Blocks filled: " + iterations);
        return map;
    }

    /**
     * Saves the map
     *
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
