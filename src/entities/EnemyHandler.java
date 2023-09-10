package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
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
			if(m.isActive())
				m.update(lvlData, player);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawMonsters(g, xLvlOffset);
	}
	
	private void drawMonsters(Graphics g, int xLvlOffset) {
		for(Monster m : monsters) 
			if(m.isActive()) {
				g.drawImage(slimeArr[m.getEnemyState()][m.getAniIndex()], 
						(int) m.getHitbox().x - MONSTER_DRAWOFFSET_X - xLvlOffset + m.flipX(), 
						(int) (m.getHitbox().y - MONSTER_DRAWOFFSET_Y), 
						MONSTER_WIDTH * m.flipW(), MONSTER_HEIGHT, null);
				m.drawHitbox(g, xLvlOffset);
				m.drawAttackBox(g, xLvlOffset);
			}
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for(Monster m: monsters) 
			if(m.isActive())
				if(attackBox.intersects(m.getHitbox())) {
					m.hurt(10);
					return;
				} 
			
	}

	private void loadEnemyImgs() {
		slimeArr = new BufferedImage[5][5];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MONSTER_SPRITE);
		
		for(int i=0; i < slimeArr.length; i++)
			for(int j=0; j < slimeArr[i].length; j++)
				slimeArr[i][j] = temp.getSubimage(j*MONSTER_DEFAULT_WIDTH, i*MONSTER_DEFAULT_HEIGHT, MONSTER_DEFAULT_WIDTH, MONSTER_DEFAULT_HEIGHT);
	}
	
	public void resetAllEnemies() {
		for(Monster m: monsters)
			m.resetEnemy();
	}
	
}
