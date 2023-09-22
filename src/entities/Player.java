package entities;

import static utilz.HelpMethods.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.GRAVITY;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniSpeed = 28;
	private boolean moving = false, attacking = false;
	private boolean left, right, jump;
	private int[][] lvlData; 
	private float xDrawOffset = 8 * Game.SCALE;
	private float yDrawOffset = 5 * Game.SCALE;
	
	//Jumping and Gravity
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	
	//health bar UI
	private BufferedImage statusBarImg;
	private Color healthRed = new Color(161,55,55);
	
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
	private int healthBarWidth = (int) (150* Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14* Game.SCALE);
	
	private int healthWidth = healthBarWidth;
	
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked;
	private Playing playing;
	
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 100;
		this.currentHealth = 35;
		this.walkSpeed = Game.SCALE * 0.95f;
		loadAnimations();
		initHitbox(14, 26);
		initAttackBox();
	}
	
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitBox.x = x;
		hitBox.y = y;
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(12 * Game.SCALE), (int) (12 * Game.SCALE));
		
	}

	public void update() {
		updateHealthBar();
		if(currentHealth <= 0) {
			playing.setGameOver(true);
			return;
		}
		updateAttackBox();
		updatePos();
		if(moving)
			checkCrystalTouched();
		if(attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
	}
	
	private void checkCrystalTouched() {
		playing.checkCrystalTouched(hitBox);
	}

	private void checkAttack() {
		if(attackChecked || aniIndex != 3)
			return;
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
	}

	private void updateAttackBox() {
		if(right) {
			attackBox.x = hitBox.x + hitBox.width + (int) (Game.SCALE * 2);
		}
		else if(left) {
			attackBox.x = hitBox.x - hitBox.width - (int) (Game.SCALE * 0);
		}
		
		attackBox.y = hitBox.y + (Game.SCALE * 8);
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
		
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[state][aniIndex], 
				(int)(hitBox.x - xDrawOffset) - lvlOffset + flipX, 
				(int)(hitBox.y - yDrawOffset), 
				width * flipW, height, null);
		//drawHitbox(g , lvlOffset);
		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(healthRed);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	}

	private void updateAnimationTick() {
		
		aniTick++;
		
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmt(state)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
			}
		}
	}
	
	private void setAnimation() {
		int startAni = state;
		
		if(moving)
			state = RUN;
		else
			state = IDLE;
		
		if(inAir) {
			if(airSpeed < 0) 
				state = JUMP;
			else
				state = FALLING;	
		}
		
		if(attacking) {
			state = ATTACK;
			if(startAni != ATTACK) {
				aniIndex = 3;
				aniTick = 0;
				return;
			}
		}
		
		if(startAni != state)
			resetAniTick();
			
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;
		
		if(jump)
			jump();
		
//		if(!left && !right && !inAir)
//			return;
		
		if(!inAir)
			if((!left && !right) || (left && right))
				return;
		
		float xSpeed = 0;
		
		if(right) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}
		
		else if(left) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		
		if(!inAir)
			if(!IsEntityOnFloor(hitBox, lvlData))
				inAir = true;
		
		if(inAir)
			if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
				hitBox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			}
			
			else {
				hitBox.y = GetEntityPosRoofOrFloor(hitBox, airSpeed);
				if(airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		else 
			updateXPos(xSpeed);
		
		moving = true;
	}
	
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
			hitBox.x += xSpeed;
		} else {
			hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
		}
		
	}
	
	public void changeHealth(int value) {
		currentHealth += value;
		
		if(currentHealth <= 0) {
			currentHealth = 0;
			//gameOver();
		}
		else if(currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}
	
	public void changePower(int value) {
		System.out.println("Added Power");
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
	}
	
	private void loadAnimations() {
		
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[10][8];
			
			for(int i=0; i < animations.length; i++)
				for(int j=0; j < animations[i].length; j++)
					animations[i][j] = img.getSubimage(j*32, i*32, 32, 32);	
			
			statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitBox, lvlData))
			inAir = true;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		state = IDLE;
		currentHealth = maxHealth;
		hitBox.x = x;
		hitBox.y = y;
		
		if(!IsEntityOnFloor(hitBox, lvlData))
			inAir = true;
	}

}