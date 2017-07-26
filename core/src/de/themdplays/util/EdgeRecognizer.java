package de.themdplays.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import de.themdplays.map.Cell;
import de.themdplays.map.Tile;
import de.themdplays.map.WizardJumperMap;

/**
 * Created by moritz on 25.07.17.
 */
public class EdgeRecognizer {

    private static Tile target;

    public static Sprite getSprite(Cell[][] cells, int x, int y) {

        target = cells[y][x].getTile();

        TextureAtlas blockatlas = Assets.manager.get(Assets.blocksAtlas);

        if(target.isEdge()) {
            String targetname = target.name().toLowerCase();
            if(isSingle(cells, x, y, target))
                return blockatlas.createSprite(targetname, 17);
            boolean left = isLeftSame(cells, x, y);
            boolean top = isTopSame(cells, x, y);
            boolean right = isRightSame(cells, x, y);
            boolean bottom = isBottomSame(cells, x, y);
            boolean topleft = isTopLeftSame(cells, x, y);
            boolean topright = isTopRightSame(cells, x, y);
            boolean bottomleft = isBottomLeftSame(cells, x, y);
            boolean bottomright = isBottomRightSame(cells, x, y);

            /*
             * SINGLE
             */

            if(left && !right && !top && !bottom) return blockatlas.createSprite(targetname, 18);
            if(right && !left && !top && !bottom) return blockatlas.createSprite(targetname, 9);
            if(top && !right && !left && !bottom) return blockatlas.createSprite(targetname, 12);
            if(bottom && !right && !top && !left) return blockatlas.createSprite(targetname, 19);
            if(left && right && !top && !bottom) return blockatlas.createSprite(targetname, 10);
            if(top && bottom && !left && !right) return blockatlas.createSprite(targetname, 11);
            //corner
            if(bottom && right && !top && !left && !bottomright) return blockatlas.createSprite(targetname, 13);
            if(bottom && left && !top && !right && !bottomleft) return blockatlas.createSprite(targetname, 14);
            if(top && left&& !bottom && !right && !topleft) return blockatlas.createSprite(targetname, 15);
            if(top && right && !bottom && !left && !topright) return blockatlas.createSprite(targetname, 16);
            //t parts
            if(top && bottom && right && !left && !topright && !bottomright) return blockatlas.createSprite(targetname, 21);
            if(bottom && left && right && !top && !bottomleft && !bottomright) return blockatlas.createSprite(targetname, 22);
            if(top && bottom && left && !right && !topleft && !bottomleft) return blockatlas.createSprite(targetname, 23);
            if(top && left && right && !bottom && !topleft && !topright) return blockatlas.createSprite(targetname, 24);
            //middle
            if(top && right && left && bottom && !topleft && !topright && !bottomleft && !bottomright) return blockatlas.createSprite(targetname, 20);

            /*
             * MULTI
             */
            if(bottom && left && right && !top) return blockatlas.createSprite(targetname, 1);
            if(right && top && bottom && !left) return blockatlas.createSprite(targetname, 2);
            if(left && top && bottom && !right) return blockatlas.createSprite(targetname, 3);
            if(top && left && right && !bottom) return blockatlas.createSprite(targetname, 4);
            //corner
            if(bottom && right && !top && !left && bottomright) return blockatlas.createSprite(targetname, 5);
            if(bottom && left && !top && !right && bottomleft) return blockatlas.createSprite(targetname, 6);
            if(top && left && !bottom && !right && topleft) return blockatlas.createSprite(targetname, 7);
            if(top && right && !bottom && !left && topright) return blockatlas.createSprite(targetname, 8);

            return blockatlas.createSprite(targetname, 0);
        }
        else return blockatlas.createSprite(target.name().toLowerCase());
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @param target target cell
     * @return whether the Tile is surrounded by other tiles or not
     */
    private static boolean isSingle(Cell[][] cells, int x, int y, Tile target) {
        return !isLeftSame(cells, x, y) && !isRightSame(cells, x, y) && !isTopSame(cells, x, y)&& !isBottomSame(cells, x, y);
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isSame(Cell[][] cells, int x, int y) {
        return !(x>=0&&y>=0&&y<cells.length&&x<cells[cells.length-1].length)?false:cells[y][x].getTile() != Tile.AIR;
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isLeftSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x-1, y);
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isRightSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x+1, y);
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isTopSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x, y+1);
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isBottomSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x, y-1);
    }


    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isTopLeftSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x-1, y+1);
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isTopRightSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x+1, y+1);
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isBottomRightSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x+1, y-1);
    }

    /**
     * @param cells map cells
     * @param x posx
     * @param y posy
     * @return whether the tile at x and y is the same as target
     */
    private static boolean isBottomLeftSame(Cell[][] cells, int x, int y) {
        return isSame(cells, x-1, y-1);
    }


}
