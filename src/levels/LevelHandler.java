package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelHandler {

	private BufferedImage[] levelSprite;
	private Game game;
	private Level levelOne;
	public LevelHandler(Game game) {
		this.game = game;
		//levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		importOutsideSprite();
		levelOne = new Level(LoadSave.GetLevelData());
	}
	
	private void importOutsideSprite() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[36];
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {
				int index = i*6 + j;
				levelSprite[index] = img.getSubimage(j*32, i*32, 32, 32);
			}
		}
		
	}

	public void draw(Graphics g, int lvlOffset) {
		
		for(int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for(int i = 0; i < levelOne.getLevelData()[0].length; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE*i - lvlOffset, Game.TILES_SIZE*j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}
	
	public void update() {
		
	}
	
	public Level getCurrentLevel() {
		return levelOne;
	}
	
}
