package screens;

import processing.core.PApplet;

public class SplashScreen {

	private final static int SPLASH_SCREEN_DEFAULT_BACKGROUND_COLOR = 60;
	
	PApplet gameScreen;

	public SplashScreen(PApplet gameScreen) {
		
		this.gameScreen = gameScreen;
		
	}

	public void drawSplashScreen() {

		gameScreen.background(60);
		gameScreen.text("Loading...", 100, 100);

	}

}