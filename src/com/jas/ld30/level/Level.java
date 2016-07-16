package com.jas.ld30.level;

import java.util.*;

import com.jas.ld30.*;
import com.jas.ld30.bullet.*;
import com.jas.ld30.entity.*;
import com.jas.ld30.graphics.*;
import com.jas.ld30.level.tile.*;
import com.jas.ld30.particle.*;
import com.jas.ld30.screen.*;

public class Level {
	public List<Sprite> sprites = new ArrayList<Sprite>();
	public boolean spritesDirty = true;
	private Comparator<Sprite> spriteSorter = new Comparator<Sprite>() {
		public int compare(Sprite s0, Sprite s1) {
			if (s1.y < s0.y) return +1;
			if (s1.y > s0.y) return -1;
			if (s1.x < s0.x) return +1;
			if (s1.x > s0.x) return -1;
			return 0;
		}
	};

	public final int w;
	public final int h;
	public final Tile[] tiles;
	public final byte[] data;

	private GameScreen screen;
	public int time = 0;

	public Level(LevelData data, GameScreen screen) {
		w = data.w;
		h = data.h;
		tiles = new Tile[w * h];
		this.data = new byte[w * h];
		this.screen = screen;

		data.createLevel(this);

		for (int i = 0; i < 10; i++) {
			add(new Dinosaur(Math.random() * w * 16, Math.random() * h * 16));
		}
	}

	public void tick() {
		time++;

		for (int i = 0; i < sprites.size(); i++) {
			Sprite s = sprites.get(i);

			if (s.isRemoved()) {
				remove(s);
				continue;
			}

			double xo = s.x;
			double yo = s.y;

			s.tick();

			double xn = s.x;
			double yn = s.y;

			if (xo != xn || yo != yn) spritesDirty = true;
		}

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				getTile(x, y).tick(this, x, y);
			}
		}
	}

	public void render(Bitmap b) {
		Collections.sort(sprites, spriteSorter);

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				getTile(x, y).render(this, b, x, y);
			}
		}

		for (int i = 0; i < sprites.size(); i++) {
			sprites.get(i).render(b);
		}
	}

	public void add(Sprite sprite) {
		sprites.add(sprite);
		sprite.init(this);
	}

	public void remove(Sprite sprite) {
		sprite.onRemoved();
		sprites.remove(sprite);
	}

	public byte getData(int x, int y) {
		if (x < 0 || y < 0 || x >= w || y >= h) return 0;
		return data[x + y * w];
	}

	public void setData(int x, int y, int d) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		data[x + y * w] = (byte) d;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= w || y >= h) return Tile.voidTile;
		return tiles[x + y * w];
	}

	public void setTile(int x, int y, Tile t) {
		if (x < 0 || y < 0 || x >= w || y >= h) return;
		tiles[x + y * w] = t;
	}

	public List<Entity> getEntities(double x0, double y0, double x1, double y1) {
		List<Entity> result = new ArrayList<Entity>();

		for (int i = 0; i < sprites.size(); i++) {
			Sprite s = sprites.get(i);

			if (s instanceof Entity) {
				Entity e = (Entity) s;
				if (e.intersects(x0, y0, x1, y1)) result.add(e);
			}
		}
		return result;
	}

	public void addExplosion(Bullet b, double x, double y) {
		int rr = 20;
		List<Entity> hurtEntities = getEntities(x - rr, y - rr, x + rr, y + rr);
		for (Entity e : hurtEntities) {
			double xd = e.x - x;
			double yd = e.y - y;
			double dd = (xd * xd + yd * yd);

			if (dd < rr * rr) {
				xd /= dd;
				yd /= dd;
				dd /= rr;

				int dmg = (int) (b.dmg + dd);
				e.hurt(dmg);
			}
		}

		int xt = (int) (x / 16);
		int yt = (int) (y / 16);

		int dmg = b.dmg + getData(xt, yt);
		if (dmg > 10) {
			setTile(xt, yt, Tile.airTile);
		} else {
			setData(xt, yt, dmg);
		}

		add(new Explosion(x, y));
	}

	public void nextLevel() {
		screen.nextLevel();
		return;
	}

	public Player getPlayer() {
		return screen.player;
	}
}