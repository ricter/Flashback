package objectManagers;

import main.Flashback;
import utils.Utils;

public class Camera {

	public void updateCamera() {

		if (Flashback.player.getxPos() <= Flashback.xResolution / 2) {
			// System.out.println("Camera hugging left wall");
			Flashback.levelData.setxDistanceFromLeftWall(0);
		} else if (Flashback.player.getxPos() >= Flashback.levelData.getLevelWidthPixels() - Flashback.xResolution / 2) {
			// System.out.println("Camera hugging right wall");
			Flashback.levelData.setxDistanceFromLeftWall((int) (Flashback.levelData.getLevelWidth() * Utils.scaleXValue)
					- Flashback.xResolution - 1);
		} else {
			Flashback.levelData.setxDistanceFromLeftWall((int) Flashback.player.getxPos() - Flashback.xResolution
					/ 2);
			// System.out.println("Camera moving off wall: " +
			// levelData.xDistanceFromLeftWall);
		}

	}

}
