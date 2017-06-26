package de.themdplays.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.themdplays.map.Tile;

import java.util.LinkedHashMap;

/**
 * Created by Moritz on 31.12.2016.
 */
public class AssetsHandler {

    private Skin menuSkin;
    private LinkedHashMap<Integer, TextureAtlas> blocks;
    private TextureAtlas a_menu ,a_editor_UI;

    public AssetsHandler() {

        //INIT TEXTURES
        blocks = new LinkedHashMap<Integer, TextureAtlas>();
        loadUIAtlases();

        //INIT SKINS
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/menu.atlas"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Test.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Constants.FONT_SIZE_SMALL;
        parameter.color = Color.WHITE;

        BitmapFont fontsmall = generator.generateFont(parameter);

        menuSkin = new Skin();
        menuSkin.add("white_small", fontsmall, BitmapFont.class);

        parameter.size = Constants.FONT_SIZE_BIG;
        parameter.color = Color.WHITE;

        BitmapFont fontbig = generator.generateFont(parameter);

        menuSkin.add("white_big", fontbig, BitmapFont.class);
        menuSkin.addRegions(buttonAtlas);
        menuSkin.load(Gdx.files.internal("ui/menuStyle.json"));
    }

    public void loadBlocks() {
        for(Tile tile : Tile.values()) {
            if(tile!=Tile.AIR) blocks.put(tile.getID(), new TextureAtlas(Gdx.files.internal("Blocks/" + tile.name() + ".atlas")));
        }
    }

    public void loadUIAtlases() {
        a_menu = new TextureAtlas(Gdx.files.internal("ui/menu.atlas"));
        a_editor_UI = new TextureAtlas(Gdx.files.internal("ui/Editor.atlas"));
    }

    LinkedHashMap<Integer, TextureAtlas> getBlocks() {
        if(!blocks.isEmpty()) return blocks;
        else {
            loadBlocks();
            return blocks;
        }
    }

    public Skin getMenuSkin() {
        return menuSkin;
    }

    public TextureAtlas getMenuAtlas() {
        return a_menu;
    }

    public TextureAtlas getA_editor_UI() {
        return a_editor_UI;
    }
}
