package objectManagers;

import gameObjects.FlyingMonster;
import gameObjects.GroundMonster;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class MonsterSpawner {

	PApplet gameScreen;
	
	int maxAirEnemies = 10;
	int curAirEnemies = 0;

	float maxAirSpawnRate = 4;
	float minAirSpawnRate = 2;
	float curAirSpawnRate;
	float airSpawnTimer = 0;

	int maxGroundEnemies = 10;
	int curGroundEnemies = 0;

	float maxGroundSpawnRate = 4;
	float minGroundSpawnRate = 2;
	float curGroundSpawnRate;
	float groundSpawnTimer = 0;

	public MonsterSpawner(PApplet gameScreen) {

		this.gameScreen= gameScreen; 
		curAirSpawnRate = gameScreen.random(minAirSpawnRate, maxAirSpawnRate);
		curGroundSpawnRate = gameScreen.random(minGroundSpawnRate, maxGroundSpawnRate);

	}

	public void update(float deltaT) {

		airSpawnTimer += deltaT;
		if (airSpawnTimer > curAirSpawnRate) {

			curAirSpawnRate = gameScreen.random(minAirSpawnRate, maxAirSpawnRate);
			airSpawnTimer = 0;
			spawnAirEnemy();

		}

		groundSpawnTimer += deltaT;
		if (groundSpawnTimer > curGroundSpawnRate) {

			curGroundSpawnRate = gameScreen.random(minGroundSpawnRate, maxGroundSpawnRate);
			groundSpawnTimer = 0;
			spawnGroundEnemy();

		}

	}

	void spawnAirEnemy() {

		// get offScreenAir position
		int bias = gameScreen.round(gameScreen.random(-1, 1));
		float spawnPointX = Flashback.levelData.xDistanceFromLeftWall
				+ ((bias > 0) ? Flashback.xResolution + 80 : -80);
		float spawnPointY = gameScreen.random(0, 400);

		Physics.addGameEntity(new FlyingMonster(gameScreen, spawnPointX, spawnPointY,
				Flashback.flyingSprite));

	}

	void spawnGroundEnemy() {

		// get offScreenAir position
		int bias = gameScreen.round(gameScreen.random(-1, 1));
		float spawnPointX = Flashback.levelData.xDistanceFromLeftWall
				+ ((bias > 0) ? Flashback.xResolution + 80 : -80);
		float spawnPointY = gameScreen.random(0, 400);
		spawnPointY = Physics.calculateNearestGroundBeneath(
				Flashback.levelData.getGround(spawnPointX), spawnPointY,
				Flashback.monsterSprite.getCollisionHeight());

		Physics.addGameEntity(new GroundMonster(gameScreen, spawnPointX, spawnPointY,
				Flashback.monsterSprite));

	}

}
