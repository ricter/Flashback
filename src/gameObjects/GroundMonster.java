package gameObjects;

import graphics.Sprite;

import java.util.ArrayList;

import physics.Physics;
import processing.core.PApplet;

public class GroundMonster extends Monster {

	public GroundMonster(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		targetX = getxPosition();
		targetY = getyPosition();

		maxManualXAcceleration = 35;
		
		fireTimer = 0;
		flipImage = false;
		
		this.listOfCollideableObjects = new ArrayList<ArrayList<? extends GameObject>>();
		this.listOfCollideableObjects.add(Physics.getFloors());

	}

}