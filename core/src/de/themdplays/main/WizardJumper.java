package de.themdplays.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import de.themdplays.screens.Splash;
import de.themdplays.util.*;

public class WizardJumper extends Game {

    public static final String TITLE = "WizardJumper", VERSION = "0.0.0.2 alpha";

    public static LanguageManager langManager;
    public static Settings settings;

    @Override
    public void create () {
        langManager = new LanguageManager(LanguageManager.Language.ENGLISH);
        settings = new Settings();
        //INIT ASSETS
        initDir();
        Assets.load();
        while(!Assets.manager.update()) {
//            Gdx.app.log("Assets", "Loaded: " + Assets.manager.getProgress()*100 + "%");
        }
        //Setting SplashScreen
        setScreen(new Splash());
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    /**
     * Initializes all directories
     */
    private void initDir() {
        if(!Gdx.files.local("maps").exists()) Gdx.files.local("maps/").mkdirs();
    }
}
