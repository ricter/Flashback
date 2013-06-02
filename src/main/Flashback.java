package main;

import gameObjects.Player;
import graphics.BulletSprite;
import graphics.EyeSprite;
import graphics.PlayerSprite;
import graphics.Renderer;
import graphics.Sprite;
import graphics.ZombieSprite;
import objectManagers.Camera;
import objectManagers.LevelData;
import objectManagers.MonsterSpawner;
import physics.Physics;
import processing.core.PApplet;
import processing.core.PImage;
import screens.InstructionScreen;
import screens.IntroductionScreen;
import screens.LoseScreen;
import screens.SplashScreen;
import screens.WinScreen;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Flashback extends PApplet {

	public final static int xResolution = 1280;
	public final static int yResolution = 720;
	public final static float ground = 40;
	public final static int baseFrameRate = 60;

	public static Player player;
	public static LevelData levelData;
	public static Camera camera;

	public static IntroductionScreen introductionScreen;
	public static InstructionScreen instructionScreen;
	public static LoseScreen loseScreen;
	public static SplashScreen splashScreen;
	public static WinScreen winScreen;

	static int backgroundColor = 255;
	public static PImage backgroundImg;

	public static PlayerSprite playerSprite;
	public static ZombieSprite zombieSprite;
	public static EyeSprite eyeSprite;
	public static BulletSprite bulletSprite;

	public Minim minim;
	public AudioPlayer lub;
	public AudioPlayer dub;
	public static AudioPlayer hit;
	public AudioPlayer music;

	MonsterSpawner monsterSpawner;
	Renderer renderer;

	/**
	 * Main driver to kick off the program.
	 * @param args
	 */
	public static void main(String[] args) {
		
		PApplet.main(new String[] { "--present", "main.Flashback" });
		
	}

	/**
	 *  Main setup method that is called on game load.
	 */
	public void setup() {

		//loadMusic();

		size(1280, 720, P2D);
		frameRate(baseFrameRate);
		background(backgroundColor);
		smooth();

		splashScreen = new SplashScreen(this);
		splashScreen.drawSplashScreen();

		loadImagesAndSprites();

		createScreens();

		monsterSpawner = new MonsterSpawner(this);

		player = new Player(this, xResolution / 2, yResolution / 2, playerSprite);
		Physics.addPlayerEntity(player);

		levelData = new LevelData(this);
		levelData.createLevelOneGroundHeights();

		camera = new Camera();
		renderer = new Renderer(this);

		textSize(32);

		randomSeed(234234324);

		//music.play();

	}

	/**
	 * 
	 */
	void createScreens() {

		introductionScreen = new IntroductionScreen(this, true);
		instructionScreen = new InstructionScreen(this);
		winScreen = new WinScreen(this);
		loseScreen = new LoseScreen(this);

	}

	/**
	 * 
	 */
	void loadImagesAndSprites() {

		backgroundImg = loadImage("../images/bg_full_Background.png");
		
		Sprite.setGameScreen(this);
		
		PlayerSprite.loadImages();
		ZombieSprite.loadImages();
		EyeSprite.loadImages();
		BulletSprite.loadImages();

		playerSprite = new PlayerSprite();
		zombieSprite = new ZombieSprite();
		eyeSprite = new EyeSprite(100, 100, 0, 50);
		bulletSprite = new BulletSprite();

	}

	/**
	 * 
	 */
	void loadMusic() {

		minim = new Minim(this);
		lub = minim.loadFile("../sounds/Lub.wav");
		dub = minim.loadFile("../sounds/Dub.wav");
		hit = minim.loadFile("../sounds/Hit.wav");
		music = minim.loadFile("../sounds/Jeff_Game.wav");
		music.loop();

	}

	/**
	 *  Automatically called as frequently as possible.
	 */
	public void draw() {

		if (introductionScreen.isIntroductionScreenActive()) {
			introductionScreen.drawIntroductionScreen();
		} else if (winScreen.isWinScreenActive()) {
			winScreen.drawWinScreen();
		} else if (loseScreen.isLoseScreenActive()) {
			loseScreen.drawLoseScreen();
		} else if (instructionScreen.isInstructionScreenActive()) {
			instructionScreen.drawInstructionScreen();
		} else {

			// background(backgroundColor);

			monsterSpawner.update(1 / (float) baseFrameRate);

			Physics.updateGameObjects(1 / (float) baseFrameRate);
			renderer.drawGameObjects();

		}
	}

	public void mousePressed() {
		
		if (introductionScreen.isIntroductionScreenActive()
				|| winScreen.isWinScreenActive() || loseScreen.isLoseScreenActive() ) { // Prevent the player from firing while screens are active

		} else { // try to fire
			
			player.tryToFire();
			
		}
		
	}
	
	/**
	 *  Handles all keyboard presses
	 */
	public void keyPressed() {

		if (introductionScreen.isIntroductionScreenActive()
				|| winScreen.isWinScreenActive() || loseScreen.isLoseScreenActive() ) { // Prevent the player from moving while screens are active

		} else {

			switch (key) {

			case ('w'):
			case ('W'):
				player.setGoUp(true);
				playerSprite.setJumping(true);
				break;
			case ('a'):
			case ('A'):
				player.setGoLeft(true);
				playerSprite.setRunning(true);
				break;
			case ('s'):
			case ('S'):
				player.setGoDown(true);
				break;
			case ('d'):
			case ('D'):
				player.setGoRight(true);
				playerSprite.setJumping(true);
				playerSprite.setRunning(true);
				break;

			case ('i'):
			case ('I'):
				instructionScreen.setInstructionScreenActive(true);
				break;

			}
		}
	}

	/**
	 *  Handles all keyboard releases
	 */
	public void keyReleased() {

		if (introductionScreen.isIntroductionScreenActive()){ // If the introduction screen is up, allow the player to press any key to begin
		
			introductionScreen.setIntroductionScreenActive(false);
			
		}
		if (winScreen.isWinScreenActive()) { // if the win screen is up, allow the player to press spacebar to restart the game
		
			if (key == ' ') {

				clearGameObjectArrays();
				addPlayerToStart();
				winScreen.setWinScreenActive(false);

			}
			
		} else if (loseScreen.isLoseScreenActive()) { // if the lose screen is up, allow the player to press spacebar to restart the game
			
			if (key == ' ') {

				clearGameObjectArrays();
				addPlayerToStart();
				loseScreen.setLoseScreenActive(false);

			}
			
		} else { // no screen active, player is actually playing the game
		
			switch (key) {

			case ('w'):
			case ('W'):
				player.setGoUp(false);
				break;
			case ('a'):
			case ('A'):
				player.setGoLeft(false);
				break;
			case ('s'):
			case ('S'):
				player.setGoDown(false);
				break;
			case ('d'):
			case ('D'):
				player.setGoRight(false);
				break;

			case ('i'):
			case ('I'):
				instructionScreen.setInstructionScreenActive(false);
				break;

			}
		}
	}

	/**
	 *  Clears all of the game object arrays (player, npcs, bullets)
	 */
	public void clearGameObjectArrays() {

		Physics.getPlayerEntities().clear();
		Physics.gameEntities.clear();
		Physics.getPlayerBullets().clear();
		Physics.getEnemyBullets().clear();

	}

	/**
	 *  Adds the player to the start of the map and adds them to the physics handler
	 */
	public void addPlayerToStart() {

		player = new Player(this, xResolution / 2, yResolution / 2, playerSprite);
		Physics.addPlayerEntity(player);

	}

	/**
	 *  Used to stop the music
	 */
	public void stop() {

		lub.close();
		dub.close();
		minim.stop();

	}

}