package objectManagers;

import main.Flashback;
import utils.Utils;

public class Camera {

	public void updateCamera() {

		if (Flashback.player.getxPosition() <= Flashback.X_RESOLUTION / 2) {
			Flashback.levelData.setxDistanceFromLeftWall(0);
		} else if (Flashback.player.getxPosition() >= Flashback.levelData.getLevelWidthPixels() - Flashback.X_RESOLUTION / 2) {
			Flashback.levelData.setxDistanceFromLeftWall((int) (Flashback.levelData.getLevelWidth() * Utils.scaleXValue)
					- Flashback.X_RESOLUTION - 1);
		} else {
			Flashback.levelData.setxDistanceFromLeftWall((int) Flashback.player.getxPosition() - Flashback.X_RESOLUTION
					/ 2);
		}

	}

}
