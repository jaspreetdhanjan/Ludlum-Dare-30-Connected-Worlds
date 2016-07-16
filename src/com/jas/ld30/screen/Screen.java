package com.jas.ld30.screen;

import com.jas.ld30.Input;
import com.jas.ld30.WorldComponent;
import com.jas.ld30.graphics.Bitmap;

public class Screen {
	public WorldComponent component;
	public Input input;

	public void init(WorldComponent component, Input input) {
		this.component = component;
		this.input = input;
	}

	public void tick() {
	}

	public void render(Bitmap b) {
	}
}