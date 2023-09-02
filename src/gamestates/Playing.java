package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelHandler;
import main.Game;
import ui.PauseOverlay;
import utilz.LoadSave;

public class Playing extends States implements Statemethods{

	private Player player;
	private LevelHandler levelHandler;
	private boolean paused = false;
	private PauseOverlay pauseOverlay;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length; //getting width from the function
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {
		levelHandler = new LevelHandler(game);
		player = new Player(200, 200,(int) (32*Game.SCALE),(int) (32*Game.SCALE));
		player.loadLvlData(levelHandler.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
	}

	public void update() {
		if(!paused) {
			levelHandler.update();
			player.update();
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
		levelHandler.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		
		if(paused) {
			g.setColor(new Color(0 ,0 ,0, 130));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
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
