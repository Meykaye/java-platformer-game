package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private float xDelta=100, yDelta=100; //direction input
	private BufferedImage img;
	private BufferedImage[][] animations; 
	private int aniTick, aniIndex, aniSpeed = 24;
	private int playerAction = RUNNING;
	private int playerDir = -1;
	private boolean moving = false;
	
	public GamePanel() {
		
		mouseInputs = new MouseInputs(this);
		
		importImg();
		loadAnimations();
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	
	}
	
	private void loadAnimations() {
		animations = new BufferedImage[9][8];
		
		for(int i=0; i < animations.length; i++)
			for(int j=0; j < animations[i].length; j++) {
				animations[i][j] = img.getSubimage(j*32, i*32, 32, 32);
			}
		
	}

	private void importImg() {
		InputStream is = getClass().getResourceAsStream("/player_sprite.png");
		
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void setPanelSize() {
		Dimension size = new Dimension(1280, 800);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	public void setDirection(int direction) {
		this.playerDir = direction;
		moving = true;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	private void setAnimation() {
		if(moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
			
	}
	
	private void updatePos() {
		if(moving) {
			switch(playerDir) {
				case LEFT:
					xDelta -= 5;
					break;
				case UP:
					yDelta -= 5;
					break;
				case RIGHT:
					xDelta += 5;
					break;
				case DOWN:
					yDelta += 5;
					break;
			}
		}
		
	}
	
	public void updateGame() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(animations[playerAction][aniIndex],(int) xDelta,(int) yDelta, 80, 80, null);
	}


	private void updateAnimationTick() {
		
		aniTick++;
		
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmt(playerAction))
				aniIndex = 0;
		}
	}
		
}
