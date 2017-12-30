package de.themdplays.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import de.themdplays.entities.Entity;
import de.themdplays.util.Location;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Moritz on 23.12.2016.
 */
public class WizardJumperMap {

    private String compressedMap = "";

    private Cell[] [] cells;
    private int width, height;

    private String name = "";

    /**
     * Creates an empty map
     * @param width width of the map
     * @param height height of the map
     */
//    public WizardJumperMap(int width, int height) {
//        cells = new Cell[height][width];
//        for(Cell[] row : cells) {
//            Arrays.fill(row, new Cell(Tile.AIR, new Vector2(0, 0)));
//        }
//        this.width = width;
//        this.height = height;
//    }

    /**
     * Loads the map from the given filehandle and sets the name to the given string
     * @param fileHandle path to mapfile
     * @param name name of the map
     */
    public WizardJumperMap(FileHandle fileHandle, String name) {
        compressedMap = fileHandle.readString();
        this.name = name;
        uncompress();
    }

    /**
     * Compresses the map to an short string
     */
    private void compress() {
        for(int y = 0; y<cells.length; y++) {
            for(int x = 0; x<cells[0].length; x++) {
                compressedMap += cells[y][x].getTile().getID() + ";";
            }
            compressedMap += ":\n";
        }
    }

    /**
     * Uncompresses a compressed string to an map
     */
    private void uncompress() {
        String[] lines = compressedMap.split(":\n");
        cells = new Cell[lines.length][lines[0].length()];

        this.width = lines[0].length()/2;
        this.height = lines.length;

        for(int y = 0; y<lines.length; y++) {
            String[] row = lines[y].split(";");
            for(int x = 0; x<row.length; x++) {
                int id = Integer.parseInt(row[x]);
                cells[y][x] = new Cell(Tile.getNameByCode(id), new Point(x, y));
            }
        }
    }

    /**
     * Saves an map
     * @param name name of the map (file will be name.wjm)
     */
    public void save(String name) {
        compress();
        Gdx.files.local("maps/" + name + ".wjm").writeString(compressedMap, false);
    }

    /**
     * Loads a map
     * @param fileHandle filehandle
     */
    public void load(FileHandle fileHandle) {
        compressedMap = Gdx.files.local("maps/" + name + ".wjm").readString();
        this.name = name;
        uncompress();
    }

    /**
     * @return cells
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the 2d cell array of the map
     * @param cells
     */
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * @return the name of the map
     */
    public String getName() {
        return name;
    }

    public static class AttributeDescriptor {
        Location spawnpoint;
        List<Entity> entityList;
    }

}
