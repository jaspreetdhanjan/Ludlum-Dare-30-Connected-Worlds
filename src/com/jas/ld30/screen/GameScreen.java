package com.jas.ld30.screen;

import com.jas.ld30.Input;
import com.jas.ld30.WorldComponent;
import com.jas.ld30.entity.Player;
import com.jas.ld30.graphics.Bitmap;
import com.jas.ld30.level.Level;
import com.jas.ld30.level.LevelData;

public class GameScreen extends Screen {
	public Level[] levels = new Level[4];
	public Player player;
	private int pp = 0;
	private boolean lostGame, wonGame;

	public void init(WorldComponent component, Input input) {
		super.init(component, input);

		levels[0] = new Level(new LevelData("/worlds/world0.png"), this);
		levels[1] = new Level(new LevelData("/worlds/world1.png"), this);
		levels[2] = new Level(new LevelData("/worlds/world2.png"), this);
		levels[3] = new Level(new LevelData("/worlds/world3.png"), this);

		player = new Player(input);
		levels[pp].add(player);
	}

	public void tick() {
		levels[pp].tick();

		if (player.hasDied || player.isRemoved()) {
			lostGame = true;
		}

		if (lostGame) {
			component.setScreen(new LostGameScreen());
		} else if (wonGame) {
			component.setScreen(new FinishedGameScreen());
		}
	}

	public void nextLevel() {
		if (pp >= 3) {
			if (pp == 3) wonGame = true;
			return;
		}

		pp++;
		levels[pp].add(player);
	}

	public void render(Bitmap b) {
		levels[pp].render(b);
		player.renderHud(b);
	}
}