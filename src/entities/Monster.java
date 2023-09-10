package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;

import main.Game;

public class Monster extends Enemy{

	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;
	
	public Monster(float x, float y) {
		super(x, y, MONSTER_WIDTH, MONSTER_HEIGHT, MONSTER);
		initHitbox(x, y, (int)(30 * Game.SCALE), (int)(19 * Game.SCALE));
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(40 * Game.SCALE), (int)(19 * Game.SCALE));
		attackBoxOffsetX = (int) (Game.SCALE * 5);
		
	}

	public void update(int[][] lvlData, Player player) {
		updateBehaviour(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}

	private void updateAttackBox() {
		attackBox.x = hitBox.x - attackBoxOffsetX;
		attackBox.y = hitBox.y;
	}

	private void updateBehaviour(int[][] lvlData, Player player) {
		if(firstUpdate)
			firstUpdateCheck(lvlData);
		
		if(inAir)
			updateInAir(lvlData);
			
		else {
			switch(enemyState) {
			case IDLE:
				newState(WALKING); break;
			case WALKING:
				if(canSeePlayer(lvlData, player))
					turnTowardsPlayer(player);
				if(isPlayerCloseForAttack(player))
					newState(ATTACKING);
				move(lvlData);
				break;
			case ATTACKING:
				if(aniIndex == 0)
					attackChecked = false;
				
				if(aniIndex == 4 && !attackChecked)
					checkPlayerHit(attackBox, player);
				break;
			case HITTING:
				break;
			}
		}
	}

	public void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.MAGENTA);
		g.drawRect((int) (attackBox.x - xLvlOffset), (int) (attackBox.y), (int) (attackBox.width), (int) (attackBox.height));
	}
	
	public int flipX() {
		if(walkDir == RIGHT)
			return width;
		else
			return 0;
	}
	
	public int flipW() {
		if(walkDir == RIGHT)
			return -1;
		else
			return 1;
	}
}
