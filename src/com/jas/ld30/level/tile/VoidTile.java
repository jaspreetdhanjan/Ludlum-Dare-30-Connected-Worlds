package com.jas.ld30.level.tile;

import com.jas.ld30.entity.Entity;

public class VoidTile extends Tile {
	public VoidTile(int id) {
		super(id);
		xSprite = 0;
		ySprite = 0;
	}
	
	public boolean isCollidable(Entity entity) {
		return true;
	}
}