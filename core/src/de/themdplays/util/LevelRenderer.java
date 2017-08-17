package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import de.themdplays.map.Tile;
import de.themdplays.map.WizardJumperMap;

/**
 * Created by Moritz on 31.12.2016.
 */
public class LevelRenderer {

    private float zoom;

    private Location mapLoc;

    private ShapeRenderer shapeRenderer;

    public LevelRenderer() {
        this.zoom = 2;

        this.mapLoc = new Location(0,0);

        this.shapeRenderer = new ShapeRenderer();
    }

    /**
     * Renders a WizardJumperMap
     * @param batch
     */
    public void render(SpriteBatch batch, WizardJumperMap wjm, boolean toBox2D) {

        float tileSize = Constants.TILE_SIZE*zoom / (toBox2D?Constants.PIXELS_TO_METERS:1);
//        Pixmap m = new Pixmap((int)tileSize, (int)tileSize, Pixmap.Format.Alpha);

        Pixmap m = new Pixmap((int)tileSize, (int)tileSize, Pixmap.Format.RGB888);
        m.setColor(Color.ROYAL);
        m.fill();
        Texture emptyTexture = new Texture(m);

        for(int y = 0; y<wjm.getHeight(); y++) {
            for(int x = 0; x<wjm.getWidth(); x++) {
                if(wjm.getCells()[y][x] != null) {
                    Tile tile = wjm.getCells()[y][x].getTile();
                    if(tile != Tile.AIR)
                        batch.draw(EdgeRecognizer.getSprite(wjm.getCells(), x, y), x*tileSize+mapLoc.getX(), y*tileSize+mapLoc.getY(), tileSize, tileSize);
                    else batch.draw(emptyTexture, x*tileSize+mapLoc.getX(), y*tileSize+mapLoc.getY(), tileSize, tileSize);
                } else {
                    Gdx.app.log("LevelDraw", "Cells null");
                }
            }
        }

//        drawDashedLines(batch.getProjectionMatrix(), wjm, tileSize);

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

    /**
     * Draws dashed lines when in editor
     */
    public void drawDashedLines(Matrix4 projectionmatrix, WizardJumperMap wjm, float tileSize) {
        shapeRenderer.setProjectionMatrix(projectionmatrix);
        for(int i = 0; i<Gdx.graphics.getWidth()/tileSize; i++) {
//            shapeRenderer.line(i*tileSize+(mapLoc.getX()%tileSize), 0, i*tileSize+(mapLoc.getX()%tileSize), Gdx.graphics.getHeight());
            drawDottedLine(shapeRenderer, (int)tileSize/4, i*tileSize+(mapLoc.getX()%tileSize), mapLoc.getY()%tileSize, i*tileSize+(mapLoc.getX()%tileSize), Gdx.graphics.getHeight());
        }
        for(int i = 0; i<Gdx.graphics.getHeight()/tileSize; i++) {
//            shapeRenderer.line(0, i*tileSize+(mapLoc.getY()%tileSize), Gdx.graphics.getWidth(), i*tileSize+(mapLoc.getY()%tileSize));
            drawDottedLine(shapeRenderer, (int)tileSize/4, mapLoc.getX()%tileSize, i*tileSize+(mapLoc.getY()%tileSize), Gdx.graphics.getWidth(), i*tileSize+(mapLoc.getY()%tileSize));
        }
        shapeRenderer.end();
    }

    /**
     * Draws a dotted line between to points (x1,y1) and (x2,y2).
     * @param shapeRenderer
     * @param dotDist (distance between dots)
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private void drawDottedLine(ShapeRenderer shapeRenderer, int dotDist, float x1, float y1, float x2, float y2) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for(int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.point(x1 + vec2.x, y1 + vec2.y, 0);
        }

        shapeRenderer.end();
    }
}
