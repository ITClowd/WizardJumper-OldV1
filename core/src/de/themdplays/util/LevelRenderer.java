package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.themdplays.main.WizardJumper;
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
    public void render(SpriteBatch batch, WizardJumperMap wjm) {

        float tileSize = Constants.TILE_SIZE*zoom; //for better performance here not in draw
        Pixmap m = new Pixmap((int)tileSize, (int)tileSize, Pixmap.Format.Alpha);

//        Pixmap m = new Pixmap((int)tileSize, (int)tileSize, Pixmap.Format.RGB888);
//        m.setColor(Color.RED);
//        m.fill();
        Texture emptytexture = new Texture(m);

        for(int y = 0; y<wjm.getHeight(); y++) {
            for(int x = 0; x<wjm.getWidth(); x++) {
                if(wjm.getCells()[y][x] != null) {
                    int id = wjm.getCells()[y][x].getTile().getID();
                    if(id != 0)
                        batch.draw(WizardJumper.assetsHandler.getBlocks().get(id).createSprites().first(), x*tileSize+mapLoc.getX(), y*tileSize+mapLoc.getY(), tileSize, tileSize);
                    else batch.draw(emptytexture, x*tileSize+mapLoc.getX(), y*tileSize+mapLoc.getY(), tileSize, tileSize);
                } else {
                    Gdx.app.log("LevelDraw", "Cells null");
                }
            }
        }
    }

    public void setZoom(float zoom) {
        if(zoom<1) this.zoom=1;
        //TODO ADD ZOOM LIMIT
//        else if(Constants.TILE_SIZE*zoom>Gdx.graphics.getWidth()/(Constants.TILES_PER_WIDTH*Constants.TILE_SIZE)) this.zoom = (Gdx.graphics.getWidth()/(Constants.TILES_PER_WIDTH*Constants.TILE_SIZE))/Constants.TILE_SIZE;
        else this.zoom = zoom;
    }

    public float getZoom() {
        return zoom;
    }

    public void setMapLoc(Location mapLoc) {
        this.mapLoc = mapLoc;
    }

    public Location getMapLoc() {
        return mapLoc;
    }
}
