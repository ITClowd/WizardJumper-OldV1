package de.themdplays.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import de.themdplays.map.Cell;
import de.themdplays.map.Tile;
import de.themdplays.map.WJMap;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.screens.menu.MainMenu;
import de.themdplays.util.*;
import de.themdplays.util.ui.EditorTools;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Stack;

/**
 * Created by Moritz on 30.12.2016.
 */
public class Editor extends InputAdapter implements Screen {

    private SpriteBatch batch;
    private static WJMap map;
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
    private Stack<List<Cell>> changes = new Stack<>();

    private static Queue<Point> tiles = new LinkedList<Point>();

    @Override
    public void show() {
        batch = new SpriteBatch();
        map = new WJMap();
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

        double tileSize = Constants.TILE_SIZE * levelRenderer.getZoom();
        Point translatedLocation = getTranslatedMousePosition(tileSize);

        if(!isOverUI(translatedLocation)) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

                if(getDistanceSinceLastClick(translatedLocation) > 1 && oldTranslatedX != -1)
                    addLinePoints(translatedLocation.x, translatedLocation.y, oldTranslatedX, oldTranslatedY);
                else
                    tiles.add(new Point(translatedLocation.x, translatedLocation.y));


                switch(tool) {
                    case PENCIL:
                        if(map.getCell(translatedLocation).getTile() != currentTile) {
                            List<Cell> tmpChanges = new ArrayList<Cell>();
                            while(!tiles.isEmpty()) {
                                Point p = tiles.remove();

                                tmpChanges.add(map.getCell(p)); //for undo
                                map.addCell(new Cell(currentTile, new Point(p.x, p.y))); //actual change
                            }
                            changes.add(tmpChanges);
                        }
                        break;
                    case ERASER:
                        List<Cell> tmpChanges = new ArrayList<Cell>();
                        while(!tiles.isEmpty()) {
                            Point p = tiles.remove();

                            tmpChanges.add(map.getCell(p)); //for UNDO
                            map.removeCell(map.getCell(p)); //actual change
                        }
                        changes.add(tmpChanges);
                        break;
                    case FILL:
                        if(map.getCell(translatedLocation).getTile() != currentTile) {
                            Tile tile = map.getCell(translatedLocation).getTile();
                            filltmp = tile;

                            map.setCellHash(floodQueueFill(map, translatedLocation.x, translatedLocation.y, tile));
                        }
                        break;
                }
                oldTranslatedX = translatedLocation.x;
                oldTranslatedY = translatedLocation.y;
            } else
                oldTranslatedX = -1;

            batch.begin();
            Sprite sprite = EdgeRecognizer.getSprite(currentTile, 0);
            float alpha = 0.5f;
            batch.setColor(sprite.getColor().r, sprite.getColor().g, sprite.getColor().b,alpha);
            batch.draw(sprite,
                    Math.round(translatedLocation.x * tileSize + levelRenderer.getMapLoc().getX()),
                    Math.round(translatedLocation.y * tileSize + levelRenderer.getMapLoc().getY()), (int) tileSize, (int) tileSize);
            batch.end();

        }

        ButtonHandler.backFunc(new MainMenu());
    }

    /**
     * Returns the distance between the last click and the current click
     * @param translatedLocation
     * @return double distance
     */
    private double getDistanceSinceLastClick(Point translatedLocation) {
        return Math.sqrt(Math.pow(Math.abs(translatedLocation.x-oldTranslatedX), 2)+ Math.pow(Math.abs(translatedLocation.y-oldTranslatedY), 2));
    }

    /**
     * Returns whether the translatedLocation is in mapgrid bounds or not
     * @param translatedLocation
     * @return boolean is in mapgrid
     */
    private boolean isOverUI(Point translatedLocation) {
        return !(Gdx.input.getX() >= editorUIRenderer.getButtons().getWidth() + editorUIRenderer.getButtons().getX() + Gdx.graphics.getWidth() * 0.04f &&
                Gdx.input.getY() <= Gdx.graphics.getHeight() - (editorUIRenderer.getChooser().getHeight() + editorUIRenderer.getChooser().getY() + Gdx.graphics.getWidth() * 0.04f));
    }

    /**
     * Translates the Mouse Location to the map grid
     * @param tileSize The translated tileSize
     * @return Translated location as {@link Point}
     */
    private Point getTranslatedMousePosition(double tileSize) {
        int translatedX = MathUtils.floor((float)((Gdx.input.getX() - levelRenderer.getMapLoc().getX()) / tileSize));
        int translatedY = MathUtils.floor((float)((Gdx.graphics.getHeight() - Gdx.input.getY() - levelRenderer.getMapLoc().getY()) / tileSize));
        return new Point(translatedX, translatedY);
    }

    /**
     * Adds Points to queue via Linear Interpolation between values
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     */
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

    /**
     * Undo's the last change
     */
    private void undo() {
        if(!changes.isEmpty()) {
            List<Cell> c = changes.pop();

            for(Cell old: c) {
                map.addCell(old);
            }
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.Z) {
            undo();
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        double newZoom = levelRenderer.getZoom() -0.5f*amount;

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
        tiles.clear();
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
     * NOT WORKING
     *
     * @param map
     * @param x
     * @param y
     * @param clickedTile
     * @return updated 2D Cellarray
     */
    @Deprecated
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

                //NOT WORKING ANY MORE
//                map[p.y][p.x] = new Cell(currentTile, new Point(p.x, p.y));
//                if(p.x > 0) queue.add(new Point(p.x - 1, p.y));
//                if(p.x < this.map.getWidth() - 1) queue.add(new Point(p.x + 1, p.y));
//                if(p.y > 0) queue.add(new Point(p.x, p.y - 1));
//                if(p.y < this.map.getHeight() - 1) queue.add(new Point(p.x, p.y + 1));

            }
        }
        Gdx.app.log("Fill", "Blocks filled: " + iterations);
        return map;
    }

    private HashMap<Integer, Cell> floodQueueFill(WJMap map, int x, int y, final Tile clickedTile) {
        List<Cell> tmpChanges = new ArrayList<Cell>();

        Queue<Point> queue = new LinkedList<Point>();
        if(map.getCell(x, y).getTile() != clickedTile)
            return map.getCellHash();

        queue.add(new Point(x, y));

        int iterations = 0;
        List<Cell> c = new ArrayList<Cell>();

        while(!queue.isEmpty()) {
            Point p = queue.remove();
            if(map.getCell(p).getTile() == clickedTile && map.getCell(p).getTile() != currentTile) {
                iterations++;
                c.add(map.getCell(p));

                tmpChanges.add(map.getCell(p));
                map.addCell(new Cell(currentTile, new Point(p.x, p.y)));

                if(p.x > 0) queue.add(new Point(p.x - 1, p.y));
                if(p.x < Constants.MAXMAPSIZE - 1) queue.add(new Point(p.x + 1, p.y));
                if(p.y > 0) queue.add(new Point(p.x, p.y - 1));
                if(p.y < Constants.MAXMAPSIZE - 1) queue.add(new Point(p.x, p.y + 1));

            }
        }
        changes.add(tmpChanges);
        Gdx.app.log("Fill", "Blocks filled: " + iterations);
        return map.getCellHash();
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
