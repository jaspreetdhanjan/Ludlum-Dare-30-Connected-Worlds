package com.jas.ld30.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.jas.ld30.Sprite;
import com.jas.ld30.entity.Portal;
import com.jas.ld30.graphics.SpriteSheet;
import com.jas.ld30.level.Level;
import com.jas.ld30.level.tile.Tile;

public class LevelData {
	public final int w, h;
	private final int[] pixels;

	public LevelData(String path) {
		BufferedImage worldBitmap = SpriteSheet.i.loadImage(path);
		w = worldBitmap.getWidth();
		h = worldBitmap.getHeight();
		pixels = new int[w * h];
		worldBitmap.getRGB(0, 0, w, h, pixels, 0, w);
	}

	public void createLevel(Level level) {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Tile loadedTiles = getBitmapTiles(x, y);
				List<Sprite> loadedSprites = getBitmapSprites(x, y);

				level.setTile(x, y, loadedTiles);
				for (int i = 0; i < loadedSprites.size(); i++) {
					level.add(loadedSprites.get(i));
				}
			}
		}
	}

	private List<Sprite> getBitmapSprites(int x, int y) {
		List<Sprite> sprites = new ArrayList<Sprite>();

		if (pixels[x + y * w] == 0xffff0000) sprites.add(new Portal(x * 16, y * 16));
		return sprites;
	}

	private Tile getBitmapTiles(int x, int y) {
		if (pixels[x + y * w] == 0xff787878) return Tile.concreteTile;
		if (pixels[x + y * w] == 0xff895d00) return Tile.dirtTile;
		if (pixels[x + y * w] == 0xffffffff) return Tile.airTile;
		if (pixels[x + y * w] == 0xff0000ff) return Tile.waterTile;
		return Tile.voidTile;
	}
}