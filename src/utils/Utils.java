package utils;

import processing.core.PApplet;
import processing.core.PVector;

public class Utils {

  public static float scaleXValue = 10;
  
  public static float scaleXPosition(float xPos){
    
    return xPos / scaleXValue;
    
  }
  
  public static void rotatePVector2D (PVector v, float theta){
	  
	  float xTemp = v.x;
	  v.x = v.x * PApplet.cos(theta) - v.y * PApplet.sin(theta);
	  v.y = xTemp * PApplet.sin(theta) + v.y * PApplet.cos(theta);
	  
  }
  
  public static float interpolateYPosition(float xPos, float yPos1, float yPos2){
    
    float distanceFromYPos1 = xPos % scaleXValue;
    float interpolatedYPosition = (yPos1*(scaleXValue-distanceFromYPos1) + yPos2*(distanceFromYPos1)) / (scaleXValue);
    //(yPos1*(scaleXValue-distanceFromYPos1) + yPos2*(distanceFromYPos1)) / (scaleXValue)
    //(40*(10-5) + 40*(5)) / (10)
    //(400) / (10)
    //(40)
    //(yPos1*(scaleXValue-distanceFromYPos1) + yPos2*(distanceFromYPos1)) / (scaleXValue)
    //(40*(10-6) + 40*(4)) / (10)
    //(400) / (10)
    //(40)
    //(yPos1*(scaleXValue-distanceFromYPos1) + yPos2*(distanceFromYPos1)) / (scaleXValue)
    //(80*(10-5) + 40*(5)) / (10)
    //(600) / (10)
    //(60)
    return interpolatedYPosition;
    
  }
  
}
