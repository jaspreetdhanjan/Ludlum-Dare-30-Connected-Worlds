package com.jas.ld30.level.tile;

import com.jas.ld30.level.Level;

public class WaterTile extends Tile {
	private int xSprite_1 = 4;
	private int xSprite_2 = 5;

	public WaterTile(int id) {
		super(id);

		xSprite = 4;
		ySprite = 0;
	}

	public void tick(Level level, int x, int y) {
		if (level.time % 60 / 30 == 0) xSprite = xSprite_1;
		else xSprite = xSprite_2;

		Tile l = level.getTile(x - 1, y);
		Tile r = level.getTile(x + 1, y);
		Tile d = level.getTile(x, y + 1);
		if (l == Tile.airTile) level.setTile(x - 1, y, Tile.waterTile);
		if (r == Tile.airTile) level.setTile(x + 1, y, Tile.waterTile);
		if (d == Tile.airTile) level.setTile(x, y + 1, Tile.waterTile);
	}

	public boolean canHurt() {
		return false;
	}
}