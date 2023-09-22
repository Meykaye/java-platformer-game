package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;

import static utilz.Constants.ObjectConstants.*;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[][] crystalImgs, containerImgs;
	private ArrayList<Crystal> crystals;
	private ArrayList<GameContainer> containers;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}
	
	public void checkObjectTouched(Rectangle2D.Float hitBox) {
		for(Crystal c: crystals)
			if(c.isActive()) {
				if(hitBox.intersects(c.getHitBox())) {
					c.setActive(false);
					applyEffectPlayer(c);
				}
			}
	}
	
	public void applyEffectPlayer(Crystal c) {
		if(c.getObjType() == PURPLE_CRYS)
			playing.getPlayer().changeHealth(PURPLE_CRYS_VALUE);
		else
			playing.getPlayer().changePower(COLORED_CRYS_VALUE);
	}
	
	public void checkObjectHit(Rectangle2D.Float attackBox) {
		for(GameContainer gc: containers)
			if(gc.isActive()) {
				if(gc.getHitBox().intersects(attackBox)) {
					gc.setAnimation(true);
					
					int type = 0;
					if(gc.getObjType() == BARREL)
						type = 1;
					crystals.add(new Crystal((int)(gc.getHitBox().x + gc.getHitBox().width/2 - 8),(int)(gc.getHitBox().y + gc.getHitBox().height/4) ,type));
					return;
				}
			}
	}
	
	public void loadObjects(Level newLevel) {
		crystals = newLevel.GetCrystals();
		containers = newLevel.GetGameContainers();
	}

	private void loadImgs() {
		BufferedImage crystalSprite = LoadSave.GetSpriteAtlas(LoadSave.CRYSTALS);
		crystalImgs = new BufferedImage[2][24];
		
		for(int j=0; j < crystalImgs.length; j++)
			for(int i=0; i < crystalImgs[j].length; i++)
				crystalImgs[j][i] = crystalSprite.getSubimage(32*i, 32*j, 32, 32);
		
		BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINERS);
		containerImgs = new BufferedImage[2][8];
		
		for(int j=0; j < containerImgs.length; j++)
			for(int i=0; i < containerImgs[j].length; i++)
				containerImgs[j][i] = containerSprite.getSubimage(40*i, 30*j, 40, 30);
	}
	
	public void update() {
		for(Crystal c : crystals)
			if(c.isActive())
				c.update();
		for(GameContainer gc: containers) {
			if(gc.isActive())
				gc.update();
		}
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawCrystals(g, xLvlOffset);
		drawContainers(g, xLvlOffset);
	}

	private void drawContainers(Graphics g, int xLvlOffset) {
		for(GameContainer gc: containers)
			if(gc.isActive()) {
				int type = 0;
				if(gc.getObjType() == BARREL)
					type = 1;
			
				g.drawImage(containerImgs[type][gc.getAniIndex()], 
				(int) (gc.getHitBox().x - gc.getxDrawOffset() - xLvlOffset), 
				(int) (gc.getHitBox().y - gc.getyDrawOffset()), 
				CONTAINER_WIDTH, 
				CONTAINER_HEIGHT,
				null);
			}
	}

	private void drawCrystals(Graphics g, int xLvlOffset) {
		for(Crystal c : crystals)
			if(c.isActive()) {
				int type = 0;
				if(c.getObjType() == PURPLE_CRYS)
					type = 1;
				
				g.drawImage(crystalImgs[type][c.getAniIndex()], 
				(int) (c.getHitBox().x - c.getxDrawOffset() - xLvlOffset), 
				(int) (c.getHitBox().y - c.getyDrawOffset()),
				CRYSTAL_WIDTH, 
				CRYSTAL_HEIGHT,
				null);
			}
	}
	
	public void resetAllObjects() {
		for(Crystal c : crystals)
			c.reset();
		
		for(GameContainer gc: containers)
			gc.reset();
	}
	
}
