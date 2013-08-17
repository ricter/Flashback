package graphics;

import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class Renderer {

	PApplet gameScreen;
	
	public Renderer (PApplet gameScreen){
		this.gameScreen = gameScreen;
	}
	
	/**
	 * draw order - background image, level data, enemies, player, bullets
	 */
	public void drawGameObjects() {

		Flashback.camera.updateCamera();
		gameScreen.image(Flashback.backgroundImg, -Flashback.levelData.getxDistanceFromLeftWall(), 0);
		Flashback.levelData.draw(Flashback.levelData.getxDistanceFromLeftWall(), 0);

		for (int index = 0; index < Physics.getWalls().size(); index++) {
            Physics.getWalls().get(index).draw(
                    -Flashback.levelData.getxDistanceFromLeftWall(), 0);
        }
		
		for (int index = 0; index < Physics.getFloors().size(); index++) {
            Physics.getFloors().get(index).draw(
                    -Flashback.levelData.getxDistanceFromLeftWall(), 0);
        }
		
		for (int index = 0; index < Physics.getGameEntities().size(); index++) {
			Physics.getGameEntities().get(index).draw(
					-Flashback.levelData.getxDistanceFromLeftWall(), 0);
		}

		for (int index = 0; index < Physics.getPlayerEntities().size(); index++) {
			Physics.getPlayerEntities().get(index).draw(
					-Flashback.levelData.getxDistanceFromLeftWall(), 0);
		}

		for (int index = 0; index < Physics.getEnemyBullets().size(); index++) {
			Physics.getEnemyBullets().get(index).draw(
					-Flashback.levelData.getxDistanceFromLeftWall(), 0);
		}

		for (int index = 0; index < Physics.getPlayerBullets().size(); index++) {
			Physics.getPlayerBullets().get(index).draw(
					-Flashback.levelData.getxDistanceFromLeftWall(), 0);
		}

	}

}
