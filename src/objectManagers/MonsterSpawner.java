package objectManagers;

import gameObjects.FlyingMonster;
import gameObjects.GroundMonster;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class MonsterSpawner {

	private PApplet gameScreen;
	
	private int maxAirEnemies = 10;
	private int curAirEnemies = 0;

	private float maxAirSpawnRate = 4;
	private float minAirSpawnRate = 2;
	private float curAirSpawnRate;
	private float airSpawnTimer = 0;

	private int maxGroundEnemies = 10;
	private int curGroundEnemies = 0;

	private float maxGroundSpawnRate = 4;
	private float minGroundSpawnRate = 2;
	private float curGroundSpawnRate;
	private float groundSpawnTimer = 0;

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
			//spawnAirEnemy();

		}

		groundSpawnTimer += deltaT;
		if (groundSpawnTimer > curGroundSpawnRate) {

			curGroundSpawnRate = gameScreen.random(minGroundSpawnRate, maxGroundSpawnRate);
			groundSpawnTimer = 0;
			//spawnGroundEnemy();

		}

	}

	void spawnAirEnemy() {

		// get offScreenAir position
		int bias = gameScreen.round(gameScreen.random(-1, 1));
		float spawnPointX = Flashback.levelData.getxDistanceFromLeftWall()
				+ ((bias > 0) ? Flashback.xResolution + 80 : -80);
		float spawnPointY = gameScreen.random(0, 400);

		Physics.addGameEntity(new FlyingMonster(gameScreen, spawnPointX, spawnPointY,
				Flashback.eyeSprite));

	}

	void spawnGroundEnemy() {

		// get offScreenAir position
		int bias = gameScreen.round(gameScreen.random(-1, 1));
		float spawnPointX = Flashback.levelData.getxDistanceFromLeftWall()
				+ ((bias > 0) ? Flashback.xResolution + 80 : -80);
		float spawnPointY = gameScreen.random(0, 400);
		//TODO Re-add ability to spawn at ground
		/*spawnPointY = Physics.calculateNearestGroundBeneath(
				Flashback.levelData.getGround(spawnPointX), spawnPointY,
				Flashback.zombieSprite.getCollisionHeight());*/

		Physics.addGameEntity(new GroundMonster(gameScreen, spawnPointX, spawnPointY,
				Flashback.zombieSprite));

	}

}
