package com.jas.ld30.bullet;

import java.util.List;

import com.jas.ld30.Sprite;
import com.jas.ld30.entity.Entity;
import com.jas.ld30.weapon.Weapon;

public class Bullet extends Sprite {
	public Weapon weapon;
	public int life;
	public double xa, ya;
	public int dmg = 5;
	public double speed = 2;

	public Bullet(Weapon weapon, double x, double y, double xa, double ya) {
		this.weapon = weapon;
		this.x = x;
		this.y = y;
		this.xa = xa * speed;
		this.ya = ya * speed;
		life = 40 + random.nextInt(20);

		xSprite = 0;
		ySprite = 4;
	}

	public void tick() {
		if (--life < 0) {
			remove();
			return;
		}

		if (xa != 0 || ya != 0) {
			tryMove();
		}
	}

	private boolean tryMove() {
		double xxa = x + xa;
		double yya = y + ya;

		if (xxa < 0 || yya < 0 || xxa >= level.w * 16 || yya >= level.h * 16) {
			remove();
			return false;
		}

		List<Entity> entities = level.getEntities(xxa - xs, yya - ys, xxa + xs, yya + ys);
		for (Entity e : entities) {
			if (e == weapon.owner) continue;

			e.hitBy(this);
			collide(e, xxa, yya);
			return false;
		}

		int xt = (int) (xxa / 16);
		int yt = (int) (yya / 16);
		level.getTile(xt, yt).hurt(this, level, xt, yt);

		x = xxa;
		y = yya;
		return true;
	}

	public void collide(Entity e, double xxa, double yya) {
		remove();
	}
}