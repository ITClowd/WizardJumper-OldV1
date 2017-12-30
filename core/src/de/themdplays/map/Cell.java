package de.themdplays.map;

import com.badlogic.gdx.math.Vector2;
import de.themdplays.util.Location;

import java.awt.*;

/**
 * Created by Moritz on 23.12.2016.
 */
public class Cell {

    private Tile tile;
    private Point location;
    private int tileVariation = 0;

    public Cell(Tile tile, Point location) {
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

    public Point getLocation() {
        return location;
    }

    public void setTileVariation(int tileVariation) {
        this.tileVariation = tileVariation;
    }

    public int getTileVariation() {
        return tileVariation;
    }
}
