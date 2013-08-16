package gameObjects;

import graphics.BoundingSprite;
import graphics.Sprite;
import processing.core.PApplet;

public class BoundingObject extends GameObject{

    public BoundingObject(PApplet gameScreen, float x, float y, Sprite sprite) {

        super(gameScreen, x, y, sprite);

    }
    
    public void update(float deltaT) {

        // do nothing, bounding objects don't move
        
    }
    
    @Override
    public boolean skipXCollision(){
        return ((BoundingSprite)this.sprite).isFloor();
    }
    
    @Override
    public boolean skipYCollision(){
        return !((BoundingSprite)this.sprite).isFloor();
    }
    
}