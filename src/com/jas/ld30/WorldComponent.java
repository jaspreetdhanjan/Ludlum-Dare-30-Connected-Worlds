package com.jas.ld30;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.jas.ld30.graphics.Bitmap;
import com.jas.ld30.graphics.SpriteSheet;
import com.jas.ld30.screen.GameScreen;
import com.jas.ld30.screen.Screen;

public class WorldComponent extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	public static final String TITLE = "Untitled Game";

	private boolean stop = false;
	private BufferedImage screenImage;
	private Bitmap screenBitmap;
	private Screen screen;
	private Input input;

	public WorldComponent() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);

		JFrame frame = new JFrame(TITLE);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void stop() {
		stop = true;
	}

	public void run() {
		init();

		int frames = 0;
		int ticks = 0;
		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;

		while (!stop) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean render = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				render = true;

				ticks++;
				if (ticks % 60 == 0) {
					System.out.println(frames + " fps " + ticks + " ticks");
					lastTime += 1000;
					frames = 0;
					ticks = 0;
				}
			}

			if (render) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void init() {
		SpriteSheet.create();
		screenImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		screenBitmap = new Bitmap(screenImage);
		input = new Input(this);

		setScreen(new GameScreen());
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
		screen.init(this, input);
	}

	private void tick() {
		screen.tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		screenBitmap.clear(0);
		screen.render(screenBitmap);

		int w = WIDTH * SCALE;
		int h = HEIGHT * SCALE;
		int screenW = getWidth();
		int screenH = getHeight();
		int x = (screenW - w) / 2 + 16;
		int y = (screenH - h) / 2 + 16;

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w, h);
		g.drawImage(screenImage, x, y, w, h, null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Thread(new WorldComponent(), "Game Thread").start();
	}
}