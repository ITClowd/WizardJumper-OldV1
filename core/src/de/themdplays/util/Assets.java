package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Moritz on 16/07/2017.
 */
public class Assets implements Disposable {

    public static final AssetManager manager = new AssetManager();

    /*
     *  Starting Assets
     */

    //FONT PATHS
    public static final String font = "font/Test.TTF";

    //Texture Atlas
    public static final AssetDescriptor<TextureAtlas>
        editorAtlas = new AssetDescriptor<TextureAtlas>("ui/Editor.atlas", TextureAtlas.class),
        menuAtlas = new AssetDescriptor<TextureAtlas>("ui/menu.atlas", TextureAtlas.class),
        blocksAtlas = new AssetDescriptor<TextureAtlas>("blocks/blocks.atlas", TextureAtlas.class),
        playerAtlas = new AssetDescriptor<TextureAtlas>("entity/player/player.atlas", TextureAtlas.class);

    //Skin
    public static final String menuSkin = "ui/menuStyle.json";


    //Texture
    public static final AssetDescriptor<Texture> logo = new AssetDescriptor<Texture>("Logo.png", Texture.class);

    /**
     * Puts the assets of the game into the queue of the assetmanager
     */
    public static void load() {
        //FONTS
        //<editor-fold desc="Skin Resources Loading">
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Test.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Constants.FONT_SIZE_SMALL;
        parameter.color = Color.WHITE;

        BitmapFont fontsmall = generator.generateFont(parameter);

        parameter.size = Constants.FONT_SIZE_BIG;
        parameter.color = Color.WHITE;

        BitmapFont fontbig = generator.generateFont(parameter);


        ObjectMap<String, Object> resources = new ObjectMap<String, Object>();
        resources.put("white_small", fontsmall);
        resources.put("white_big", fontbig);
        //</editor-fold>

        manager.load(menuSkin, Skin.class, new SkinLoader.SkinParameter("ui/menu.atlas", resources));
        manager.load(logo);
        manager.load(menuAtlas);
        manager.load(editorAtlas);
        manager.load(blocksAtlas);
        manager.load(playerAtlas);

    }

    @Override
    public void dispose() {
        manager.dispose();
    }

}
