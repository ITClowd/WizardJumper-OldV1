package de.themdplays.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import de.themdplays.entities.Player;
import de.themdplays.map.WizardJumperMap;
import de.themdplays.screens.menu.MainMenu;
import de.themdplays.util.ButtonHandler;
import de.themdplays.util.LevelRenderer;
import de.themdplays.util.Location;
import de.themdplays.util.WorldHelper;

public class Play implements Screen {

    private WizardJumperMap map;
    private LevelRenderer levelRenderer;

    private SpriteBatch batch;

    public static World world;
    private Box2DDebugRenderer debugRenderer;

    public static OrthographicCamera camera;

    private final float pixelsToMeters = 32;

    private final float TIMESTEP = 1/60f;
    private final int VELOCITYITERATIONS = 8, POSITIONIITERATIONS = 3;

    private Player player;

    public Play(WizardJumperMap map) {
        this.map = map;
    }

    @Override
    public void show() {
        levelRenderer = new LevelRenderer();
        levelRenderer.setZoom(1);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        world = new World(new Vector2(0, -9.81f*2), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(new Location(2, 5), world);

        new WorldHelper(map);
        System.out.println("Width:" + map.getWidth());
        System.out.println("Height:" + map.getHeight());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONIITERATIONS);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        levelRenderer.render(batch, map, true);
        player.render(batch, delta);
        batch.end();

        debugRenderer.render(world, camera.combined);
        ButtonHandler.backFunc(new MainMenu());
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width/25;
        camera.viewportHeight = height/25;

        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        debugRenderer.dispose();
    }

    //<editor-fold desc="Unnecessary override stuff">


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    //</editor-fold>
}
