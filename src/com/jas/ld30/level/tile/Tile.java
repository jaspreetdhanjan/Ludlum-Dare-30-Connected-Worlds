package com.jas.ld30.level.tile;

import com.jas.ld30.bullet.Bullet;
import com.jas.ld30.entity.Entity;
import com.jas.ld30.graphics.Bitmap;
import com.jas.ld30.graphics.SpriteSheet;
import com.jas.ld30.level.Level;

public class Tile {
	public static final Tile[] DEFINED_TILES = new Tile[128];

	public static Tile voidTile = new VoidTile(0);
	public static Tile concreteTile = new ConcreteTile(1);
	public static Tile airTile = new AirTile(2);
	public static Tile dirtTile = new DirtTile(3);
	public static Tile waterTile = new WaterTile(4);

	public byte id;
	public int xSprite = -1;
	public int ySprite = -1;

	public Tile(int id) {
		this.id = (byte) id;
		if (DEFINED_TILES[id] != null) throw new RuntimeException("Tile is already defined!");
		DEFINED_TILES[id] = this;
	}

	public void render(Level level, Bitmap b, int x, int y) {
		int xp = x << 4;
		int yp = y << 4;

		b.draw(SpriteSheet.i.main[xSprite][ySprite], xp, yp);
	}

	public boolean isCollidable(Entity entity) {
		return false;
	}

	public void hurt(Bullet bullet, Level level, int x, int y) {
		if (!canHurt()) return;

		int dmg = level.getData(x, y);

		if (dmg > 10) {
			level.setTile(x, y, Tile.airTile);
			level.setData(x, y, 0);
		} else {
			level.setData(x, y, bullet.dmg + dmg);
		}
	}

	public boolean canHurt() {
		return true;
	}

	public void tick(Level level, int x, int y) {
	}
}