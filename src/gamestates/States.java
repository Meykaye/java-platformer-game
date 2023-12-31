package gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

public class States {
	
	protected Game game;
	public States(Game game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
				
	}
	
	public Game getGame() {
		return game;
	}
}
