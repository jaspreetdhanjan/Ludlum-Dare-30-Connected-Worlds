package com.jas.ld30.weapon;

import com.jas.ld30.audio.Sound;
import com.jas.ld30.bullet.*;
import com.jas.ld30.entity.*;

public class Weapon {
	public static final int RIFLE = 0;
	public static final int FLAME_THROWER = 1;
	public static final int ROCKET_LAUNCHER = 2;

	public Entity owner;
	public int currentWeapon;

	public int[] ammo = new int[3];
	public boolean[] noAmmo = new boolean[3];

	public Weapon(Entity owner) {
		this.owner = owner;
		topupAmmo();
	}

	public void topupAmmo() {
		ammo[RIFLE] = 90;
		ammo[FLAME_THROWER] = 60;
		ammo[ROCKET_LAUNCHER] = 15;
	}

	public void load(int currentWeapon) {
		this.currentWeapon = currentWeapon;
	}

	public void shoot(int dir) {
		noAmmo[currentWeapon] = (ammo[currentWeapon] < 0);

		int attackDir = 0;
		if (dir == Entity.EAST) {
			attackDir = -1;
		} else if (dir == Entity.WEST) {
			attackDir = +1;
		}

		int xx = attackDir * 20;
		int yy = 10;

		Bullet b = null;
		if (currentWeapon == RIFLE) {
			b = new Bullet(this, owner.x + xx, owner.y + yy, attackDir, 0);
			Sound.rifleSound.play();
		}

		if (currentWeapon == FLAME_THROWER) b = new FlameBullet(this, owner.x + xx, owner.y + yy, attackDir, 0);
		if (currentWeapon == ROCKET_LAUNCHER) b = new Rocket(this, owner.x + xx, owner.y + yy, attackDir, 0);

		if (b != null) {
			owner.level.add(b);
			ammo[currentWeapon] -= 1;
		}
	}

	public String toString() {
		if (currentWeapon == RIFLE) return "Rifle";
		if (currentWeapon == FLAME_THROWER) return "Flamethrower";
		if (currentWeapon == ROCKET_LAUNCHER) return "Rocket Launcher";
		return super.toString();
	}
}