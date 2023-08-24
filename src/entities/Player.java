package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmt;
import static utilz.Constants.PlayerConstants.*;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 35;
	private int playerAction = WALKING;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
	private float playerSpeed = 2.0f;
	
	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
		
	}
	
	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex],(int) x,(int) y, 80, 80, null);
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
		
		if(right && !left) {
			x += playerSpeed;
			moving = true;
		}
		
		else if(left && !right) {
			x -= playerSpeed;
			moving = true;
		}
		
		
		if(up && !down) {
			y -= playerSpeed;
			moving = true;
		}
		
		else if(down && !up) {
			y += playerSpeed;
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
