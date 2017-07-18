package de.themdplays.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.screens.menu.MainMenu;
import de.themdplays.util.ButtonHandler;
import de.themdplays.util.LevelRenderer;

public class Play implements Screen {

    private WizardJumperMap map;
    private LevelRenderer levelRenderer;

    private SpriteBatch batch;



    public Play(WizardJumperMap map) {
        this.map = map;
    }

    @Override
    public void show() {
        levelRenderer = new LevelRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        levelRenderer.render(batch, map);
        batch.end();

        ButtonHandler.backFunc(new MainMenu());
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

    @Override
    public void dispose() {

    }
    //</editor-fold>
}
