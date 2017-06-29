package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.themdplays.main.WizardJumper;
import de.themdplays.map.Tile;
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
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    private void initButtons() {
        eraser = new ToolButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Eraser"), EditorTools.ERASER);
        fill = new ToolButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Fill"), EditorTools.FILL);
        pencil = new ToolButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Pencil"), EditorTools.PENCIL);
        save = new ToolButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Save"), EditorTools.SAVE);

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
        for(Tile tile : Tile.values()) {
            if(tile.getID() != 0) chooser.add(new BlockButton(WizardJumper.assetsHandler.getBlocks().get(tile.getID()).createSprites().first(), tile)).space(0, eraser.getHeight()*0.1f, 0, eraser.getHeight()*0.1f);
        }
    }

}
