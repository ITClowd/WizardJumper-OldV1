package de.themdplays.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.awt.*;
import java.util.HashMap;


public class WJMap {

    private String name = "";
    private HashMap<Integer, Cell> cellHash;

    public WJMap() {
        this.cellHash = new HashMap<>();
    }

    public WJMap(FileHandle fileHandle, String name) {
        this.name = name;
        load(fileHandle);
    }

    public void save(String name) {
        String compressed = "";
        for(Cell c: cellHash.values()) {
            compressed += c.getTile().id + ";"
                    + c.getTileVariation() + ";"
                    + c.getLocation().x + ";"
                    + c.getLocation().y + ",\n";
        }
        Gdx.files.local("maps/" + name + ".wjm").writeString(compressed, false);
    }

    private void load(FileHandle fileHandle) {
        cellHash = new HashMap<>();

        String compressed = fileHandle.readString();
        String[] cells = compressed.split(",\n");

        for(String rawCellData : cells) {
            String[] cellData = rawCellData.split(";");

            Tile tile = Tile.getNameByCode(Integer.parseInt(cellData[0]));
            int tileVariation = Integer.parseInt(cellData[1]);
            Point location = new Point(Integer.parseInt(cellData[2]), Integer.parseInt(cellData[3]));

            cellHash.put(getCellKey(location), new Cell(tile, location, tileVariation));
        }
    }

    private int getCellKey(Point p) {
        return ( p.y << 16 ) ^ p.x;
    }

    public void addCell(Cell c) {
        int key = getCellKey(c.getLocation());
        if(cellHash.containsKey(key))
            cellHash.replace(key, c);
        else cellHash.put(key, c);

    }

    public void removeCell(Cell c) {
        int key = getCellKey(c.getLocation());
        if(cellHash.containsKey(key))
            cellHash.remove(key);
    }

    public Cell getCell(Point p) {
        int key = ( p.y << 16 ) ^ p.x;
        if(cellHash.containsKey(key)) {
            return cellHash.get(key);
        } else return new Cell(Tile.AIR, p);
    }

    public Cell getCell(int x, int y) {
        return getCell(new Point(x, y));
    }

    /*
     *  GETTER AND SETTER
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Cell> getCellHash() {
        return cellHash;
    }

    public void setCellHash(HashMap<Integer, Cell> cellHash) {
        this.cellHash = cellHash;
    }

}
