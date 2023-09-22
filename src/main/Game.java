package main;

import java.awt.Graphics;
import gamestates.Gamestates;
import utilz.LoadSave;
import gamestates.*;

public class Game implements Runnable{
	
	private GamePanel gamePanel;
	private GameWindow gameWindow;
	private Thread gameThread;
	private final int FPS_SET = 165;
	private final int UPS_SET = 200;
	private Playing playing;
	private Menu menu;
	
	public static final int TILES_DEFAULT_SIZE = 32;
	public static final float SCALE = 2f;
	public static final int TILES_IN_WIDTH = 26;
	public static final int TILES_IN_HEIGHT = 14;
	public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
	public static final int GAME_WIDTH = (int)(TILES_SIZE * TILES_IN_WIDTH);
	public static final int GAME_HEIGHT = (int)(TILES_SIZE * TILES_IN_HEIGHT);

	
	public Game() {
		initClasses();
		
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		
		startGameLoop();
	}
	
	private void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void update() {
		
		switch(Gamestates.states) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
		case QUIT:
		default:
			System.exit(0);
			break;
		}
		
	}

	public void render(Graphics g) {
		
		switch(Gamestates.states) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		default:
			break;
		}
		
	}
	
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
//				System.out.println("FPS: "+ frames + " | UPS: "+ updates);
				frames = 0;
				updates = 0;
			}
			
		}
		
	}
	
	public void windowFocusLost() {
		if(Gamestates.states == Gamestates.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
}
