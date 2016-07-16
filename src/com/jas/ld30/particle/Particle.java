package com.jas.ld30.particle;

import com.jas.ld30.Sprite;

public class Particle extends Sprite {
	public int life = 100;
	public double xa, ya;
	public double drag = 0.998;
	public double gravity = 0.08;

	public Particle(double x, double y) {
		this.x = x;
		this.y = y;

		life = random.nextInt(40) + 20;

		do {
			xa = random.nextDouble() * 2 - 1;
			ya = random.nextDouble() * 2 - 1;
		} while (xa * xa + ya * ya > 1);
		double dd = Math.sqrt(xa * xa + ya * ya);
		double speed = 1;
		xa = xa / dd * speed;
		ya = (ya / dd * speed);
	}

	public void tick() {
		if (--life < 0) {
			remove();
			return;
		}

		xa *= drag;
		ya *= drag;

		ya -= gravity;

		x += xa;
		y += ya;
	}
}