package screens;

import processing.core.PApplet;

public class LoseScreen {

	private static boolean loseScreenActive = false;

	private static int loseScreenBackgroundColor = 125;

	PApplet gameScreen;

	public LoseScreen(PApplet gameScreen) {
		
		this.gameScreen = gameScreen;

	}
	
	public void drawLoseScreen() {

		gameScreen.background(loseScreenBackgroundColor);
		gameScreen.text("You have lost.Sorry!", 100, 100);
		gameScreen.text("Press spacebar to restart.", 100, 125);

	}

	public boolean isLoseScreenActive() {
		return loseScreenActive;
	}

	public void setLoseScreenActive(boolean loseScreenActive) {
		this.loseScreenActive = loseScreenActive;
	}

}
