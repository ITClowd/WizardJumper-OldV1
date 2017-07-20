package de.themdplays.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import de.themdplays.screens.menu.MainMenu;

public class Splash implements Screen {

	@Override
	public void show() {
		//TODO Adding Splash
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu()); //DEBUG SWITCH TO MAINMENU
	}

	@Override
	public void render(float delta) {
		
	}

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

}
