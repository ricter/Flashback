package screens;

import main.Flashback;
import processing.core.PApplet;

public class InstructionScreen {

    private static final int instructionScreenBackgroundColor = 125;
    private static final int screenNumberMax = 3;
    
    private boolean instructionScreenActive = false;
	private int currentScreenNumber = 1;
	
	private int textShift = 40;
	
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
		gameScreen.text("Survive.", 100, 100+textShift);
		
		gameScreen.text("Use W & D or Left Arrow & Right Arrow to navigate screens.  Press Esc to resume playing!", 100, 400, Flashback.X_RESOLUTION - 100, Flashback.Y_RESOLUTION - 200);

	}
	
	private void drawInstructionScreen2() {

        gameScreen.background(instructionScreenBackgroundColor);
        gameScreen.text("Controls", 100, 100);
        gameScreen.text("W: Move up", 100, 100+textShift);
        gameScreen.text("A: Move left", 100, 100+textShift*2);
        gameScreen.text("S: Move down", 100, 100+textShift*3);
        gameScreen.text("D: Move right", 100, 100+textShift*4);
        gameScreen.text("Left Mouse Button: Fire", 100, 100+textShift*5);
        gameScreen.text("Mouse Wheel: Adjusts heart beat by a limited amount.", 100, 100+textShift*6);
        
        gameScreen.text("Use W & D or Left Arrow & Right Arrow to navigate screens.  Press Esc to resume playing!", 100, 400, Flashback.X_RESOLUTION - 100, Flashback.Y_RESOLUTION - 200);

    }
	
	private void drawInstructionScreen3() {

        gameScreen.background(instructionScreenBackgroundColor);
        gameScreen.text("Tips", 100, 100);
        gameScreen.text("Your heartbeat is everything.  Too high, you die.  Too low, you die.", 100, 100+textShift);
        gameScreen.text("More enemies means higher heartbeat as long as they live.", 100, 100+textShift*2);
        gameScreen.text("Taking damage means lower heartbeat as long as you live.", 100, 100+textShift*3);
        gameScreen.text("You have some control over it, but beware: it can kill you.", 100, 100+textShift*4);
        gameScreen.text("As your heartbeat raises, so does your fire rate and move speed.", 100, 100+textShift*5);
        gameScreen.text("However, accuracy suffers significantly.", 100, 100+textShift*6);
        
        gameScreen.text("Use W & D or Left Arrow & Right Arrow to navigate screens.  Press Esc to resume playing!", 100, 400, Flashback.X_RESOLUTION - 100, Flashback.Y_RESOLUTION - 200);

    }

	public boolean isInstructionScreenActive() {
		return instructionScreenActive;
	}

	public void setInstructionScreenActive(boolean instructionScreenActive) {
		this.instructionScreenActive = instructionScreenActive;
	}

}