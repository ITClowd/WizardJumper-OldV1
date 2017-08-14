package de.themdplays.main.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import de.themdplays.main.WizardJumper;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = WizardJumper.TITLE + " v" + WizardJumper.VERSION;
		cfg.vSyncEnabled = true;
		cfg.samples=2;
		cfg.width = 1280;
		cfg.height = 720;
        cfg.addIcon("icon/Icon_32x32.png", FileType.Internal);
        cfg.addIcon("icon/Icon_128x128.png", FileType.Internal);
        cfg.addIcon("icon/Icon_16x16.png", FileType.Internal);

//        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
//        cfg.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
//        cfg.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		cfg.fullscreen = false;
		cfg.resizable = false;
		
		boolean PACKER = false;
		
		if(PACKER) {
			Settings s = new Settings();
			s.edgePadding = true;
			s.paddingX = 2;
			s.paddingY = 2;
		
			TexturePacker.process("/home/moritz/dirt/", "/home/moritz/dirt/packer/", "blocks");
		}

		
		new LwjglApplication(new WizardJumper(), cfg);
	}
}
