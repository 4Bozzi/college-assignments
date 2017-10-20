package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * The Sound class that has all static methods so that any part of the view can
 * access sounds without having to create a sound object. this also makes it
 * very easy to turn on/off the sound with a boolean value and a couple of
 * methods.
 * 
 * @author Matt Giardina
 * 
 */
public class Sound extends Thread {

	private static boolean soundStatus = true;

	/**
	 * Play the introduction music that the user will hear while they are
	 * setting up the game.
	 */
	public void playIntro() {
		// Only Play is Sound is Allowed
		if (soundStatus) {
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"sounds/unloaded.au")));
				AudioPlayer.player.stop(new AudioStream(new FileInputStream(
						"sounds/unloaded.au")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Play a sound when the Move Button is hit, which moves Armies to a new
	 * Territory.
	 */
	public static void playMove() {
		// Only Play is Sound is Allowed
		if (soundStatus) {
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"sounds/move.au")));
				AudioPlayer.player.stop(new AudioStream(new FileInputStream(
						"sounds/move.au")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Play a Sound when Attack Button is Clicked
	 */
	public static void playAttack() {
		// Only Play is Sound is Allowed
		if (soundStatus) {
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"sounds/attack.au")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Play a sound when reinforcing a planet
	 */
	public static void playReinforce() {
		// Only Play is Sound is Allowed
		if (soundStatus) {
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"sounds/reinforce.au")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Play Next Stage Sound.
	 */
	public static void playStageChange() {
		// Only Play is Sound is Allowed
		if (soundStatus) {
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"sounds/stagechange.au")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Play the Destroy Territory sound when a territory is taken over (Changes
	 * Owner)
	 */
	public static void playDestroy() {
		// Only Play if Sound is allowed.
		if (soundStatus) {
			try {
				AudioPlayer.player.start(new AudioStream(new FileInputStream(
						"sounds/destroy.au")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	/**
	 * Turn the sound off for the whole game
	 */
	public static void off() {
		soundStatus = false;
	}

	/**
	 * Turn the sound on for the whole game
	 */
	public static void on() {
		soundStatus = true;
	}
}
