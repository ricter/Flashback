package main;

import gameObjects.Player;
import graphics.Renderer;
import graphics.Sprite;
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

	public PImage playerImg;
	public PImage monsterImg;
	public PImage flyingImg;
	public PImage bulletImg;

	public static Sprite playerSprite;
	public static Sprite monsterSprite;
	public static Sprite flyingSprite;
	public static Sprite bulletSprite;

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
		// TODO Auto-generated method stub
		PApplet.main(new String[] { "--present", "main.Flashback" });
	}

	// Main setup method that is called on game load.
	public void setup() {

		loadMusic();

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

		music.play();

	}

	void createScreens() {

		introductionScreen = new IntroductionScreen(this, true);
		instructionScreen = new InstructionScreen(this);
		winScreen = new WinScreen(this);
		loseScreen = new LoseScreen(this);

	}

	void loadImagesAndSprites() {

		backgroundImg = loadImage("../images/bg_full_Background.png");

		playerImg = loadImage("../images/commando_jump_scale.png");
		playerSprite = new Sprite(this, playerImg);
		monsterImg = loadImage("../images/zombie_scale.png");
		monsterSprite = new Sprite(this, monsterImg);
		flyingImg = loadImage("../images/eye1-scale.png");
		flyingSprite = new Sprite(this, flyingImg, 100, 100, 0, 50);
		bulletImg = loadImage("../images/Bullet.png");
		bulletSprite = new Sprite(this, bulletImg);

	}

	void loadMusic() {

		minim = new Minim(this);
		lub = minim.loadFile("../sounds/Lub.wav");
		dub = minim.loadFile("../sounds/Dub.wav");
		hit = minim.loadFile("../sounds/Hit.wav");
		music = minim.loadFile("../sounds/Jeff_Game.wav");
		music.loop();

	}

	// Automatically called as frequently as possible.
	public void draw() {

		if (introductionScreen.isIntroductionScreenActive()) {
			introductionScreen.drawIntroductionScreen();
		} else if (winScreen.winScreenActive) {
			winScreen.drawWinScreen();
		} else if (loseScreen.loseScreenActive) {
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

	// Handles all keyboard presses
	public void keyPressed() {

		if (introductionScreen.isIntroductionScreenActive()
				|| winScreen.winScreenActive || loseScreen.loseScreenActive) { // Prevent
																				// the
																				// player
																				// from
																				// moving
																				// while
																				// screens
																				// are
																				// active

		} else {

			switch (key) {

			case ('w'):
			case ('W'):
				player.setGoUp(true);
				break;
			case ('a'):
			case ('A'):
				player.setGoLeft(true);
				break;
			case ('s'):
			case ('S'):
				player.setGoDown(true);
				break;
			case ('d'):
			case ('D'):
				player.setGoRight(true);
				break;

			case ('i'):
			case ('I'):
				instructionScreen.setInstructionScreenActive(true);
				break;

			}
		}
	}

	// Handles all keyboard releases
	public void keyReleased() {

		if (introductionScreen.isIntroductionScreenActive()) // If the
																// introduction
																// screen is up,
																// allow the
																// player to
																// press any key
																// to begin
		{
			introductionScreen.setIntroductionScreenActive(false);
		}
		if (winScreen.winScreenActive) // if the win screen is up, allow the
										// player to press spacebar to restart
										// the game
		{
			if (key == ' ') {

				clearGameObjectArrays();
				addPlayerToStart();
				winScreen.winScreenActive = false;

			}
		} else if (loseScreen.loseScreenActive) { // if the lose screen is up,
													// allow the player to press
													// spacebar to restart the
													// game
			if (key == ' ') {

				clearGameObjectArrays();
				addPlayerToStart();
				loseScreen.loseScreenActive = false;

			}
		} else // no screen active, player is actually playing the game
		{
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

	// Clears all of the game object arrays (player, npcs, bullets)
	public void clearGameObjectArrays() {

		Physics.getPlayerEntities().clear();
		Physics.gameEntities.clear();
		Physics.getPlayerBullets().clear();
		Physics.getEnemyBullets().clear();

	}

	// Adds the player to the start of the map and adds them to the physics
	// handler
	public void addPlayerToStart() {

		player = new Player(this, xResolution / 2, yResolution / 2, playerSprite);
		Physics.addPlayerEntity(player);

	}

	// Used to stop the music
	public void stop() {

		lub.close();
		dub.close();
		minim.stop();

	}

}