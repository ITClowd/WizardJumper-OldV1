package de.themdplays.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.themdplays.main.WizardJumper;
import de.themdplays.util.LevelHandler;

public class LevelMenu implements Screen {

	private Stage stage;
	private Table table;
	private Skin skin;
	private ScrollPane pane;
	
	@Override
	public void show() {
		stage = new Stage();
		skin = WizardJumper.assetsHandler.getMenuSkin();
        table = new Table();
        pane = new ScrollPane(table);


		LevelHandler l = new LevelHandler();
	}

	@Override
	public void render(float delta) {
		//CLEAR SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		//RENDERING MENU
		stage.act();
		stage.draw();
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
		stage.dispose();
        skin.dispose();
	}

}
