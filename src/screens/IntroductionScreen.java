package screens;

import processing.core.PApplet;

public class IntroductionScreen {

	private boolean introductionScreenActive = false;

	private static final int introductionScreenBackgroundColor = 60;

	PApplet gameScreen;

	public IntroductionScreen(PApplet gameScreen, boolean introductionScreenActive) {

		this.gameScreen = gameScreen;
		this.setIntroductionScreenActive(introductionScreenActive);

	}

	public void drawIntroductionScreen() {

		gameScreen.background(introductionScreenBackgroundColor);
		gameScreen.text("Press any key to start the game.", 100, 100);

	}

	public boolean isIntroductionScreenActive() {
		return introductionScreenActive;
	}

	public void setIntroductionScreenActive(boolean introductionScreenActive) {
		this.introductionScreenActive = introductionScreenActive;
	}

}
