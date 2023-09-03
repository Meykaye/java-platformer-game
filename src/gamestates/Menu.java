package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends States implements Statemethods{
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg, backgroundMenuImg;
	private int menuX, menuY, menuWidth, menuHeight;

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		backgroundMenuImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MENU);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (48 * Game.SCALE);
		
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int)(150 * Game.SCALE), 0, Gamestates.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int)(214 * Game.SCALE), 1, Gamestates.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int)(278 * Game.SCALE), 2, Gamestates.QUIT);
		
	}

	public void update() {
		for(MenuButton mb : buttons)
			mb.update();
	}

	public void draw(Graphics g) {
		
		g.drawImage(backgroundMenuImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb : buttons)
			mb.draw(g);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e,mb)) {
				mb.setMousePressed(true);
			}
		}
		
	}

	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed())
					mb.applyGamestates();
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton mb : buttons)
			mb.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons)
			mb.setMouseOver(false);
		
		for(MenuButton mb : buttons)
			if(isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			Gamestates.states = Gamestates.PLAYING;
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
