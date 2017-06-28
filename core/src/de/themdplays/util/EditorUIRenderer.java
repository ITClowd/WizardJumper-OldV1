package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.themdplays.main.WizardJumper;
import de.themdplays.util.ui.EditorTools;
import de.themdplays.util.ui.ImageButton;

/**
 * Created by Moritz on 30.12.2016.
 */
public class EditorUIRenderer {


    private Stage stage;

    private ImageButton eraser, fill, pencil, save;
    private Table table;

    public EditorUIRenderer() {
        stage = new Stage();
        table = new Table();
        table.setPosition(Gdx.graphics.getHeight()*0.04f, Gdx.graphics.getHeight()*0.5f);
        stage.addActor(table);
        initButtons();
        Gdx.input.setInputProcessor(stage);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    private void initButtons() {
        eraser = new ImageButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Eraser"), EditorTools.ERASER);
        fill = new ImageButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Fill"), EditorTools.FILL);
        pencil = new ImageButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Pencil"), EditorTools.PENCIL);
        save = new ImageButton(WizardJumper.assetsHandler.getA_editor_UI().createSprite("Save"), EditorTools.SAVE);

        table.add(pencil).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);
        table.row();
        table.add(fill).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);
        table.row();
        table.add(eraser).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);
        table.row();
        table.add(save).spaceBottom(eraser.getHeight()+eraser.getHeight()*0.1f);

    }

    private void initChooser() {

    }

}
