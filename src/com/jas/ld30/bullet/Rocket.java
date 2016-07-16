package com.jas.ld30.bullet;

import com.jas.ld30.weapon.Weapon;

public class Rocket extends Bullet {
	public Rocket(Weapon weapon, double x, double y, double xa, double ya) {
		super(weapon, x, y, xa, ya);
		xSprite = 1;
		ySprite = 4;
		dmg = 15;
	}

	public void onRemoved() {
		level.addExplosion(this, x, y);
	}
}