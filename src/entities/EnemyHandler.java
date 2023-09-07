package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyHandler {
	
	private Playing playing;
	private BufferedImage[][] slimeArr;
	private ArrayList<Monster> monsters = new ArrayList<>();
	
	public EnemyHandler(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	
	private void addEnemies() {
		monsters = LoadSave.getMonsters();
		System.out.println("Size of Skeletons: "+ monsters.size());
		
	}

	public void update() {
		for(Monster s : monsters)
			s.update();
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawSkeletons(g, xLvlOffset);
	}
	
	private void drawSkeletons(Graphics g, int xLvlOffset) {
		for(Monster s : monsters)
			g.drawImage(slimeArr[s.getEnemyState()][s.getAniIndex()], (int) s.getHitbox().x - xLvlOffset, (int) s.getHitbox().y, MONSTER_WIDTH, MONSTER_HEIGHT, null);
		
	}

	private void loadEnemyImgs() {
		slimeArr = new BufferedImage[5][5];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MONSTER_SPRITE);
		
		for(int i=0; i < slimeArr.length; i++)
			for(int j=0; j < slimeArr[i].length; j++)
				slimeArr[i][j] = temp.getSubimage(j*MONSTER_DEFAULT_WIDTH, i*MONSTER_DEFAULT_HEIGHT, MONSTER_DEFAULT_WIDTH, MONSTER_DEFAULT_HEIGHT);
	}
	
}
