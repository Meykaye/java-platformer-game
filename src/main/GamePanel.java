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

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private float xDelta=100, yDelta=100; //direction input
	private BufferedImage img;
	private BufferedImage[][] animations; 
	private int aniTick, aniIndex, aniSpeed = 60;
	
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
				animations[i][j] = img.getSubimage(j*33, i*32, 33, 32);
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

	public void changeXDelta(int value) {
		this.xDelta += value; 
	}
	
	public void changeYDelta(int value) {
		this.yDelta += value; 
	}
	
	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		updateAnimationTick();
			
		g.drawImage(animations[4][3],(int) xDelta,(int) yDelta, 120, 80, null);
		
	}

	private void updateAnimationTick() {
		
//		aniTick++;
//		
//		if(aniTick >= aniSpeed) {
//			aniTick = 0;
//			aniIndex++;
//			if(aniIndex >= aniIndex.length)
//				aniIndex = 0;
//		}
		
	}
		
}
