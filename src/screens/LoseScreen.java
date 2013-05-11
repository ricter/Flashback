package screens;

import processing.core.PApplet;

public class LoseScreen {

	public boolean loseScreenActive = false;

	int loseScreenBackgroundColor = 125;
	
	PApplet gameScreen;

	public LoseScreen(PApplet gameScreen) {
		
		this.gameScreen = gameScreen;

	}

	public void drawLoseScreen() {

		gameScreen.background(loseScreenBackgroundColor);
		gameScreen.text("You have lost.Sorry!", 100, 100);
		gameScreen.text("Press spacebar to restart.", 100, 125);

	}

}
