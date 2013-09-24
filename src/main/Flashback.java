package main;

import gameObjects.Player;
import graphics.BoundingSprite;
import graphics.BulletSprite;
import graphics.EyeSprite;
import graphics.PlayerArmSprite;
import graphics.PlayerSprite;
import graphics.Renderer;
import graphics.Sprite;
import graphics.ZombieSprite;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import objectManagers.Camera;
import objectManagers.LevelData;
import objectManagers.MonsterSpawner;

import org.apache.log4j.Logger;

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

    public final static int LEVEL_X_WIDTH = 8000;
	public final static int X_RESOLUTION = 1280;
	public final static int Y_RESOLUTION = 720;
	public final static float GROUND = 40;
	public final static int BASE_FRAME_RATE = 60;

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
	public static PlayerArmSprite playerArmSprite;
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
	
	private static double instructionTextAlpha = 100;
	private static boolean loaded = false;
	
	private static Logger logger = Logger.getLogger(Flashback.class);

	/**
	 * Main driver to kick off the program.
	 * @param args
	 */
	public static void main(String[] args) {
		
	    if(logger.isInfoEnabled()) logger.info("Starting Flashback.");
	    PApplet.main(new String[] { "--present", "main.Flashback" });
        
	}

	/**
	 *  Adds the player to the start of the map and adds them to the physics handler
	 */
	public void addPlayerToStart() {

	    if(logger.isInfoEnabled()) logger.info("Adding player to start.");
		player = new Player(this, X_RESOLUTION / 2, Y_RESOLUTION / 2, playerSprite, playerArmSprite);
		Physics.addPlayerEntity(player);

	}
	
	/**
	 *  Clears all of the game object arrays (player, npcs, bullets)
	 */
	public void clearGameObjectArrays() {

		Physics.getPlayerEntities().clear();
		Physics.getGameEntities().clear();
		Physics.getPlayerBullets().clear();
		Physics.getEnemyBullets().clear();
		Physics.getWalls().clear();

	}

	/**
	 *  Automatically called as frequently as possible.
	 */
	public void draw() {

	    if (!loaded){
	        splashScreen.drawSplashScreen();
	    } else if (introductionScreen.isIntroductionScreenActive()) {
			introductionScreen.drawIntroductionScreen();
		} else if (winScreen.isWinScreenActive()) {
			winScreen.drawWinScreen();
		} else if (loseScreen.isLoseScreenActive()) {
			loseScreen.drawLoseScreen();
		} else if (instructionScreen.isInstructionScreenActive()) {
			instructionScreen.drawInstructionScreen();
		} else {

			monsterSpawner.update(1 / (float) BASE_FRAME_RATE);
			Physics.updateGameObjects(1 / (float) BASE_FRAME_RATE);

			renderer.drawGameObjects();
			
			if (instructionTextAlpha > 0){
			    fill(255, 255, 255, (float)instructionTextAlpha);
	            instructionTextAlpha = instructionTextAlpha - .25;
	            text("i: instructions", X_RESOLUTION - 250, 40);
			}

		}
	}
	
	public static Player getPlayer(){
		
		return player;
		
	}

	/**
	 *  Handles all keyboard presses
	 */
	public void keyPressed() {

		if (introductionScreen.isIntroductionScreenActive()
				|| winScreen.isWinScreenActive() || loseScreen.isLoseScreenActive() ) { // Prevent the player from moving while screens are active

		    // do nothing
		    
		} if (instructionScreen.isInstructionScreenActive()) {
		    
		    if (key == CODED){
                
                if (keyCode == LEFT) {
                    
                    instructionScreen.changeScreenNumber(false);
                    
                } else if (keyCode == RIGHT) {
                    
                    instructionScreen.changeScreenNumber(true);
                    
                }
                
            } else {
                
                switch (key) {

                case ('a'):
                case ('A'):
                    instructionScreen.changeScreenNumber(false);
                    break;
                case ('d'):
                case ('D'):
                    instructionScreen.changeScreenNumber(true);
                    break;
                case (ESC):
                    instructionScreen.setInstructionScreenActive(false);
                    key = 0; 
                    break;
                }
            
                
            }
		    
		} else { // player is playing the game

		    if (key == CODED){
                
                if (keyCode == UP) {
                    
                    player.setGoUp(true);
                    playerSprite.setJumping(true);
                    
                } else if (keyCode == LEFT) {
                    
                    player.setGoLeft(true);
                    playerSprite.setRunning(true);
                    
                } else if (keyCode == DOWN) {
                    
                    player.setGoDown(true);
                    
                } else if (keyCode == RIGHT) {
                    
                    player.setGoRight(true);
                    playerSprite.setJumping(true);
                    playerSprite.setRunning(true);
                    
                }
                
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
                    playerSprite.setRunning(true);
                    break;

                case ('i'):
                case ('I'):
                    instructionScreen.setInstructionScreenActive(true);
                    break;

                }
                
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
				levelData.loadLevel();
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

			}
		}
	}

	public void mousePressed() {
		
		if (introductionScreen.isIntroductionScreenActive()
				|| winScreen.isWinScreenActive() || loseScreen.isLoseScreenActive() ) { // Prevent the player from firing while screens are active

		} else { // try to fire
			
			player.setTryToFire(true);
			
		}
		
	}
	
	public void mouseReleased() {
	    
	    player.setTryToFire(false);
	    
	}
	
	public void mouseWheel(int delta) {
		
		player.setFireRateAdjustment(player.getFireRateAdjustment() + ((double)delta * -1) / 5);
		
	}
	
	/**
	 *  Main setup method that is called on game load.
	 */
	public void setup() {

		loadMusic();

		size(1280, 720, P2D);
		frameRate(BASE_FRAME_RATE);
		background(backgroundColor);
		smooth();

		splashScreen = new SplashScreen(this);
		splashScreen.drawSplashScreen();

		loadImagesAndSprites();

		createScreens();

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				mouseWheel(mwe.getWheelRotation());
			}
		}); 
		
		monsterSpawner = new MonsterSpawner(this);

		addPlayerToStart();

		levelData = new LevelData(this);

		camera = new Camera();
		renderer = new Renderer(this);

		textSize(32);

		randomSeed(234234324);

		music.play();
		loaded = true;

	}

	/**
	 *  Used to stop the music
	 */
	public void stop() {

		lub.close();
		dub.close();
		minim.stop();

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
		PlayerArmSprite.loadImages();
		ZombieSprite.loadImages();
		EyeSprite.loadImages();
		BulletSprite.loadImages();
		BoundingSprite.loadImages();

		playerSprite = new PlayerSprite();
		playerArmSprite = new PlayerArmSprite();
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

}