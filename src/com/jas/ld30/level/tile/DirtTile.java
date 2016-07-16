package com.jas.ld30.level.tile;

import com.jas.ld30.entity.Entity;

public class DirtTile extends Tile {
	public DirtTile(int id) {
		super(id);

		xSprite = 3;
		ySprite = 0;
	}
	
	public boolean isCollidable(Entity entity) {
		return true;
	}
}