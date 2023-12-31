package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;

public class Playing extends States implements Statemethods{

	private Player player;
	private LevelHandler levelHandler;
	private EnemyHandler enemyHandler;
	private ObjectManager objectManager;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused = false;
	private PauseOverlay pauseOverlay;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
//	private int lvlTilesWide = LoadSave.GetLevelData()[0].length; //getting width from the function
//	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX;
	private BufferedImage backgroundImg1, backgroundImg2, backgroundImg3, dripstone; // background images
	private boolean gameOver;
	private boolean lvlCompleted;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		
		backgroundImg1 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUNDIMG_1);
		backgroundImg2 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUNDIMG_2);
		backgroundImg3 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUNDIMG_3);
		dripstone = LoadSave.GetSpriteAtlas(LoadSave.DRIPSTONE);
		
		calcLvlOffset();
		loadStartLevel();
	}
	
	public void loadNextLevel() {
		resetAll();
		levelHandler.loadNextLevel();
		player.setSpawn(levelHandler.getCurrentLevel().getPlayerSpawn());
	}
	
	private void loadStartLevel() {
		enemyHandler.loadEnemies(levelHandler.getCurrentLevel());
		objectManager.loadObjects(levelHandler.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelHandler.getCurrentLevel().getLvlOffset();
		
	}

	private void initClasses() {
		levelHandler = new LevelHandler(game);
		enemyHandler = new EnemyHandler(this);
		objectManager = new ObjectManager(this);
		player = new Player(200, 200,(int) (32*Game.SCALE),(int) (32*Game.SCALE), this);
		player.loadLvlData(levelHandler.getCurrentLevel().getLevelData());
		player.setSpawn(levelHandler.getCurrentLevel().getPlayerSpawn());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
	}

	public void update() {
		
		if(paused)
			pauseOverlay.update();
		
		else if(lvlCompleted)
			levelCompletedOverlay.update();
		
		else if (!gameOver){
			levelHandler.update();
			objectManager.update();
			player.update();
			enemyHandler.update(levelHandler.getCurrentLevel().getLevelData(), player);
			checkBorder();
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
		objectManager.draw(g, xLvlOffset);
		
		if(paused) {
			g.setColor(new Color(0 ,0 ,0, 130));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
		else if(gameOver)
			gameOverOverlay.draw(g);
		
		else if(lvlCompleted)
			levelCompletedOverlay.draw(g);
	}

	private void drawPillars(Graphics g) {
		for(int i=0; i<3; i++)
			g.drawImage(backgroundImg2, i * BACKGROUNDIMG_2_WIDTH - (int)(xLvlOffset * 0.3), (int) (90 * Game.SCALE), BACKGROUNDIMG_2_WIDTH, BACKGROUNDIMG_2_HEIGHT, null);
		
		for(int i=0; i<3; i++)
			g.drawImage(backgroundImg3, i * BACKGROUNDIMG_3_WIDTH - (int)(xLvlOffset * 0.7), (int) (90 * Game.SCALE), BACKGROUNDIMG_3_WIDTH, BACKGROUNDIMG_3_HEIGHT, null);
	
		for(int i=0; i<3; i++)
			g.drawImage(dripstone, i * DRIPSTONE_WIDTH - (int) (xLvlOffset * 0.9), (int) (80 * Game.SCALE), DRIPSTONE_WIDTH, DRIPSTONE_HEIGHT, null);
	}
	
	public void resetAll() {
		//resetting enemy, player and level
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyHandler.resetAllEnemies();
		objectManager.resetAllObjects();
	}
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted; 
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyHandler.checkEnemyHit(attackBox);
	}
	
	public void checkCrystalTouched(Rectangle2D.Float hitBox) {
		objectManager.checkObjectTouched(hitBox);
	}
	
	public void checkObjectHit(Rectangle2D.Float hitBox) {
		objectManager.checkObjectHit(hitBox);
	}

	public void mouseClicked(MouseEvent e) {
		if(!gameOver)
			if(e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
	}

	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseMoved(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}	
	}

	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
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
		if(!gameOver)
			switch(e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
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
		if(!gameOver)
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
	
	public EnemyHandler getEnemyHandler() {
		return enemyHandler;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
}
