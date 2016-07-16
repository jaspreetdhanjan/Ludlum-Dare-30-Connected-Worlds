package com.jas.ld30.bullet;

import com.jas.ld30.particle.FlameParticle;
import com.jas.ld30.weapon.Weapon;

public class DinoBomb extends Bullet {
	public DinoBomb(Weapon weapon, double x, double y, double xa, double ya) {
		super(weapon, x, y, xa, ya);
		xSprite = 2;
		ySprite = 5;
	}

	public void onRemoved() {
		for (int i = 0; i < 10; i++) {
			level.add(new FlameParticle(x, y));
		}
	}
}