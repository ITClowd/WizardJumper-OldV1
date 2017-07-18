package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.themdplays.map.Tile;
import de.themdplays.screens.Editor;
import de.themdplays.screens.menu.MainMenu;
import de.themdplays.util.ui.BlockButton;
import de.themdplays.util.ui.EditorTools;
import de.themdplays.util.ui.ToolButton;

/**
 * Created by Moritz on 30.12.2016.
 */
public class EditorUIRenderer {
    private Stage stage;

    private ToolButton eraser, fill, pencil, save;
    private Table buttons, chooser;

    private Window w_save;

    //SAVE WINDOW STUFF
    private TextField w_name;
    private TextButton w_bsave;


    public EditorUIRenderer() {
        stage = new Stage();

        //INIT TABLE
        buttons = new Table();
        buttons.setPosition(Gdx.graphics.getHeight()*0.04f, Gdx.graphics.getHeight()*0.5f);

        chooser = new Table();
        chooser.setPosition(Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getWidth()*0.04f);


        stage.addActor(buttons);
        stage.addActor(chooser);
        initButtons();
        initChooser();

        Gdx.input.setInputProcessor(stage);

        initSaveWindow();


    }

    private void initSaveWindow() {

        w_name = new TextField("", Assets.manager.get(Assets.menuSkin, Skin.class));

        w_bsave = new TextButton("Save", Assets.manager.get(Assets.menuSkin, Skin.class), "big");

        w_bsave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Editor.saveMap(w_name.getText());
            }
        });

        w_save = new Window("Save", Assets.manager.get(Assets.menuSkin, Skin.class));

        w_save.setSize(Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getHeight()*0.8f);
        w_save.setPosition(Gdx.graphics.getWidth()*.5f-w_save.getWidth()*.5f, Gdx.graphics.getHeight()*.5f-w_save.getHeight()*.5f);
        w_save.padTop(Constants.FONT_SIZE_BIG*1.2f);


        w_save.getTitleTable().setWidth(Gdx.graphics.getWidth()*0.8f);

        w_name.setSize(Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getHeight()*0.8f);
        w_name.setAlignment(Align.center);

        w_save.setMovable(false);
        w_save.add(new Label("Name", Assets.manager.get(Assets.menuSkin, Skin.class), "big")).row();
        w_save.add(w_name).row();
        w_save.add(w_bsave).align(Align.bottom);
        w_save.getTitleLabel().setAlignment(Align.center);
        w_save.setVisible(false);

        stage.addActor(w_save);
    }

    public void render(SpriteBatch batch) {
        w_save.setVisible(Editor.getCurrentTool() == EditorTools.SAVE);
        stage.act();
        stage.draw();

        //BACK FUNCTION
        ButtonHandler.backFunc(new MainMenu(), stage);
    }

    private void initButtons() {
        eraser = new ToolButton(Assets.manager.get(Assets.editorAtlas).createSprite("Eraser"), EditorTools.ERASER);
        fill = new ToolButton(Assets.manager.get(Assets.editorAtlas).createSprite("Fill"), EditorTools.FILL);
        pencil = new ToolButton(Assets.manager.get(Assets.editorAtlas).createSprite("Pencil"), EditorTools.PENCIL);
        save = new ToolButton(Assets.manager.get(Assets.editorAtlas).createSprite("Save"), EditorTools.SAVE);

        buttons.setWidth(eraser.getWidth());

        //BUTTONS
        buttons.add(pencil).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);
        buttons.row();
        buttons.add(fill).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);
        buttons.row();
        buttons.add(eraser).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);
        buttons.row();
        buttons.add(save).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);
    }

    private void initChooser() {
        chooser.setHeight(eraser.getHeight());
        for(Tile tile : Tile.values()) {
            if(tile.getID() != 0) chooser.add(new BlockButton(Assets.manager.get(Assets.blocksAtlas).createSprite(tile.name().toLowerCase()), tile)).space(0, eraser.getHeight()*0.1f, 0, eraser.getHeight()*0.1f);
        }
    }

    public Table getButtons() {
        return buttons;
    }

    public Table getChooser() {
        return chooser;
    }

    public void dispose() {
        stage.dispose();
    }
}
