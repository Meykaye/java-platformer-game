package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;

public class Playing extends States implements Statemethods{

	private Player player;
	private LevelHandler levelHandler;
	private EnemyHandler enemyHandler;
	private boolean paused = false;
	private PauseOverlay pauseOverlay;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length; //getting width from the function
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	private BufferedImage backgroundImg1, backgroundImg2, backgroundImg3, dripstone; // background images
	
	public Playing(Game game) {
		super(game);
		initClasses();
		
		backgroundImg1 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUNDIMG_1);
		backgroundImg2 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUNDIMG_2);
		backgroundImg3 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUNDIMG_3);
		dripstone = LoadSave.GetSpriteAtlas(LoadSave.DRIPSTONE);
	}
	
	private void initClasses() {
		levelHandler = new LevelHandler(game);
		enemyHandler = new EnemyHandler(this);
		player = new Player(200, 200,(int) (32*Game.SCALE),(int) (32*Game.SCALE));
		player.loadLvlData(levelHandler.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
	}

	public void update() {
		if(!paused) {
			levelHandler.update();
			player.update();
			enemyHandler.update();
			checkBorder();
		}
		
		else {
			pauseOverlay.update();
		}
	}

	private void checkBorder() {
		int playerX = (int) player.getHitbox().x;
		int difference = playerX - xLvlOffset;
		
		if(difference > rightBorder)
			xLvlOffset += difference - rightBorder;
		
		else if(difference < leftBorder)
			xLvlOffset += difference - leftBorder;
		
		
		if(xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		
		else if(xLvlOffset < 0)
			xLvlOffset = 0;
	}

	public void draw(Graphics g) {
		g.drawImage(backgroundImg1, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		drawPillars(g);
		
		levelHandler.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyHandler.draw(g, xLvlOffset);
		
		if(paused) {
			g.setColor(new Color(0 ,0 ,0, 130));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
	}

	private void drawPillars(Graphics g) {
		for(int i=0; i<3; i++)
			g.drawImage(backgroundImg2, i * BACKGROUNDIMG_2_WIDTH - (int)(xLvlOffset * 0.3), (int) (90 * Game.SCALE), BACKGROUNDIMG_2_WIDTH, BACKGROUNDIMG_2_HEIGHT, null);
		
		for(int i=0; i<3; i++)
			g.drawImage(backgroundImg3, i * BACKGROUNDIMG_3_WIDTH - (int)(xLvlOffset * 0.7), (int) (90 * Game.SCALE), BACKGROUNDIMG_3_WIDTH, BACKGROUNDIMG_3_HEIGHT, null);
	
		for(int i=0; i<3; i++)
			g.drawImage(dripstone, i * DRIPSTONE_WIDTH - (int) (xLvlOffset * 0.9), (int) (80 * Game.SCALE), DRIPSTONE_WIDTH, DRIPSTONE_HEIGHT, null);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
	}

	public void mousePressed(MouseEvent e) {
		if(paused)
			pauseOverlay.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		if(paused)
			pauseOverlay.mouseReleased(e);
	}

	public void mouseMoved(MouseEvent e) {
		if(paused)
			pauseOverlay.mouseMoved(e);
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setUp(true);
			break;
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_S:
			player.setDown(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_ESCAPE:
			paused = !paused;
			break; 
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setUp(false);
			break;
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_S:
			player.setDown(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}
		
	}
	
	public void mouseDragged(MouseEvent e) {
		if(paused)
			pauseOverlay.mouseDragged(e);
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
