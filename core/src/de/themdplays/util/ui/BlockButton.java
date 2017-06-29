package de.themdplays.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.themdplays.map.Tile;
import de.themdplays.screens.Editor;

/**
 * Created by Moritz on 29/06/2017.
 */
public class BlockButton extends ImageButton {

    private Tile block;

    public BlockButton(Sprite sprite, final Tile block) {
        super(sprite);
        this.block = block;
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Editor.setCurrentTile(block);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(block == Editor.getCurrentTile()) {
            batch.draw(new Sprite(new Texture(Gdx.files.internal("ui/Editor_Selected.png"))),  getX(), getY(), getWidth(), getHeight());
        }
    }
}
