package gameObjects;

import enumerations.MonsterType;
import graphics.Sprite;

import java.util.ArrayList;

import physics.Physics;
import processing.core.PApplet;
import processing.core.PVector;

public class PlayerBullet extends Bullet {

    /** Default player bullet constructor */
    PlayerBullet(PApplet gameScreen, float x, float y, Sprite img, float targetX, float targetY, double heartBeat) {

        super(gameScreen, x, y, img);

        PVector firingVector = new PVector(targetX - x, targetY - y);
        firingVector.normalize();

        float theta = (gameScreen.random(-1, 1) + gameScreen.random(-1, 1) + gameScreen.random(-1, 1)) / 3 * (float) heartBeat * 15;

        gameScreen.constrain(theta, -MAX_ACCURACY_ANGLE, MAX_ACCURACY_ANGLE);

        firingVector.rotate(gameScreen.radians(theta));
        this.xVelocity = firingVector.x * STARTING_VELOCITY;
        this.yVelocity = firingVector.y * STARTING_VELOCITY;

        this.listOfCollideableObjects = new ArrayList<ArrayList<? extends GameObject>>();
        this.listOfCollideableObjects.add(Physics.getGameEntities());

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
        
        MonsterType monsterType = MonsterType.getMonsterTypeFromClassName(gameObject.getClass());
        Physics.getPlayerEntities().get(0).getScorecard().addMonsterKills(monsterType, 1);
        gameObject.animateDeath();
        this.animateDeath();
        
    }
    
}
