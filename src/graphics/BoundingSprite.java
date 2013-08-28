package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class BoundingSprite extends Sprite {

    static PImage floorImage;
    static PImage wallImage;
    
    private ArrayList<String> loadedImages = new ArrayList<String>();
    private ArrayList<PImage> loadedPImages = new ArrayList<PImage>();
    
    boolean isFloor;

    // TO DO - Fix this. Legacy code. Need tmp images so that
    // the collision bounding box is set correctly. Doesn't seem
    // to be a way to set it from here.
    public BoundingSprite(boolean isFloor, String spritePath) {
    	super(floorImage);
    	this.tryLoadImage(spritePath);
        this.isFloor = isFloor;
    }
    
    private void tryLoadImage(String path)
    {
    	if (loadedImages.contains(path)) {
    		this.currentImage = loadedPImages.get(loadedImages.indexOf(path));
    	} else {
    		PImage newFloorImage = gameScreen.loadImage(path);
    		loadedImages.add(path);
    		loadedPImages.add(newFloorImage);
    		this.currentImage = newFloorImage;
    	}
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