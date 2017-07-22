package de.themdplays.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import de.themdplays.entities.Entity;
import de.themdplays.util.Location;

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
    public WizardJumperMap(int width, int height) {
        cells = new Cell[height][width];
        for(Cell[] row : cells) {
            Arrays.fill(row, new Cell(Tile.AIR));
        }
        this.width = width;
        this.height = height;
    }

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
        compressedMap = "";
        compressedMap+=width+";" + height+";";
        for(int y = 0; y<height; y++) {
            for(int x = 0; x<width; x++) {
                compressedMap+=cells[y][x].getTile().getID()+";";
            }
        }

    }

    /**
     * Uncompresses a compressed string to an map
     */
    private void uncompress() {
        String[] array = compressedMap.split(";");
        this.width=Integer.parseInt(array[0]);
        this.height=Integer.parseInt(array[1]);
        cells = new Cell[height][width];

        for(int y = 0; y<height; y++) {
            for(int x = 0; x<width; x++) {
                cells[y][x] = new Cell(Tile.getNameByCode(Integer.parseInt(array[y*height + x+2])));
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
