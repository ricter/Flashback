package physics;

import gameObjects.Bullet;
import gameObjects.GameObject;
import gameObjects.Player;

import java.util.ArrayList;

import main.Flashback;

public class Physics {

	private static ArrayList<Player> playerEntities = new ArrayList<Player>();
	public static ArrayList<GameObject> gameEntities = new ArrayList<GameObject>();
	private static ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	private static ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();

	public Physics() {

	}

	public static void addPlayerEntity(Player gameObj) {
		getPlayerEntities().add(gameObj);
	}

	public static void addGameEntity(GameObject gameObj) {
		gameEntities.add(gameObj);
	}

	public static void addPlayerBullet(Bullet gameObj) {
		getPlayerBullets().add(gameObj);
	}

	public static void addEnemyBullet(Bullet gameObj) {
		getEnemyBullets().add(gameObj);
	}

	public static void updateGameObjects(float deltaT) {

		for (int index = 0; index < getPlayerEntities().size(); index++) {
			getPlayerEntities().get(index).update(deltaT);
		}

		for (int index = 0; index < getPlayerBullets().size(); index++) {

			getPlayerBullets().get(index).update(deltaT);
			if (getPlayerBullets().get(index).shouldRemove()) {
				getPlayerBullets().remove(index);
			}

		}

		for (int index = 0; index < getEnemyBullets().size(); index++) {

			getEnemyBullets().get(index).update(deltaT);
			if (getEnemyBullets().get(index).shouldRemove()) {
				getEnemyBullets().remove(index);
			}

		}

		for (int index = 0; index < gameEntities.size(); index++) {

			gameEntities.get(index).update(deltaT);
			if (gameEntities.get(index).shouldRemove()) {
				gameEntities.remove(index);
			}

		}

		// check for collisions
		for (GameObject m : gameEntities) {

			for (Bullet b : getPlayerBullets()) {

				if (m.collide(b)) {

					m.animateDeath();
					b.animateDeath();

				}
			}
		}

		for (Bullet b : getEnemyBullets()) {

			if (b.collide(getPlayerEntities().get(0))) {

				b.animateDeath();
				getPlayerEntities().get(0).OnCollision();

			}
		}
	}

	public static double applyGravity(double yVelocity, float deltaT) {
		return yVelocity - (double) deltaT * 120;
	}

	public static float stopAtGround(float xPosition, float yPosition, float deltaY, float objectHeight) {

		float nearestGroundBeneath = calculateNearestGroundBeneath(Flashback.levelData.getGround(xPosition), yPosition, objectHeight);
		if (yPosition + deltaY > nearestGroundBeneath) {
			return nearestGroundBeneath;
		} else {
			return yPosition + deltaY;
		}
		
	}

	public static float calculateNearestGroundBeneath(float[] groundValues,
			float yPosition, float objectHeight) {

		float groundToFallTo = Flashback.yResolution - objectHeight;

		for (int index = 0; index < groundValues.length; index++) {

			if ((Flashback.yResolution - groundValues[index] - objectHeight) >= (yPosition)) {
				groundToFallTo = Flashback.yResolution - groundValues[index]
						- objectHeight;
			}

		}
		return groundToFallTo;
	}

	public static float stopAtWall(float xPosition, float objectWidth,
			float objectHeight) {

		if (xPosition < 0) {

			return 0f;

		} else if (xPosition > (Flashback.levelData.getLevelWidthPixels() - objectWidth)) {

			Flashback.winScreen.setWinScreenActive(true);
			return Flashback.levelData.getLevelWidthPixels() - objectHeight;

		} else
			return xPosition;

	}

	public static boolean isAtGround(float xPosition, float yPosition,
			float objectHeight) {

		boolean isAtGround = false;
		if (yPosition == (float) Flashback.yResolution - objectHeight) {

			isAtGround = true;

		} else {

			float[] groundValues = Flashback.levelData.getGround(xPosition);
			for (int index = 0; index < groundValues.length; index++) {

				if (yPosition == (float) Flashback.yResolution - groundValues[index]
						- objectHeight) {

					isAtGround = true;
					break;

				}
			}
		}
		return isAtGround;

	}

	public static ArrayList<Player> getPlayerEntities() {
		return playerEntities;
	}

	public static void setPlayerEntities(ArrayList<Player> playerEntities) {
		Physics.playerEntities = playerEntities;
	}

	public static ArrayList<Bullet> getEnemyBullets() {
		return enemyBullets;
	}

	public static void setEnemyBullets(ArrayList<Bullet> enemyBullets) {
		Physics.enemyBullets = enemyBullets;
	}

	public static ArrayList<Bullet> getPlayerBullets() {
		return playerBullets;
	}

	public static void setPlayerBullets(ArrayList<Bullet> playerBullets) {
		Physics.playerBullets = playerBullets;
	}

}