package entities;

import static utilz.HelpMethods.CanMoveHere;
import static utilz.Constants.PlayerConstants.*;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 35;
	private int playerAction = WALKING;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 2.0f;
	private int[][] lvlData; 
	private float xDrawOffset = 8 * Game.SCALE;
	private float yDrawOffset = 5 * Game.SCALE;
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 21 * Game.SCALE, 34 * Game.SCALE);
	}
	
	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitBox.x - xDrawOffset), (int)(hitBox.y - yDrawOffset), width, height, null);
		drawHitbox(g);
	}

	private void updateAnimationTick() {
		
		aniTick++;
		
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmt(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
	}
	
	private void setAnimation() {
		int startAni = playerAction;
		
		if(moving)
			playerAction = WALKING;
		else
			playerAction = IDLE;
		
		if(attacking)
			playerAction = ATTACKING;
		
		if(startAni != playerAction)
			resetAniTick();
			
	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		
		moving = false;
		if(!left && !right && !up && !down)
			return;
		
		float xSpeed = 0, ySpeed = 0;
		
		if(right && !left)
			xSpeed = playerSpeed;
		
		else if(left && !right)
			xSpeed = -playerSpeed;
		
		
		if(up && !down)
			ySpeed = -playerSpeed;
		
		else if(down && !up)
			ySpeed = playerSpeed;
		
//		if(CanMoveHere(x+xSpeed, y+ySpeed, width, height, lvlData)) {
//			this.x += xSpeed;
//			this.y += ySpeed;
//			moving = true;
//		}
		
		if(CanMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData)) {
			hitBox.x += xSpeed;
			hitBox.y += ySpeed;
			moving = true;
		}
	}
	
	private void loadAnimations() {
		
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[9][8];
			
			for(int i=0; i < animations.length; i++)
				for(int j=0; j < animations[i].length; j++)
					animations[i][j] = img.getSubimage(j*32, i*32, 32, 32);	
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

}
