package com.jas.ld30.level.tile;

import com.jas.ld30.entity.Entity;

public class ConcreteTile extends Tile {
	public ConcreteTile(int id) {
		super(id);
		xSprite = 1;
		ySprite = 0;
	}
	
	public boolean isCollidable(Entity entity) {
		return true;
	}
}