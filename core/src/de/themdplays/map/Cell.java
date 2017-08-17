package de.themdplays.map;

import com.badlogic.gdx.math.Vector2;
import de.themdplays.util.Location;

/**
 * Created by Moritz on 23.12.2016.
 */
public class Cell {

    private Tile tile;
    private Vector2 location;

    public Cell(Tile tile, Vector2 location) {
        this.tile = tile;
        this.location = location;
    }

    /**
     * Returns the tile of the Cell
     * @return tile
     */
    public Tile getTile() {
        return tile;
    }

    public Vector2 getLocation() {
        return location;
    }
}
