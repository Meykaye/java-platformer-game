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

	public void update(int[][] lvlData, Player player) {
		for(Monster m : monsters)
			m.update(lvlData, player);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawMonsters(g, xLvlOffset);
	}
	
	private void drawMonsters(Graphics g, int xLvlOffset) {
		for(Monster m : monsters) {
			g.drawImage(slimeArr[m.getEnemyState()][m.getAniIndex()], (int) (m.getHitbox().x - MONSTER_DRAWOFFSET_X) - xLvlOffset, (int) (m.getHitbox().y - MONSTER_DRAWOFFSET_Y), MONSTER_WIDTH, MONSTER_HEIGHT, null);
			//m.drawHitbox(g, xLvlOffset);
		}
	}

	private void loadEnemyImgs() {
		slimeArr = new BufferedImage[5][5];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MONSTER_SPRITE);
		
		for(int i=0; i < slimeArr.length; i++)
			for(int j=0; j < slimeArr[i].length; j++)
				slimeArr[i][j] = temp.getSubimage(j*MONSTER_DEFAULT_WIDTH, i*MONSTER_DEFAULT_HEIGHT, MONSTER_DEFAULT_WIDTH, MONSTER_DEFAULT_HEIGHT);
	}
	
}
