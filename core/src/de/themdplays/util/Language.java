package de.themdplays.util;

public enum Language {

	GERMAN("de"),
	ENGLISH("en");
	
	private String key;
	
	private Language(String key) {
		this.key = key;
	}

    /**
     * @return key
     */
	public String getKey() {
		return key;
	}
	
}
