package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.GRAVITY;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import static utilz.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity{
	protected int enemyType;
	protected int aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected float walkDir = LEFT;
	protected int tileY;
	protected float attackRange = 33;
	protected boolean active = true;
	protected boolean attackChecked;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = Game.SCALE * 0.25f;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitBox, lvlData));
			inAir = true;
		firstUpdate = false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.y += airSpeed;
			airSpeed += GRAVITY;
		}
		else {
			inAir = false;
			hitBox.y = GetEntityPosRoofOrFloor(hitBox, airSpeed);
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
		this.state = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if(currentHealth <= 0 )
			newState(DYING);
		else
			newState(HITTING);
	}
	
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.hitBox))
			player.changeHealth(-GetEnemyDmg(enemyType));
		attackChecked = true;
		
	}
	
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmt(enemyType, state)) {
				aniIndex = 0;
				
				switch(state) {
					case ATTACKING, HITTING -> state = IDLE;
					case DYING -> active = false;
				}
//				if(enemyState == ATTACKING)
//					enemyState = IDLE;
//				else if(enemyState == HITTING)
//					enemyState = IDLE;
//				else if(enemyState == DYING)
//					active = false;
			}
		}	
	}
	
	protected void changeWalkDir() {
		if(walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}
	
	public void resetEnemy() {
		hitBox.x = x;
		hitBox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed = 0;
	}
	
	public boolean isActive() {
		return active;
	}
}
