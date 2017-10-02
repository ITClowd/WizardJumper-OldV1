package de.themdplays.screens.menu.Options;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.themdplays.main.WizardJumper;
import de.themdplays.util.Assets;

public class O_Graphics implements Screen {

	private Stage stage;
    private Skin skin;

    private CheckBox vsync;

    private TextButton b_back;

	@Override
	public void show() {

	    //TODO REFACTORING

		//DECLARING STUFF
		stage = new Stage();

        skin = Assets.manager.get(Assets.menuSkin);

        Table table = new Table(skin);

		Gdx.input.setInputProcessor(stage);
		
		Label vsynclabel = new Label("VSync", skin);
		Label sampletext = new Label("sampletext", skin);

		vsync = new CheckBox("", skin);
		vsync.setChecked(WizardJumper.settings.isVSync());
		
		vsync.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				WizardJumper.settings.setVSync(vsync.isChecked());
				Gdx.app.log("VSYNC", "" + WizardJumper.settings.isVSync());
			}
			
		});
		
		b_back = new TextButton(WizardJumper.langManager.get("back"), skin);
		
		b_back.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Options());
			}
			
		});
		
		table.setFillParent(true);
		table.top();
		
		int dif = Gdx.app.getGraphics().getWidth()/4;
		
		table.add().spaceBottom(50).row();
        table.add(WizardJumper.langManager.get("graphics"), "big").spaceBottom(100).row();
        table.add(vsynclabel).spaceBottom(100).spaceRight(dif);
		table.add(vsync).spaceBottom(100).spaceLeft(dif).row();
		table.add(b_back);

		ScrollPane pane = new ScrollPane(table);
		pane.setFillParent(true);
		pane.setOverscroll(true, true);

		pane.debugAll();

		stage.addActor(pane);
	}

	@Override
	public void render(float delta) {
		//CLEAR SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//RENDER MENU
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
