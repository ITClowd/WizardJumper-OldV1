package de.themdplays.main.desktop;

import com.badlogic.gdx.Files.FileType;
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
        cfg.addIcon("icon/icon_32x32.png", FileType.Internal);
        cfg.addIcon("icon/icon_128x128.png", FileType.Internal);
        cfg.addIcon("icon/icon_16x16.png", FileType.Internal);

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
		
			TexturePacker.process("D://Programmieren//Spiele//Graphics//Blocks//Grass//", "D://Programmieren//Spiele//WizardJumper//core//assets//blocks", "blocks");
		}

		
		new LwjglApplication(new WizardJumper(), cfg);
	}
}
