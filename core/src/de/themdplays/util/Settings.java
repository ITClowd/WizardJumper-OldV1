package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class Settings {

	private boolean vSync;
	
	public Settings() {
		if(Gdx.files.local("settings.json").exists()) {
			load();
		} else {
		    Json json = new Json();
		    json.setOutputType(JsonWriter.OutputType.json);
			Gdx.files.local("settings.json").writeString(json.prettyPrint(newSettingsDescriptor()), false);
			load();
		}
	}

    /**
     * Loads the settings form file
     */
	private void load() {
		//READ DATA
        Json json = new Json();
		SettingsDescriptor descriptor = json.fromJson(SettingsDescriptor.class, Gdx.files.local("settings.json"));
		//APPLY PATCHES
		Gdx.graphics.setVSync(descriptor.vSync);
        Gdx.app.log("Settings", "VSync: " + vSync);
	}

    /**
     * Sets Vsync
     * @param vsync
     */
	public void setVSync(boolean vsync) {
		this.vSync = vsync;
		Gdx.graphics.setVSync(vsync);
	}
	
	public boolean isVSync() {
		return vSync;
	}
	
	public void save() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        Gdx.files.local("settings.json").writeString(json.prettyPrint(newSettingsDescriptor()), false);
	}

    /**
     * creates a SettingsDescriptor object
     * @return SettingsDescriptor
     */
	public SettingsDescriptor newSettingsDescriptor() {
	    SettingsDescriptor descriptor = new SettingsDescriptor();

        descriptor.vSync = false;
        descriptor.fullscreen = false;

	    return descriptor;
	}

    /**
     *  Class to save Settings
     */
	public static class SettingsDescriptor {
	    boolean fullscreen;
	    boolean vSync;
    }
	
}
