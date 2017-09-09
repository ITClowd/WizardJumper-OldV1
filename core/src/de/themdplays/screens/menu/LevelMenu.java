package de.themdplays.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.themdplays.screens.Play;
import de.themdplays.util.ButtonHandler;
import de.themdplays.util.LevelHandler;
import de.themdplays.util.ui.ImageButton;

public class LevelMenu implements Screen {

    private Stage stage;
    private ImageButton story, user;


    private LevelHandler levelHandler;

    @Override
    public void show() {
        stage = new Stage();
        levelHandler = new LevelHandler();

        ((Game) Gdx.app.getApplicationListener()).setScreen(new Play(levelHandler.getUserMaps().get(0)));
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        ButtonHandler.backFunc(new MainMenu(), stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    //<editor-fold desc="Unnecessary override stuff">
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
    //</editor-fold>
}
