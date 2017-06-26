package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;

public class Settings {

	private boolean vsync;
	
	public Settings() {
		if(Gdx.files.local("settings.ini").exists()) {
			load();
		} else {
			Gdx.files.local("settings.ini").writeString(
					Gdx.files.internal("defaultsettings.ini").readString(), false);
			load();
			
		}
	}

    /**
     * Loads the settings form file
     */
	private void load() {
		//READ DATA
		JsonReader r = new JsonReader();
		vsync = r.parse(Gdx.files.local("settings.ini")).get("Graphics").getBoolean("vsync");
		
		//APPLY PATCHES
		Gdx.graphics.setVSync(vsync);
		Gdx.app.log("Settings", "VSync: " + vsync);
		
	}
	
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
	}
	
	public boolean isVSync() {
		return vsync;
	}
	
	public void save() {

	}
	
}
