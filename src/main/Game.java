package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelHandler;

public class Game implements Runnable{
	
	private GamePanel gamePanel;
	private GameWindow gameWindow;
	private Thread gameThread;
	private final int FPS_SET = 160;
	private final int UPS_SET = 200;
	private Player player;
	private LevelHandler levelHandler;
	
	public static final int TILES_DEFAULT_SIZE = 32;
	public static final float SCALE = 1.5f;
	public static final int TILES_IN_WIDTH = 26;
	public static final int TILES_IN_HEIGHT = 14;
	public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
	public static final int GAME_WIDTH = (int)(TILES_SIZE * TILES_IN_WIDTH);
	public static final int GAME_HEIGHT = (int)(TILES_SIZE * TILES_IN_HEIGHT);




	
	public Game() {
		initClasses();
		
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop();
	}
	
	private void initClasses() {
		player = new Player(200, 200);
		levelHandler = new LevelHandler(this);
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void update() {
		player.update();
		levelHandler.update();
	}

	public void render(Graphics g) {
		levelHandler.draw(g);
		player.render(g);
	}
	
	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET; 
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
 		
		while(true) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				deltaF--;
				frames++;
			}
			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: "+ frames + " | UPS: "+ updates);
				frames = 0;
				updates = 0;
			}
			
		}
		
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
}
