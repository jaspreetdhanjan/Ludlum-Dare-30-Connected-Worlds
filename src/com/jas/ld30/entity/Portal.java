package com.jas.ld30.entity;

import java.util.List;

import com.jas.ld30.graphics.Bitmap;
import com.jas.ld30.graphics.SpriteSheet;

public class Portal extends Entity {
	public Portal(double x, double y) {
		this.x = x;
		this.y = y;

		xSprite = 6;
		ySprite = 6;
	}

	public void tick() {
		int xr = 8;
		int yr = 8;

		List<Entity> entities = level.getEntities(x - xr, y - yr, x + xr, y + yr);
		for (Entity e : entities) {
			if (e == this) continue;
			
			if (e instanceof Player) {
				level.nextLevel();
			}
		}
	}

	public void render(Bitmap b) {
		Bitmap[][] s = SpriteSheet.i.main;

		int r = 16;
		int xp = (int) x;
		int yp = (int) y;

		b.draw(s[xSprite][ySprite], xp, yp);
		b.draw(s[xSprite + 1][ySprite], xp + r, yp);
		b.draw(s[xSprite][ySprite + 1], xp, yp + 16);
		b.draw(s[xSprite + 1][ySprite + 1], xp + r, yp + 16);
	}
}