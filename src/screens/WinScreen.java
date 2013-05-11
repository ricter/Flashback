package screens;

import processing.core.PApplet;

public class WinScreen {

	public boolean winScreenActive = false;

	int winScreenBackgroundColor = 125;
	
	PApplet gameScreen;

	public WinScreen(PApplet gameScreen) {
		
		this.gameScreen = gameScreen;

	}

	public void drawWinScreen() {

		gameScreen.background(winScreenBackgroundColor);
		gameScreen.text("You have won.Congratulations!", 100, 100);
		gameScreen.text("Press spacebar to restart.", 100, 125);

	}

}
