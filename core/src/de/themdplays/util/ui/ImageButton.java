package de.themdplays.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.themdplays.screens.Editor;

/**
 * Created by Moritz on 11/04/2017.
 */
public class ImageButton extends Actor {

    private Sprite look;
    private EditorTools editorTool;

    public ImageButton(Sprite sprite, final EditorTools editorTool) {
        this.look = sprite;
        setWidth(Gdx.graphics.getHeight()*0.05f);
        setHeight(Gdx.graphics.getHeight()*0.05f);
        this.editorTool = editorTool;
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Editor.setTool(editorTool);
            }
        });
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(look, getX(), getY(), getWidth(), getHeight());

        if(Editor.getCurrentTool() == editorTool) {
            batch.draw(new Sprite(new Texture(Gdx.files.internal("ui/Editor_Selected.png"))),  getX(), getY(), getWidth(), getHeight());

        }
    }

}
