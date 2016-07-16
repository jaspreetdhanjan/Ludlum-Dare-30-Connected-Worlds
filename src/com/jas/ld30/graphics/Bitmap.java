package com.jas.ld30.graphics;

import java.awt.image.*;

public class Bitmap {
	private static final String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + "abcdefghijklmnopqrstuvwxyz_               " + "0123456789+-=*:;                              " + "";

	public final int w, h;
	public final int[] pixels;
	public final int alphaChannel = 0xffff00ff;

	public boolean xFlip = false;
	public boolean yFlip = false;

	public Bitmap(int w, int h, int[] pixels) {
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
	}

	public Bitmap(BufferedImage img) {
		w = img.getWidth();
		h = img.getHeight();
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	public void draw(Bitmap b, int xp, int yp) {
		for (int y = 0; y < b.h; y++) {
			int yo = y + yp;
			if (yo < 0 || yo >= h) continue;

			for (int x = 0; x < b.w; x++) {
				int xo = x + xp;
				if (xo < 0 || xo >= w) continue;

				int xf = b.w - 1 - x;
				int yf = b.h - 1 - y;

				int src = b.pixels[x + y * b.w];

				if (xFlip) src = b.pixels[xf + y * b.w];
				if (yFlip) src = b.pixels[x + yf * b.w];

				if (src != alphaChannel) pixels[xo + yo * w] = src;
			}
		}
	}

	public void clear(int col) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = col;
		}
	}

	public void fill(int x0, int y0, int x1, int y1, int color) {
		for (int y = y0; y < y1; y++) {
			if (y < 0 || y >= h) continue;

			for (int x = x0; x < x1; x++) {
				if (x < 0 || x >= w) continue;

				pixels[x + y * w] = color;
			}
		}
	}

	public void drawQuad(int x, int y, int w, int h, int color) {
		fill(x, y, x + w, y + h, color);
	}

	public void drawString(String msg, int x, int y) {
		for (int i = 0; i < msg.length(); i++) {
			int ch = chars.indexOf(msg.charAt(i));
			int xx = ch % 42;
			int yy = ch / 42;
			if (ch >= 0) draw(SpriteSheet.i.font[xx][yy], x + i * 6, y);
		}
	}
}