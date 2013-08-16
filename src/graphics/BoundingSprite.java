package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class BoundingSprite extends Sprite {

    static PImage floorImage;
    static PImage wallImage;
    
    boolean isFloor;
    
    public BoundingSprite() {
        super(wallImage);
        this.isFloor = false;
    }

    public BoundingSprite(boolean isFloor) {
        super(floorImage);
        this.isFloor = isFloor;
    }
    
    public static void loadImages(){
        floorImage = gameScreen.loadImage("../images/basic_floor.png");
        wallImage = gameScreen.loadImage("../images/basic_wall.png");
    }
    
    @Override
    public ArrayList<PImage> getMovingImages() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public PImage getStaticImage() {
        if (isFloor){
            return floorImage;
        } else return wallImage;
    }

    public boolean isFloor() {
        return isFloor;
    }
    
}