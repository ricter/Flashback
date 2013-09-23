package gameObjects;

import graphics.Sprite;

import java.util.ArrayList;

import physics.Physics;
import processing.core.PApplet;

public class GameObject {

	protected boolean flipImage;
	
	protected PApplet gameScreen;
	
	protected boolean isAffectedByGravity = true;
	protected ArrayList<ArrayList<? extends GameObject>> listOfCollideableObjects;
	protected float maxManualXAcceleration;
    protected float maxXVelocity = 2000;
    protected float maxYVelocity = 2000;
	protected float oldXPosition;
	protected float oldYPosition;
	protected boolean removeMe = false;
	protected Sprite sprite;
	
	protected float xAcceleration;
	protected float xPosition;
	protected float xVelocity;
	protected float yAcceleration;
	protected float yPosition;
	protected float yVelocity;
	
	public GameObject(PApplet gameScreen) {

		this.gameScreen = gameScreen;
		setXPosition(0);
		yPosition = 0;
		this.sprite = null;

	}

    public GameObject(PApplet gameScreen, float x, float y, Sprite sprite) {

		this.gameScreen = gameScreen;
		setXPosition(x);
		setYPosition(y);
		this.sprite = sprite;

	}

    public void animateDeath() {

		removeMe = true;

	}

    public void computeVelocity(float deltaT){
        
        if ( (xAcceleration < 0 && xVelocity > 0) || (xAcceleration > 0 && xVelocity < 0) ){ // check if direction of movement is different than desired direction of movement
            xVelocity = 0;  // allow instant turns
        } else xVelocity += xAcceleration * (deltaT);
        
        
        if (isAffectedByGravity){
            yAcceleration += Physics.GRAVITY_ACCELERATION;  
        }
        yVelocity += yAcceleration * (deltaT);
        
        
        // Bound velocity
        xVelocity = (xVelocity > maxXVelocity) ? maxXVelocity : xVelocity;
        xVelocity = (xVelocity < -maxXVelocity) ? -maxXVelocity : xVelocity;
        yVelocity = (yVelocity > maxYVelocity) ? maxYVelocity : yVelocity;
        yVelocity = (yVelocity < -maxYVelocity) ? -maxYVelocity : yVelocity;
        
	}

    public void draw(int x, int y) {

		if (sprite != null) {
			sprite.draw(getxPosition() + x, yPosition + y, flipImage);
		}

	}

    public float getOldXPosition() {
        return oldXPosition;
    }

    public float getOldYPosition() {
        return oldYPosition;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getxAcceleration() {
        return xAcceleration;
    }

    public float getxPosition() {
        return xPosition;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public float getyAcceleration() {
        return yAcceleration;
    }
	
    public float getyPosition() {
        return yPosition;
    }

	public float getyVelocity() {
        return yVelocity;
    }

	public boolean isFlipImage() {
        return flipImage;
    }

	public void setxAcceleration(float xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

	public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

	public void setXPosition(float xPos) {
		this.xPosition = xPos;
	}

	public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

	public void setyAcceleration(float yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

	public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

	public void setYPosition(float yPos) {
        this.yPosition = yPos;
    }
	
	public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public boolean shouldRemove() {
		return removeMe;
	}

	public boolean skipXCollision() {
        // TODO Auto-generated method stub
        return false;
    }
	
	public boolean skipYCollision() {
        // TODO Auto-generated method stub
        return false;
    }
	
	public void triggerXCollision(float oldX, GameObject gameObject){
	    
	    xVelocity = 0;
        xAcceleration = 0;
        xPosition = oldX;
	    
	}
	
	public void triggerYCollision(float oldY, GameObject gameObject){
        
	    yVelocity = 0;
        yAcceleration = 0;
        yPosition = oldY;
        
    }

    /** Should never be called directly, should be overloaded by any children */
	public void update(float deltaT) {
	    
	}

    public void updateMovement(float deltaT){
	    
	    // try to move
	    oldXPosition = xPosition;
	    oldYPosition = yPosition;
	    xPosition += xVelocity * deltaT;
        yPosition += yVelocity * deltaT;
	    
	    for (ArrayList<? extends GameObject> list : listOfCollideableObjects){
	        
	        for (GameObject gameObject : list){
	            
	            switch (Physics.checkCollision(this, gameObject, flipImage, gameObject.flipImage)){
	                
	                case XANDY:
	                    
	                    triggerXCollision(oldXPosition, gameObject);
	                    triggerYCollision(oldYPosition, gameObject);
	                    break;
	                
	                case X:
	                    
	                    triggerXCollision(oldXPosition, gameObject);
	                    break;
	                    
	                case Y:
	                    
	                    triggerYCollision(oldYPosition, gameObject);
	                    break;
	                
	                    // all other cases do nothing
	            }
	            
	        }
	        
	    }
	    
	}
	
}