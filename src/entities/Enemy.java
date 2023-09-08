package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity{
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 40;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected float fallSpeed; 
	protected float gravity = 0.04f * Game.SCALE;
	protected float walkSpeed = 0.35f * Game.SCALE;
	protected float walkDir = LEFT;
	protected int tileY;
	protected float attackRange = 32;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x, y, width, height);
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitBox, lvlData));
			inAir = true;
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.y += fallSpeed;
			fallSpeed += gravity;
		}
		else {
			inAir = false;
			hitBox.y = GetEntityPosRoofOrFloor(hitBox, fallSpeed);
			tileY = (int) (hitBox.y / Game.TILES_SIZE);
		}
	}
	
	protected void move(int[][] lvlData) {
		float xSpeed = 0;
		
		if(walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;
		
		if(CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
			if(IsFloor(hitBox, xSpeed, lvlData)) {
				hitBox.x += xSpeed;
				return;
			}
		
		changeWalkDir();
	}
	
	protected void turnTowardsPlayer(Player player) {
		if(player.hitBox.x > hitBox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int)(player.getHitbox().y / Game.TILES_SIZE);
		if(playerTileY == tileY)
			if(isPlayerInRange(player)) {
				if(IsSightClear(lvlData, hitBox, player.hitBox, tileY))
					return true;
			}
		
		return false;
	}
	
	protected boolean isPlayerInRange(Player player) {
		int absVal = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absVal <= attackRange * 8;
	}
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absVal = (int) Math.abs(player.hitBox.x - hitBox.x);
		int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
		if(playerTileY == tileY)
			return absVal <= attackRange;
		return false;
	}

	protected void newState(int enemyState) {
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmt(enemyType, enemyState)) {
				aniIndex = 0;
				if(enemyState == ATTACKING)
					enemyState = IDLE;
			}
		}	
	}
	
	protected void changeWalkDir() {
		if(walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	public int getAniIndex() {
		return aniIndex;
	}
	
	public int getEnemyState() {
		return enemyState;
	}
}
