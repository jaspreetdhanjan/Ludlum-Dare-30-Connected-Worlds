package com.jas.ld30.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public static SpriteSheet i;

	public static void create() {
		i = new SpriteSheet();
	}

	public Bitmap[][] main = getSpriteSheet("/spritesheet.png", 16, 16);
	public Bitmap[][] font = getSpriteSheet("/font.png", 6, 8);

	private Bitmap[][] getSpriteSheet(String path, int xs, int ys) {
		BufferedImage img = loadImage(path);

		int w = img.getWidth();
		int h = img.getHeight();
		int ww = w / xs;
		int hh = h / ys;

		Bitmap[][] result = new Bitmap[ww][hh];
		for (int y = 0; y < hh; y++) {
			for (int x = 0; x < ww; x++) {
				result[x][y] = new Bitmap(xs, ys);
				img.getRGB(x * xs, y * ys, xs, ys, result[x][y].pixels, 0, xs);
			}
		}
		return result;
	}

	public BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}