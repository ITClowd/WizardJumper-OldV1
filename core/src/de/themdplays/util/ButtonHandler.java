package de.themdplays.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Moritz on 02/03/2017.
 */
public class ButtonHandler {
    /**
     * Adds a button to a table with calculated size
     * @param button Button
     * @param table Table
     */
    public static void addButtonToTable(TextButton button, Table table) {
        table.add(button).height(Gdx.graphics.getHeight()/15).width(Gdx.graphics.getHeight()/15*
                (Assets.manager.get(Assets.menuAtlas).findRegion("button0").getRegionWidth()/Assets.manager.get(Assets.menuAtlas).findRegion("button0").getRegionHeight()))
                .spaceBottom(Gdx.graphics.getHeight()/60).row();
    }

    /**
     * Adds a clicklistener to a button with transition between target screen
     * @param button Target Button
     * @param stage Stage to add to
     * @param screen Target screen
     */
    public static void addClicklistener(TextButton button, final Stage stage, final Screen screen) {
        button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
                    }
                })));

            }

        });
    }

    /**
     * Changes the screen to the previous one with transition
     * @param screen
     * @param stage
     */
    public static void backFunc(final Screen screen, Stage stage) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            stage.addAction(Actions.sequence(Actions.fadeOut(.25f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    backFunc(screen);
                }
            })));
    }

    /**
     * Changes the screen to the previous one without transition
     * @param screen
     */
    public static void backFunc(final Screen screen) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }

}
