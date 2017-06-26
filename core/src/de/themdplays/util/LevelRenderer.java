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

    public LevelRenderer() {
        this.zoom = 2;
    }

    /**
     * Renders a WizardJumperMap
     * @param batch
     */
    public void render(SpriteBatch batch, WizardJumperMap wjm) {
        float tileSize = Constants.TILE_SIZE*zoom; //for better performance here not in draw
        Texture emptytexture = new Texture(new Pixmap((int)tileSize, (int)tileSize, Pixmap.Format.Alpha));

        for(int y = 0; y<wjm.getHeight(); y++) {
            for(int x = 0; x<wjm.getWidth(); x++) {
                if(wjm.getCells()[y][x] != null) {
                    int id = wjm.getCells()[y][x].getTile().getID();
                    if(id != 0)
                        batch.draw(WizardJumper.assetsHandler.getBlocks().get(id).createSprites().first(), x*tileSize, y*tileSize, tileSize, tileSize);
                    else batch.draw(emptytexture, x*tileSize, y*tileSize, tileSize, tileSize);
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
}
