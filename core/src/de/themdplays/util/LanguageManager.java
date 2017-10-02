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
     * @param key
     * @return an element of the current language
     */
	public String get(String key) {
		JsonReader r = new JsonReader();
		return r.parse(Gdx.files.internal("lang/" + lang.getKey() + ".lang")).getString(key);
	}

	public enum Language {

		GERMAN("de"),
		ENGLISH("en");

		private String key;

		Language(String key) {
			this.key = key;
		}

		/**
		 * @return key
		 */
		public String getKey() {
			return key;
		}

	}

}
