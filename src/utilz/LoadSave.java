package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "player_sprite.png";
	public static final String LEVEL_ATLAS = "tileset_sprite.png";
	//public static final String LEVEL_ONE_DATA = "level_one_data_1.png";
	public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
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
	
	public static int[][] GetLevelData() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		
		for(int j=0; j < img.getHeight(); j++)
			for(int i=0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				
				if(value >= 81)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}
	
}
