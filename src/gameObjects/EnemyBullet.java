package gameObjects;

import graphics.Sprite;

import java.util.ArrayList;

import physics.Physics;
import processing.core.PApplet;
import processing.core.PVector;

public class EnemyBullet extends Bullet {

    /** Default monster bullet constructor */
    EnemyBullet(PApplet gameScreen, float x, float y, Sprite img, float targetX, float targetY) {

        super(gameScreen, x, y, img);

        PVector firingVector = new PVector(targetX - x, targetY - y);
        firingVector.normalize();

        // TODO add random angle

        this.xVelocity = firingVector.x * STARTING_VELOCITY;
        this.yVelocity = firingVector.y * STARTING_VELOCITY;

        this.listOfCollideableObjects = new ArrayList<ArrayList<? extends GameObject>>();
        this.listOfCollideableObjects.add(Physics.getPlayerEntities());

    }

    @Override
    public void triggerXCollision(float oldX, GameObject gameObject) {
        
        resolveCollision(oldX, gameObject);
        
    }
    
    @Override
    public void triggerYCollision(float oldY, GameObject gameObject) {

        resolveCollision(oldY, gameObject);
        
    }

    private void resolveCollision(float oldY, GameObject gameObject){
        
        this.animateDeath();
        Physics.getPlayerEntities().get(0).OnCollision();
        
    }
    
}