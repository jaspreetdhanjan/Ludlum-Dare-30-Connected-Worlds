package com.jas.ld30.entity;

import com.jas.ld30.Sprite;
import com.jas.ld30.bullet.Bullet;
import com.jas.ld30.level.tile.Tile;

public class Entity extends Sprite {
	public static final int EAST = 0;
	public static final int WEST = 1;

	public double xa, ya;
	public int dir, walkDist;
	public int health = 10;
	public boolean hasDied = false;

	public void tick() {
		if (hasDied) remove();

		if (xa != 0 || ya != 0) {
			tryMove();
		}
	}

	public void tryMove() {
		int steps = (int) Math.sqrt(xa * xa + ya * ya);
		steps++;

		for (int i = 0; i < steps; i++) {
			moveStep(xa / steps, 0);
			moveStep(0, ya / steps);
		}
	}

	private boolean moveStep(double xa, double ya) {
		double xxa = x + xa;
		double yya = y + ya;

		if (xxa < 0 || yya < 0 || xxa >= level.w * 16 || yya >= level.h * 16) {
			collide(null, xxa, yya);
			return false;
		}

		/*		List<Entity> entities = level.getEntities(xxa - xs, yya - ys, xxa + xs, yya + ys);
				for (Entity e : entities) {
					if (e == this) continue;
					if (e.stops(this) && stops(e)) {
						collide(e, xxa, yya);
						return false;
					}
				}*/

		for (int c = 0; c < 4; c++) {
			int xn = (int) xxa;
			int yn = (int) yya;
			int xt = (xn + (c % 2 * 2 - 1) * xs + 8) / 16;
			int yt = (yn + (c / 2 * 2 - 1) * ys + 8) / 16;

			Tile t = level.getTile(xt, yt);

			if (t.isCollidable(this)) {
				collide(null, xxa, yya);
				return false;
			}
		}

		if (xa < 0) dir = EAST;
		if (xa > 0) dir = WEST;

		x = xxa;
		y = yya;
		walkDist++;
		return true;
	}

	public void collide(Entity otherEntity, double xxa, double yya) {
		if (xxa != 0) xa = 0;
		if (yya != 0) ya = 0;
	}

	public boolean intersects(double x0, double y0, double x1, double y1) {
		return !(x + xs < x0 || y + ys < y0 || x - xs > x1 || y - ys > y1);
	}

	public void hitBy(Bullet bullet) {
		hurt(bullet.dmg);
	}

	public void hurt(int dmg) {
		health -= dmg;

		if (health <= 0) {
			hasDied = true;
		}
	}

	public boolean onGround() {
		int xt = (int) (x / 16.0);
		int yt = (int) (y / 16.0) + 2;
		return level.getTile(xt, yt).isCollidable(this);
	}
}