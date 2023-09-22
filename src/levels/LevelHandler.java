package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestates;
import main.Game;
import utilz.LoadSave;

public class LevelHandler {

	private BufferedImage[] levelSprite;
	private Game game;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	public LevelHandler(Game game) {
		this.game = game;
		//levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		importOutsideSprite();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	
	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex >= levels.size()) {
			lvlIndex = 0;
			System.out.println("no more levels lol");
			Gamestates.states = Gamestates.MENU;
		}
		
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyHandler().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);	
	}
	
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for(BufferedImage img: allLevels)
			levels.add(new Level(img));
		
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
			for(int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE*i - lvlOffset, Game.TILES_SIZE*j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}
	
	public void update() {
		
	}
	
	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	
	public int getAmtOfLevels() {
		return levels.size();
	}
}
