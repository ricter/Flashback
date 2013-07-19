package screens;

import main.Flashback;
import processing.core.PApplet;

public class InstructionScreen {

    private static final int instructionScreenBackgroundColor = 125;
    private static final int screenNumberMax = 3;
    
    private boolean instructionScreenActive = false;
	private int currentScreenNumber = 1;
	
	private PApplet gameScreen;

	public InstructionScreen(PApplet gameScreen) {
		this.gameScreen = gameScreen;
	}

	public void drawInstructionScreen(){
	    
        switch (currentScreenNumber) {
        
        case 1:
            drawInstructionScreen1();
            break;
            
        case 2:
            drawInstructionScreen2();
            break;
            
        case 3:
            drawInstructionScreen3();
            break;
            
        }
	    
	}
	
	public void changeScreenNumber(boolean goRight){
	    
	    if (goRight){
	        if ( currentScreenNumber < screenNumberMax ){
	            currentScreenNumber++;
	        }
	    } else {
	        if ( currentScreenNumber > 0 ){
                currentScreenNumber--;
            }
	    }
	    
	}
	
	private void drawInstructionScreen1() {

		gameScreen.background(instructionScreenBackgroundColor);
		gameScreen.text("Objectives", 100, 100);
		
		gameScreen.text("Use W & D or Left Arrow & Right Arrow to navigate screens.  Press Esc to resume playing!", 100, 400, Flashback.xResolution - 100, Flashback.yResolution - 500);

	}
	
	private void drawInstructionScreen2() {

        gameScreen.background(instructionScreenBackgroundColor);
        gameScreen.text("Controls", 100, 100);
        gameScreen.text("W: Move up", 100, 125);
        gameScreen.text("A: Move left", 100, 150);
        gameScreen.text("S: Move down", 100, 175);
        gameScreen.text("D: Move right", 100, 200);
        gameScreen.text("Mouse Wheel: Adjusts heart beat by a limited amount.", 100, 225);
        
        gameScreen.text("Use W & D or Left Arrow & Right Arrow to navigate screens.  Press Esc to resume playing!", 100, 400, Flashback.xResolution - 100, Flashback.yResolution - 500);

    }
	
	private void drawInstructionScreen3() {

        gameScreen.background(instructionScreenBackgroundColor);
        gameScreen.text("Placeholder", 100, 100);
        
        gameScreen.text("Use W & D or Left Arrow & Right Arrow to navigate screens.  Press Esc to resume playing!", 100, 400, Flashback.xResolution - 100, Flashback.yResolution - 500);

    }

	public boolean isInstructionScreenActive() {
		return instructionScreenActive;
	}

	public void setInstructionScreenActive(boolean instructionScreenActive) {
		this.instructionScreenActive = instructionScreenActive;
	}

}
