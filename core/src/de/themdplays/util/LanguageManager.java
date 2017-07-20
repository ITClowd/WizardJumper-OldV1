package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;

public class LanguageManager {

	private Language lang;

    /**
     * Handles the language system
     * @param lang
     */
	public LanguageManager(Language lang) {
		this.lang = lang;
	}

    /**
     * Changes the language
     * @param lang
     */
	public void changeLanguage(Language lang) {
		this.lang = lang;
	}

    /**
     * Returns an element of the current language
     * @param key
     * @return
     */
	public String get(String key) {
		JsonReader r = new JsonReader();
		return r.parse(Gdx.files.internal("lang/" + lang.getKey() + ".lang")).getString(key);
	}
	
	
}
