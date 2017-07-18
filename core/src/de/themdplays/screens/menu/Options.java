package de.themdplays.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import de.themdplays.main.WizardJumper;
import de.themdplays.util.Assets;
import de.themdplays.util.ButtonHandler;

public class Options implements Screen {

	private TextButton b_graphics, b_sound, b_game;
	private Skin skin;
    private Stage stage;

	@Override
	public void show() {
		//DECLARING STUFF
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		skin = Assets.manager.get(Assets.menuSkin);

		Table table = new Table();
		
		//INIT MENU
		registerButtons();
		
		//SETTING UP TABLE
		table.setFillParent(true);
		table.center();

		//adding buttons
        ButtonHandler.addButtonToTable(b_graphics, table);
        ButtonHandler.addButtonToTable(b_sound, table);
        ButtonHandler.addButtonToTable(b_game, table);
		
		//ADDING TABLE TO STAGE
		stage.addActor(table);
		
		//FADEINEFFECT
		stage.addAction(Actions.sequence(Actions.fadeOut(0), Actions.fadeIn(.25f)));
	}

	@Override
	public void render(float delta) {
		//CLEAR SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//RENDER MENU
		stage.act();
		stage.draw();

		//BACK FUNCTION
        ButtonHandler.backFunc(new MainMenu(), stage);
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
	
	/**
	 * Registers menu buttons
	 */
    private void registerButtons() {
		b_graphics = new TextButton(WizardJumper.langManager.get("graphics"), skin, "big");
		b_sound = new TextButton(WizardJumper.langManager.get("sounds"), skin, "big");
		b_game = new TextButton(WizardJumper.langManager.get("game"), skin, "big");

		//ADDING CLICKLISTENER
        ButtonHandler.addClicklistener(b_graphics, stage, new O_Graphics());
        ButtonHandler.addClicklistener(b_sound, stage, new O_Sounds());
        ButtonHandler.addClicklistener(b_game, stage, new O_Game());
	}
	
}
