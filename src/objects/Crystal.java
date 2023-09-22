package objects;

import main.Game;

public class Crystal extends GameObject{

	public Crystal(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		initHitbox(30, 30);
		xDrawOffset = (int) (3 * Game.SCALE);
		yDrawOffset = (int) (2 * Game.SCALE);
	}
	
	public void update() {
		updateAnimationTick();
	}

}
