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
        this.cellHash = new HashMap<Integer, Cell>();
    }

    public WJMap(FileHandle fileHandle, String name) {
        this.name = name;
        load(fileHandle);
    }

    public void save(String name) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        Gdx.files.local("maps/" + name + ".wjm").writeString(json.toJson(newMapDescriptor()), false);
    }

    public void load(FileHandle fileHandle) {
        //READ DATA
        Json json = new Json();
        MapDescriptor descriptor = json.fromJson(MapDescriptor.class, fileHandle);

        //APPLY PATCHES
        cellHash = descriptor.cellHash;
        name = descriptor.name;
    }

    public void addCell(Cell c) {
        int key = c.getLocation().x + (c.getLocation().y)*2;
        if(cellHash.containsKey(key))
            cellHash.replace(key, c);
        else cellHash.put(key, c);

    }

    public Cell getCell(Point p) {
        if(cellHash.containsKey(p.x + (p.y)*2)) {
            return cellHash.get(p.x + (p.y)*2);
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

    public MapDescriptor newMapDescriptor() {
        MapDescriptor m = new MapDescriptor();
        m.cellHash = this.cellHash;
        m.name = this.name;
        return m;
    }

    /**
     *  Class to save Map
     */
    public static class MapDescriptor {
        String name;
        HashMap<Integer, Cell> cellHash;
    }
}
