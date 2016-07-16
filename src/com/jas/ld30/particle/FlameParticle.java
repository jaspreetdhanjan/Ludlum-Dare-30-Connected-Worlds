package com.jas.ld30.particle;

public class FlameParticle extends Particle {
	public boolean noSmoke;

	public FlameParticle(double x, double y) {
		super(x, y);
		xSprite = 1;
		ySprite = 5;

		life /= 2;

		xa = random.nextDouble();
		ya = -1 - random.nextDouble();
		drag = 0.92;
		gravity = 0;
	}

	public void tick() {
		super.tick();

		if (isRemoved() && random.nextInt(5) == 0 && !noSmoke) {
			Particle smoke = new SmokeParticle(x, y);
			smoke.xa *= 0.1;
			smoke.ya *= 0.1;
			smoke.xa += xa;
			smoke.ya += ya;
			level.add(smoke);
		}
	}
}