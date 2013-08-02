package gameObjects;

import graphics.Sprite;

import java.util.ArrayList;

import physics.Physics;
import processing.core.PApplet;

public class GameObject {

	protected PApplet gameScreen;
	
	protected ArrayList<ArrayList<? extends GameObject>> listOfCollideableObjects;
	
	protected float xPosition;
	protected float xVelocity;
	protected float xAcceleration;
	protected float yPosition;
	protected float yVelocity;
	protected float yAcceleration;
	
	protected float maxManualXAcceleration;
	protected float maxYVelocity = 2000;
	protected float maxXVelocity = 2000;
	protected boolean isAffectedByGravity = true;
	
	protected Sprite sprite;
	protected boolean flipImage;

    protected boolean removeMe = false;

    public GameObject(PApplet gameScreen) {

		this.gameScreen = gameScreen;
		setXPosition(0);
		yPosition = 0;
		this.sprite = null;

	}

    public GameObject(PApplet gameScreen, float x, float y, Sprite sprite) {

		this.gameScreen = gameScreen;
		setXPosition(x);
		yPosition = y;
		this.sprite = sprite;

	}

    public void animateDeath() {

		removeMe = true;

	}

    public void computeVelocity(float deltaT){
        
        xVelocity += xAcceleration * (deltaT);
        if (isAffectedByGravity){
            yAcceleration += Physics.GRAVITY_ACCELERATION;  
        }
        yVelocity += yAcceleration * (deltaT);
        
        
        // Bound velocity
        xVelocity = (xVelocity > maxXVelocity) ? maxXVelocity : xVelocity;
        xVelocity = (xVelocity < -maxXVelocity) ? -maxXVelocity : xVelocity;
        yVelocity = (yVelocity > maxYVelocity) ? maxYVelocity : yVelocity;
        yVelocity = (yVelocity < -maxYVelocity) ? -maxYVelocity : yVelocity;
        
        //System.out.println("xAcceleration: " + xAcceleration + " yAcceleration: " + yAcceleration);
        //System.out.println("xVelocity: " + xVelocity + " yVelocity " + yVelocity);
        
	}

    public void draw(int x, int y) {

		if (sprite != null) {
			sprite.draw(getxPosition() + x, yPosition + y, flipImage);
		}

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

	/** Should never be called directly, should be overloaded by any children */
	public void update(float deltaT) {
	    
	}
	
	public void updateMovement(float deltaT){
	    
	    // try to move
	    float oldX = xPosition;
	    float oldY = yPosition;
	    xPosition += xVelocity * deltaT;
        yPosition += yVelocity * deltaT;
	    
	    for (ArrayList<? extends GameObject> list : listOfCollideableObjects){
	        
	        for (GameObject gameObject : list){
	            
	            switch (Physics.checkCollision(this, gameObject, flipImage, gameObject.flipImage)){
	                
	                case XANDY:
	                    
	                    triggerXCollision(oldX);
	                    triggerYCollision(oldY);
	                    break;
	                
	                case X:
	                    
	                    triggerXCollision(oldX);
	                    break;
	                    
	                case Y:
	                    
	                    triggerYCollision(oldY);
	                    break;
	                
	                    // all other cases do nothing
	            }
	            
	        }
	        
	    }
	    
	}
	
	public void triggerXCollision(float oldX){
	    
	    xVelocity = 0;
        xAcceleration = 0;
        xPosition = oldX;
	    
	}
	
	public void triggerYCollision(float oldY){
        
	    yVelocity = 0;
        yAcceleration = 0;
        yPosition = oldY;
        
    }

    public boolean skipXCollision() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean skipYCollision() {
        // TODO Auto-generated method stub
        return false;
    }
	
}