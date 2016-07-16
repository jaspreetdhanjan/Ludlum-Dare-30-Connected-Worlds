package com.jas.ld30.particle;

import com.jas.ld30.audio.Sound;
import com.jas.ld30.graphics.Bitmap;

public class Explosion extends Particle {
	public int spread = 20;
	
	public Explosion(double x, double y) {
		super(x, y);
		Sound.explosionSound.play();
	}

	public void tick() {
		super.tick();
	}

	public void render(Bitmap b) {
		int ps = life * spread;
		
		for (int i = 0; i < ps; i++) {
			double dir = random.nextDouble() * Math.PI * 2;
			double dist = random.nextDouble() * 6;
			double xx = x + Math.cos(dir) * dist;
			double yy = y + Math.sin(dir) * dist;

			FlameParticle fd = new FlameParticle((int) xx, (int) yy);
			if (random.nextInt(2) == 0) fd.life /= 2;
			fd.xa *= 0.1;
			fd.ya *= 0.1;
			fd.xa += (xx - x) * 0.5;
			fd.ya += (yy - y) * 0.5;
			level.add(fd);
		}
	}
}