package com.jas.ld30.bullet;

import com.jas.ld30.particle.FlameParticle;
import com.jas.ld30.weapon.Weapon;

public class FlameBullet extends Bullet {
	public FlameBullet(Weapon weapon, double x, double y, double xa, double ya) {
		super(weapon, x, y, xa, ya);
		xSprite = 2;
		ySprite = 4;

		life = 45;
		dmg = 7;
	}

	public void tick() {
		for (int i = 0; i < 2; i++) {
			FlameParticle flame = new FlameParticle(x - xa * 4, y - ya * 4);
			flame.xa *= 0.5;
			flame.ya *= 0.5;
			flame.xa += xa * 1.5 * i;
			flame.ya += ya * 1.5*i;
			flame.noSmoke = true;
			level.add(flame);
		}

		super.tick();
	}
}
