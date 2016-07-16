package com.jas.ld30.particle;

public class SmokeParticle extends Particle {
	public SmokeParticle(double x, double y) {
		super(x, y);
		xSprite = 0;
		ySprite = 5;

		drag = 0.92;
		gravity = -0.02;
	}
}