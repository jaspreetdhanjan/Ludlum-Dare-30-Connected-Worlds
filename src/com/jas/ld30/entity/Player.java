package com.jas.ld30.entity;

import com.jas.ld30.Input;
import com.jas.ld30.graphics.Bitmap;
import com.jas.ld30.graphics.SpriteSheet;
import com.jas.ld30.particle.FlameParticle;
import com.jas.ld30.particle.SmokeParticle;
import com.jas.ld30.weapon.Weapon;

public class Player extends Entity {
	private Input input;
	private Weapon weapon;
	private boolean wasAttacking;

	public int score;

	public Player(Input input) {
		this.input = input;
		weapon = new Weapon(this);
		x = y = 32;
		health = 100;
		xSprite = 0;
		ySprite = 6;

		weapon.load(Weapon.RIFLE);
	}

	public void tick() {
		super.tick();
		ya = 0;

		boolean xFlip = dir == EAST;
		int r = 16;
		if (xFlip) r *= -1;

		if (walkDist % 10 == 0) {
			if (health < 20) level.add(new FlameParticle(x - r / 4, y + 8));
			else level.add(new SmokeParticle(x - r / 4, y + 8));
		}

		if (input.l) {
			xa -= 0.5;
		} else if (input.r) {
			xa += 0.5;
		}

		if (input.u) {
			ya--;
		} else {
			ya++;
		}

		if (input.s) {
			if (!wasAttacking) weapon.shoot(dir);
			wasAttacking = true;
		} else {
			wasAttacking = false;
		}

		if (input._1) {
			weapon.load(Weapon.RIFLE);
		} else if (input._2) {
			weapon.load(Weapon.FLAME_THROWER);
		} else if (input._3) {
			weapon.load(Weapon.ROCKET_LAUNCHER);
		}
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

	private int t;

	public void renderHud(Bitmap b) {
		t++;

		b.drawString("Health: " + health, 4, 4 + 8 * 0);
		b.drawString("Current weapon: " + weapon.toString(), 4, 4 + 8 * 1);
		b.drawString("Weapon ammo: " + weapon.ammo[weapon.currentWeapon], 4, 4 + 8 * 2);

		b.draw(SpriteSheet.i.main[3][4], 4 + 16 * Weapon.RIFLE, 4 + 8 * 6);
		b.draw(SpriteSheet.i.main[4][4], 4 + 16 * Weapon.FLAME_THROWER, 4 + 8 * 6);
		b.draw(SpriteSheet.i.main[5][4], 4 + 16 * Weapon.ROCKET_LAUNCHER, 4 + 8 * 6);

		b.draw(SpriteSheet.i.main[7][4], 4 + 16 * weapon.currentWeapon, (4 + 8 * 4) + t % 60 / 15);
		b.draw(SpriteSheet.i.main[6][4], 4 + 16 * weapon.currentWeapon, 4 + 8 * 6);
	}
}