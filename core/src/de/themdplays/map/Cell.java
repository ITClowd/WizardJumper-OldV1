package de.themdplays.map;

/**
 * Created by Moritz on 23.12.2016.
 */
public class Cell {

    private Tile tile;

    public Cell(Tile tile) {
        this.tile = tile;
    }

    /**
     * Returns the tile of the Cell
     * @return tile
     */
    public Tile getTile() {
        return tile;
    }

}
