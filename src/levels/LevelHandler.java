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
		levelSprite = new BufferedImage[27];
		for(int i=0; i<3; i++) {
			for(int j=0; j<9; j++) {
				int index = i*9 + j;
				levelSprite[index] = img.getSubimage(j*32, i*32, 32, 32);
			}
		}
		
	}

	public void draw(Graphics g) {
		
		for(int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for(int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE*i, Game.TILES_SIZE*j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}
	
	public void update() {
		
	}
	
}
