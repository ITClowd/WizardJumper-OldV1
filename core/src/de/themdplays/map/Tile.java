package de.themdplays.map;

/**
 * Created by Moritz on 23.12.2016.
 */
public enum Tile {
    AIR(0, false),
    DIRT(1, true),
    STONE(2, false);

    int id;
    boolean edge;

    Tile(int id, boolean edge) {
        this.id=id;
        this.edge = edge;
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

    public boolean isEdge() {
        return edge;
    }
}
