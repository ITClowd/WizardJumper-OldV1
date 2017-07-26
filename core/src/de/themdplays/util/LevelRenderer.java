package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.themdplays.map.Tile;
import de.themdplays.map.WizardJumperMap;

/**
 * Created by Moritz on 31.12.2016.
 */
public class LevelRenderer {

    private float zoom;

    private Location mapLoc;

    public LevelRenderer() {
        this.zoom = 2;

        this.mapLoc = new Location(0,0);
    }

    /**
     * Renders a WizardJumperMap
     * @param batch
     */
    public void render(SpriteBatch batch, WizardJumperMap wjm, boolean toBox2D) {

        float tileSize = Constants.TILE_SIZE*zoom / (toBox2D?Constants.PIXELS_TO_METERS:1);
//        Pixmap m = new Pixmap((int)tileSize, (int)tileSize, Pixmap.Format.Alpha);

        Pixmap m = new Pixmap((int)tileSize, (int)tileSize, Pixmap.Format.RGB888);
        m.setColor(Color.RED);
        m.fill();
        Texture emptytexture = new Texture(m);

        for(int y = 0; y<wjm.getHeight(); y++) {
            for(int x = 0; x<wjm.getWidth(); x++) {
                if(wjm.getCells()[y][x] != null) {
                    Tile tile = wjm.getCells()[y][x].getTile();
                    if(tile != Tile.AIR)
                        batch.draw(EdgeRecognizer.getSprite(wjm.getCells(), x, y), x*tileSize+mapLoc.getX(), y*tileSize+mapLoc.getY(), tileSize, tileSize);
                    else batch.draw(emptytexture, x*tileSize+mapLoc.getX(), y*tileSize+mapLoc.getY(), tileSize, tileSize);
                } else {
                    Gdx.app.log("LevelDraw", "Cells null");
                }
            }
        }
    }

    /**
     * Sets the current Zoom of the {@link LevelRenderer}
     * @param zoom
     */
    public void setZoom(float zoom) {
        if(zoom<1) this.zoom=1;
        //TODO ADD ZOOM LIMIT
        else this.zoom = zoom;
    }

    /**
     * @return float zoom
     */
    public float getZoom() {
        return zoom;
    }

    /**
     * Sets the location where the map should be rendered
     * @param mapLoc {@link Location}
     */
    public void setMapLoc(Location mapLoc) {
        this.mapLoc = mapLoc;
    }

    /**
     * @return {@link Location}
     */
    public Location getMapLoc() {
        return mapLoc;
    }
}
