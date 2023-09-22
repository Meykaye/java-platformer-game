package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Monster;
import main.Game;
import static utilz.Constants.EnemyConstants.MONSTER;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "player_sprite.png";
	public static final String LEVEL_ATLAS = "tileset_sprite.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data_1.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
	public static final String MENU_BUTTONS = "menu_buttons.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_buttons.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String BACKGROUND_MENU = "background_menu.jpg";
	public static final String BACKGROUNDIMG_1 = "bg_1.png";
	public static final String BACKGROUNDIMG_2 = "bg_2.png";
	public static final String BACKGROUNDIMG_3 = "bg_3.png";
	public static final String DRIPSTONE = "dripstone.png";
	public static final String MONSTER_SPRITE = "slime_sprite.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String COMPLETED_IMG = "completed_sprite.png";
	public static final String CRYSTALS = "crystals_sprite.png";
	public static final String CONTAINERS = "objects_sprites.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		
		try {
			img = ImageIO.read(is);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
				try {
					is.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
		}
		return img;
	}
	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;
		
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];
		
//		for(File f: files)
//			System.out.println("File: "+ f.getName());
	
		
		for(int i = 0; i < filesSorted.length; i++) 
			for(int j = 0; j < files.length; j++) {
				if(files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];
			}
			
//		for(File f: filesSorted)
//			System.out.println("File sorted: "+ f.getName());
		
		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		
		for(int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return imgs;
			
	}
}
