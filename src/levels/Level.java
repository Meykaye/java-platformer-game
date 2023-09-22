package levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.getMonsters;
import static utilz.HelpMethods.GetPlayerSpawn;

import entities.Monster;
import main.Game;
import objects.*;
import utilz.HelpMethods;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Monster> monsters;
	private ArrayList<Crystal> crystals;
	private ArrayList<GameContainer> containers;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffset;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createCrystals();
		createContainers();
		calculateLvlOffsets();
		calculatePlayerSpawn();
	}
	
	private void createContainers() {
		containers = HelpMethods.getGameContainers(img);
	}

	private void createCrystals() {
		crystals = HelpMethods.getCrystals(img);
	}
	
	private void calculatePlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
		
	}

	private void calculateLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffset = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		monsters = getMonsters(img);
		
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData() {
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffset;
	}
	
	public ArrayList<Monster> GetMonsters() {
		return monsters;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
	public ArrayList<Crystal> GetCrystals() {
		return crystals;
	}
	
	public ArrayList<GameContainer> GetGameContainers() {
		return containers;
	}
}
