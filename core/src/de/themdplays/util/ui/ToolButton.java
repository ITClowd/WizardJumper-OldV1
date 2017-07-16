package de.themdplays.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.themdplays.screens.Editor;

/**
 * Created by Moritz on 29/06/2017.
 */
public class ToolButton extends ImageButton {

    private EditorTools editorTool;

    public ToolButton(Sprite sprite, final EditorTools editorTool) {
        super(sprite);
        this.editorTool = editorTool;

        setSize(Gdx.graphics.getHeight()*0.05f, Gdx.graphics.getHeight()*0.05f);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Editor.setTool(editorTool);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(editorTool == Editor.getCurrentTool()) {
            batch.draw(new Sprite(new Texture(Gdx.files.internal("ui/Editor_Selected.png"))),  getX(), getY(), getWidth(), getHeight());
        }
    }
}
