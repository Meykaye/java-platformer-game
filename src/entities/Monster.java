package entities;

import static utilz.Constants.EnemyConstants.*;

import main.Game;

public class Monster extends Enemy{

	public Monster(float x, float y) {
		super(x, y, MONSTER_WIDTH, MONSTER_HEIGHT, MONSTER);
		initHitbox(x, y, (int)(26 * Game.SCALE), (int)(14 * Game.SCALE));
	}
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();
	}

	private void updateMove(int[][] lvlData, Player player) {
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
			}
		}
	}
}
