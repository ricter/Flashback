package screens;

import processing.core.PApplet;

public class InstructionScreen {

	private boolean instructionScreenActive = false;

	int instructionScreenBackgroundColor = 125;
	
	PApplet gameScreen;

	public InstructionScreen(PApplet gameScreen) {
		this.gameScreen = gameScreen;
	}

	public void drawInstructionScreen() {

		gameScreen.background(instructionScreenBackgroundColor);
		gameScreen.text("Controls", 100, 100);
		gameScreen.text("W: Move up", 100, 125);
		gameScreen.text("A: Move left", 100, 150);
		gameScreen.text("S: Move down", 100, 175);
		gameScreen.text("D: Move right", 100, 200);

	}

	public boolean isInstructionScreenActive() {
		return instructionScreenActive;
	}

	public void setInstructionScreenActive(boolean instructionScreenActive) {
		this.instructionScreenActive = instructionScreenActive;
	}

}
