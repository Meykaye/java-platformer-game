package utilz;

import static utilz.Constants.EnemyConstants.MONSTER;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Monster;
import main.Game;
import objects.Crystal;
import objects.GameContainer;

import static utilz.Constants.ObjectConstants.*;


public class HelpMethods {
	
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		
		if(!IsSolid(x, y, lvlData))
			if(!IsSolid(x+width, y+height, lvlData))
				if(!IsSolid(x+width, y, lvlData))
					if(!IsSolid(x,y+height, lvlData))
						return true;
		
		return false;
		
	}
	
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if(x < 0 || x >= maxWidth)
			return true;
		
		if(y < 0 || y >= Game.GAME_HEIGHT)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
	}
	
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];
				
		if(value >= 36 || value < 0 || value != 35)
			return true;
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
		
		int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
				
		if(xSpeed > 0) {
			//right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
			return tileXPos + xOffset - 1;
		}
		else {
			return currentTile * Game.TILES_SIZE;
		}
		
	}
	
	public static float GetEntityPosRoofOrFloor(Rectangle2D.Float hitBox, float airSpeed) {
		int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
			//Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
			return tileYPos + yOffset - 1;
		}
		else {
			//Jumping
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][]lvlData) {
		//Check the bottomleft and bottomright pixel because player is not in air.
		
		if(!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) // bottomleft pixel
			if(!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData)) // bottomright pixel
				return false;
		
		return true;
	}
	
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		if(xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
		else
			return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}
	
	public static boolean IsAllTileWalkable(int xStart, int xEnd , int y, int[][] lvlData) {
		for(int i=0; i < xEnd - xStart; i++) {
			if(IsTileSolid(xStart + i, y, lvlData))
				return false;
			if(!IsTileSolid(xStart + i, y+1, lvlData))
				return false;
		}
		return true;
	}
	
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitBox, Rectangle2D.Float secondHitBox, int tileY) {
		int firstXTile = (int) (firstHitBox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitBox.x / Game.TILES_SIZE);
		
		if(firstXTile > secondXTile)
			return IsAllTileWalkable(secondXTile, firstXTile, tileY, lvlData);
		else
			return IsAllTileWalkable(firstXTile, secondXTile, tileY, lvlData);
	}
	
	public static int[][] GetLevelData(BufferedImage img) {
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		
		for(int j=0; j < img.getHeight(); j++)
			for(int i=0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				
				if(value >= 81)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}
	
	public static ArrayList<Monster> getMonsters(BufferedImage img) {
		ArrayList<Monster> list = new ArrayList<>();
		
		for(int j=0; j < img.getHeight(); j++)
			for(int i=0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == MONSTER)
					list.add(new Monster(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;
	}
	
	public static Point GetPlayerSpawn(BufferedImage img) {
		for(int j=0; j < img.getHeight(); j++)
			for(int i=0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if(value == 100)
					return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
			}
		return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
	}
	
	public static ArrayList<Crystal> getCrystals(BufferedImage img) {
		ArrayList<Crystal> list = new ArrayList<>();
		
		for(int j=0; j < img.getHeight(); j++)
			for(int i=0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if(value == PURPLE_CRYS || value == COLORED_CRYS)
					list.add(new Crystal(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
					
			}
		return list;
	}
	
	public static ArrayList<GameContainer> getGameContainers(BufferedImage img) {
		ArrayList<GameContainer> list = new ArrayList<>();
		
		for(int j=0; j < img.getHeight(); j++)
			for(int i=0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if(value == BOX || value == BARREL)
					list.add(new GameContainer(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
			}
		return list;
	}
}


