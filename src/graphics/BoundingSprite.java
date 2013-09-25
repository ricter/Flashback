package graphics;

import java.util.ArrayList;

import processing.core.PConstants;
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
    
    public BoundingSprite(boolean isFloor, String spritePath, int xOffset, int yOffset, int Width, int Height, int TotalWidth, int TotalHeight) {
    	super(floorImage);
    	this.tryLoadTileset(spritePath, xOffset, yOffset, Width, Height, TotalWidth, TotalHeight);
        this.isFloor = isFloor;
    }
    
    private void tryLoadTileset(String path, int xOffset, int yOffset, int Width, int Height, int TotalWidth, int TotalHeight) {
		String offsetPath  = path + Integer.toString(xOffset) + Integer.toString(yOffset);
		
    	if (loadedImages.contains(offsetPath)) {	
    		this.currentImage = loadedPImages.get(loadedImages.indexOf(offsetPath));
    	} else {
    		PImage newFloorImage = gameScreen.loadImage(path);
    		PImage tmpImage;
    		
    		int cols = TotalWidth / Width;
    		int rows = TotalHeight / Height;
    		
    		for (int row = 0; row < rows; row++) {
    			for (int col = 0; col < cols; col++) {
    				tmpImage = gameScreen.createImage(Width, Height, PConstants.ARGB);
    				tmpImage.copy(newFloorImage, col * Width, row * Height, Width, Height, 0, 0, Width, Height);
    	    		loadedImages.add(path + Integer.toString(col) + Integer.toString(row));
    	    		loadedPImages.add(tmpImage);
    			}
    		}
    		
    		this.currentImage = loadedPImages.get(loadedImages.indexOf(offsetPath));
    	}
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