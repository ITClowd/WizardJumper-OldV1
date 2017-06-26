package de.themdplays.map;

import com.badlogic.gdx.Gdx;

import java.util.Arrays;

/**
 * Created by Moritz on 23.12.2016.
 */
public class WizardJumperMap {

    private String compressedMap = "";

    private Cell[] [] cells;
    private int width, height;

    public WizardJumperMap(int width, int height) {
        cells = new Cell[height][width];
        for(Cell[] row : cells) {
            Arrays.fill(row, new Cell(Tile.AIR));
        }
        this.width = width;
        this.height = height;
    }

    /**
     * Compresses the map to an short string
     */
    private void compress() {
        compressedMap+=width+";" + height+";";
        for(int x = 0; x<height; x++) {
            for(int y = 0; y<width; y++) {
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
        for(int x = 0; x<height; x++) {
            for(int y = 0; y<width; y++) {
                cells[y][x] = new Cell(Tile.getNameByCode(Integer.parseInt(array[(y+2)*(x+2)])));
            }
        }
    }

    /**
     * Saves an map
     * @param name
     */
    public void save(String name) {
        compress();
        Gdx.files.local("maps/" + name + ".wjm").writeString(compressedMap, false);
    }

    /**
     * Loads a map
     * @param name
     */
    public void load(String name) {
        compressedMap = Gdx.files.local("maps/" + name + ".wjm").readString();
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

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}
