package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moritz on 16.12.2016.
 */
public class LevelHandler {

    private List<TiledMap> userMaps, gameMaps;

    public LevelHandler() {
        userMaps = new ArrayList<TiledMap>();
        gameMaps = new ArrayList<TiledMap>();
        loadGameLevels();
        loadUserMaps();
    }

    /**
     * Loads all Game internal maps
     */
    private void loadGameLevels() {
        Gdx.app.log("INFO", "Loading Game Levels");
        for(FileHandle f : Gdx.files.local("levels").list()) {
            if(f.extension().equalsIgnoreCase("tmx")) gameMaps.add(new TmxMapLoader().load(f.path()));
            Gdx.app.log("Loaded", f.nameWithoutExtension());
        }
        Gdx.app.log("INFO", "Finished Loading Game Levels");
    }


    /**
     * Loads all user created maps
     */
    private void loadUserMaps() {
        Gdx.app.log("INFO", "Loading User Levels");
        for(FileHandle f : Gdx.files.local("levels").list()) {
            if(f.extension().equalsIgnoreCase("wjm")) userMaps.add(new TmxMapLoader().load(f.path()));
            Gdx.app.log("Loaded", f.nameWithoutExtension());
        }
        Gdx.app.log("INFO", "Finished Loading User Levels");
    }

}
