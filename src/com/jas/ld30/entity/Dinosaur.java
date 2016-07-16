package com.jas.ld30.entity;

import com.jas.ld30.bullet.DinoBomb;
import com.jas.ld30.graphics.Bitmap;
import com.jas.ld30.graphics.SpriteSheet;
import com.jas.ld30.level.tile.Tile;
import com.jas.ld30.particle.Explosion;
import com.jas.ld30.weapon.Weapon;

public class Dinosaur extends Entity {
	private int jumpTime;
	private Weapon weapon = new Weapon(this);

	public Dinosaur(double x, double y) {
		this.x = x;
		this.y = y;

		xSprite = 2;
		ySprite = 6;
	}

	public void tick() {
		if (jumpTime > 0) jumpTime--;
		boolean onGround = onGround();
		if (!onGround && jumpTime == 0) {
			xSprite = 4;
			ya = 1;
		}

		Player enemy = level.getPlayer();

		if (random.nextInt(60) == 0) {
			if (enemy.x < x) xa = -1;
			if (enemy.x > x) xa = +1;

			if (onGround) {
				ya = -1;
				xSprite = 2;
				jumpTime = 30;
			}
		}

		if (random.nextInt(50) == 0) {
			double xd = enemy.x - x;
			double yd = enemy.y - y;
			double dd = Math.sqrt(xd * xd + yd * yd);
			xd /= dd;
			yd /= dd;

			double dir = Math.atan2(yd, xd);
			double xa = Math.cos(dir);
			double ya = Math.sin(dir);

			level.add(new DinoBomb(weapon, x, y, xa, ya));
		}

		super.tick();
	}

	public void render(Bitmap b) {
		Bitmap[][] s = SpriteSheet.i.main;

		boolean xFlip = dir == EAST;
		int r = 16;
		if (xFlip) r *= -1;

		int xp = (int) x;
		int yp = (int) y;

		b.xFlip = xFlip;
		b.draw(s[xSprite][ySprite], xp, yp);
		b.draw(s[xSprite + 1][ySprite], xp + r, yp);
		b.draw(s[xSprite][ySprite + 1], xp, yp + 16);
		b.draw(s[xSprite + 1][ySprite + 1], xp + r, yp + 16);
		b.xFlip = false;
	}

	public void onRemoved() {
		Explosion e = new Explosion(x, y);
		e.spread = 40;
		level.add(e);
	}

	public void findStartPos() {
		while (true) {
			int xx = random.nextInt(level.w);
			int yy = random.nextInt(level.h);

			if (level.getTile(xx, yy) == Tile.airTile) {
				System.out.println("tile is an air tile");
				x = xx * 16;
				y = yy * 16;
				return;
			}
		}
	}
}