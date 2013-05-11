package objectManagers;

import main.Flashback;
import utils.Utils;

public class Camera {

	public void updateCamera() {

		if (Flashback.player.xPos <= Flashback.xResolution / 2) {
			// System.out.println("Camera hugging left wall");
			Flashback.levelData.xDistanceFromLeftWall = 0;
		} else if (Flashback.player.xPos >= Flashback.levelData.levelWidthPixels - Flashback.xResolution / 2) {
			// System.out.println("Camera hugging right wall");
			Flashback.levelData.xDistanceFromLeftWall = (int) (Flashback.levelData.levelWidth * Utils.scaleXValue)
					- Flashback.xResolution - 1;
		} else {
			Flashback.levelData.xDistanceFromLeftWall = (int) Flashback.player.xPos - Flashback.xResolution
					/ 2;
			// System.out.println("Camera moving off wall: " +
			// levelData.xDistanceFromLeftWall);
		}

	}

}
