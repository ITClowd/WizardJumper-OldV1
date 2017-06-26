package de.themdplays.map;

/**
 * Created by Moritz on 23.12.2016.
 */
public enum Tile {
    AIR(0),
    DIRT(1),
    STONE(2);

    int id;

    Tile(int id) {
        this.id=id;
    }

    /**
     * @return id
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the name of an Tile by its id
     * @param code
     * @return
     */
    public static Tile getNameByCode(int code){
        for(Tile e : Tile.values()){
            if(code == e.id) return e;
        }
        return null;
    }
}
