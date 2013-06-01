package screens;

import processing.core.PApplet;

public class WinScreen {

	private static boolean winScreenActive = false;

	private static final int winScreenBackgroundColor = 125;

	PApplet gameScreen;

	public WinScreen(PApplet gameScreen) {
		
		this.gameScreen = gameScreen;

	}
	
	public void drawWinScreen() {

		gameScreen.background(winScreenBackgroundColor);
		gameScreen.text("You have won. Congratulations!", 100, 100);
		gameScreen.text("Press spacebar to restart.", 100, 125);

	}

	public boolean isWinScreenActive() {
		return winScreenActive;
	}

	public void setWinScreenActive(boolean winScreenActive) {
		this.winScreenActive = winScreenActive;
	}

}
