package gameObjects;

import graphics.Sprite;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class Monster extends GameObject {

    protected boolean goLeft = false;
    protected boolean goRight = false;
    protected boolean goUp = false;
    protected boolean goDown = false;

    protected float fireTimer;
    protected float minFireRate = 1;
    protected float maxFireRate = 4;
    
    protected float targetX;
    protected float targetY;

    public Monster(PApplet gameScreen, float x, float y, Sprite sprite) {

        super(gameScreen, x, y, sprite);

    }

    public void update(float deltaT) {

        // target near player generally
        if (targetX - 2 <= getxPosition() && targetX + 2 >= getxPosition()) {

            targetX = Physics.getPlayerEntities().get(0).getxPosition() + gameScreen.random(-300, 300);
            targetY = Physics.getPlayerEntities().get(0).yPosition + 290;

        }

        // move closer to target
        xAcceleration = getxPosition() < targetX ? maxManualXAcceleration : -maxManualXAcceleration;

        flipImage = getxPosition() < targetX ? true : false;

        if (fireTimer < 0) {

            fireTimer = gameScreen.random(minFireRate, maxFireRate);

            Physics.addEnemyBullet(new EnemyBullet(gameScreen,
                                                   getxPosition(),
                                                   yPosition,
                                                   Flashback.bulletSprite,
                                                   Physics.getPlayerEntities().get(0).getxPosition(),
                                                   Physics.getPlayerEntities().get(0).yPosition));

        } else fireTimer -= deltaT;
        
        computeVelocity(deltaT);
        updateMovement(deltaT);

    }

}