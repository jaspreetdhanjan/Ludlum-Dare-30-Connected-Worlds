package com.jas.ld30;

import java.util.Random;

import com.jas.ld30.graphics.Bitmap;
import com.jas.ld30.graphics.SpriteSheet;
import com.jas.ld30.level.Level;

public class Sprite {
	public static final Random random = new Random();

	public double x, y;
	public int xs = 16, ys = 16;
	public Level level;

	public int xSprite = -1;
	public int ySprite = -1;

	private boolean removed = false;

	public final void init(Level level) {
		this.level = level;
		init();
	}

	public void init() {
	}

	public void tick() {
	}

	public void render(Bitmap b) {
		b.draw(SpriteSheet.i.main[xSprite][ySprite], (int) x, (int) y);
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void onRemoved() {
	}
}