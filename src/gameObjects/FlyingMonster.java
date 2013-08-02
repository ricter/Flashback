package gameObjects;

import graphics.Sprite;

import java.util.ArrayList;

import processing.core.PApplet;

public class FlyingMonster extends Monster {
    
	public FlyingMonster(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		targetX = getxPosition();
		targetY = getyPosition();
		
		isAffectedByGravity = false;
		maxManualXAcceleration = 35;

		fireTimer = 0;
		flipImage = false;

		this.listOfCollideableObjects = new ArrayList<ArrayList<? extends GameObject>>();
		
	}

}