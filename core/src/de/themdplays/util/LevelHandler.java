package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import de.themdplays.map.WizardJumperMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moritz on 16.12.2016.
 */
public class LevelHandler {

    private List<WizardJumperMap> userMaps, gameMaps;

    public LevelHandler() {
        userMaps = new ArrayList<WizardJumperMap>();
        gameMaps = new ArrayList<WizardJumperMap>();
        loadGameLevels();
        loadUserMaps();
    }

    /**
     * Loads all Game internal maps
     */
    private void loadGameLevels() {
        Gdx.app.log("INFO", "Loading Game Levels");
        for(FileHandle f : Gdx.files.local("levels").list()) {
            if(f.extension().equalsIgnoreCase("wjm")) {
                gameMaps.add(new WizardJumperMap(f));
                Gdx.app.log("Loaded", f.nameWithoutExtension());
            }
        }
        Gdx.app.log("INFO", "Finished Loading Game Levels");
    }


    /**
     * Loads all user created maps
     */
    private void loadUserMaps() {
        Gdx.app.log("INFO", "Loading User Levels");
        for(FileHandle f : Gdx.files.local("maps").list()) {
            if(f.extension().equalsIgnoreCase("wjm")) {
                gameMaps.add(new WizardJumperMap(f));
                Gdx.app.log("Loaded", f.nameWithoutExtension());
            }
        }
        Gdx.app.log("INFO", "Finished Loading User Levels");
    }

}
