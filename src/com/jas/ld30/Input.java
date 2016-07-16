package com.jas.ld30;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	public boolean u, d, l, r, s;
	public boolean _1, _2, _3;

	public Input(WorldComponent world) {
		world.addKeyListener(this);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		handlePress(e, true);
	}

	public void keyReleased(KeyEvent e) {
		handlePress(e, false);
	}

	private void handlePress(KeyEvent e, boolean pressed) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) u = pressed;
		if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) d = pressed;
		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) l = pressed;
		if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) r = pressed;
		if (code == KeyEvent.VK_SPACE) s = pressed;
		if (code == KeyEvent.VK_1) _1 = pressed;
		if (code == KeyEvent.VK_2) _2 = pressed;
		if (code == KeyEvent.VK_3) _3 = pressed;
		
	}
}