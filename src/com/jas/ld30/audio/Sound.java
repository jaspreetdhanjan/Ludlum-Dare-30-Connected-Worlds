package com.jas.ld30.audio;

import javax.sound.sampled.*;

public class Sound {
	public static Sound explosionSound = loadSound("/sound/explosion.wav");
	public static Sound rifleSound = loadSound("/sound/rifle.wav");
	public static Sound burningSound = loadSound("/sound/burning.wav");

	public static Sound loadSound(String fileName) {
		Sound sound = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		} catch (Exception e) {
			System.out.println(e);
		}
		return sound;
	}

	private Clip clip;

	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}