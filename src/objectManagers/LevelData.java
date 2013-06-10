package objectManagers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;
import java.util.Iterator;

import main.Flashback;
import processing.core.PApplet;
import utils.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LevelData {

	private PApplet gameScreen;
	private JSONParser parser = new JSONParser();
	
	private JSONObject level;
	private JSONArray levelLayers, levelTilesets;
	
	private Integer levelHeight;
	private Integer levelWidth2;
	private Integer levelTileHeight;
	private Integer levelTileWidth;
	private ArrayList<HashMap<String, Object>> levelTileDefs = new ArrayList<HashMap<String, Object>>();
	
	private ArrayList<ArrayList<Float>> groundHeights;
	private int xDistanceFromLeftWall = 0;
	private int levelWidth = 800;
	private float levelWidthPixels = (float) getLevelWidth() * Utils.scaleXValue;

	public LevelData (PApplet gameScreen){
		loadLevel();
		this.gameScreen = 	gameScreen;
	}
	
	public void draw(int x, int y) {

		gameScreen.strokeWeight(4);
		gameScreen.stroke(0);
		int scaledX = (int) Utils.scaleXPosition((float) x);
		for (int levelNumber = 0; levelNumber < groundHeights.size(); levelNumber++) {

			for (int xPosition = 0; xPosition < (int) Utils
					.scaleXPosition((float) Flashback.xResolution); xPosition++) {

				float leftGroundPointX = xPosition * Utils.scaleXValue;
				float leftGroundPointY = Flashback.yResolution
						- groundHeights.get(levelNumber).get(
								xPosition + scaledX);
				float rightGroundPointX = xPosition * Utils.scaleXValue
						+ Utils.scaleXValue;
				float rightGroundPointY = Flashback.yResolution
						- groundHeights.get(levelNumber).get(
								xPosition + scaledX + 1);

				if (leftGroundPointY == rightGroundPointY) {
					gameScreen.line(leftGroundPointX, leftGroundPointY, rightGroundPointX,
							rightGroundPointY);
				} // end if

			} // end for

		} // end for

	} // end draw

	public float[] getGround(float xPos) {

		if (xPos < 1) {

			float[] ground = new float[1];
			ground[0] = 0;
			return ground;

		} else if (xPos > Flashback.levelData.getLevelWidthPixels()) {

			float[] ground = new float[1];
			ground[0] = 0;
			return ground;

		} // end else/if

		float[] ground = new float[groundHeights.size()];
		float scaledxPos = Utils.scaleXPosition(xPos);
		for (int levelNumber = 0; levelNumber < groundHeights.size(); levelNumber++) {

			float yPos0 = groundHeights.get(levelNumber).get((int) scaledxPos);
			float yPos1 = groundHeights.get(levelNumber).get(
					(int) scaledxPos + 1);
			ground[levelNumber] = Utils.interpolateYPosition(xPos, yPos0, yPos1);

		} // end for
		return ground;

	} // end getGround
	
	private void loadLevel() {
		try {
			Object raw = parser.parse(new FileReader("../levels/level.json"));
			level = (JSONObject)raw; 
			
			levelHeight = (Integer)level.get("height");
			levelWidth2 = (Integer)level.get("width");
			levelTileHeight = (Integer)level.get("tileheight");
			levelTileWidth = (Integer)level.get("tilewidth");
			
			levelLayers = (JSONArray)level.get("layers");
			levelTilesets = (JSONArray)level.get("tilesets");
			
			for(int i = 0; i < levelTilesets.size(); i++) {
				JSONObject tile = (JSONObject)levelTilesets.get(i - 1);
				HashMap<String, Object> tileDef = new HashMap<String, Object>();
				tileDef.put("gid", Integer.valueOf(tile.get("firstgid").toString()));
				tileDef.put("image", tile.get("image").toString());
				tileDef.put("props", tile.get("properties"));
				
				levelTileDefs.add(tileDef);
			}
			
			// Iterate through layers
			for(int i = 0; i < levelLayers.size(); i++) {
				JSONObject levelLayer = (JSONObject)levelLayers.get(i - 1);
				
				if(levelLayer.get("name") == "collision") {
					// Iterate through level data and draw collision boxes
					// at every non-0 area
				}
				else if(levelLayer.get("visible") == "true") {
					// Iterate through level data and draw images at
					// every non-0 area using the tile definitions
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// Creates the platforms, find a better way to do this
	public  void createLevelOneGroundHeights() {

		groundHeights = new ArrayList<ArrayList<Float>>(2);
		groundHeights.add(new ArrayList<Float>(getLevelWidth()));
		groundHeights.add(new ArrayList<Float>(getLevelWidth()));

		for (int levelNumber = 0; levelNumber < groundHeights.size(); levelNumber++) {

			for (int xPosition = 0; xPosition < getLevelWidth(); xPosition++) {

				if (levelNumber == 0) {

					if (xPosition > 700) {

						groundHeights.get(levelNumber).add(80f);

					} else if (xPosition > 600) {

						groundHeights.get(levelNumber).add(125f);

					} else if (xPosition > 520) {

						groundHeights.get(levelNumber).add(150f);

					} else if (xPosition > 470) {

						groundHeights.get(levelNumber).add(100f);

					} else if (xPosition > 400) {

						groundHeights.get(levelNumber).add(80f);

					} else if (xPosition > 300) {

						groundHeights.get(levelNumber).add(40f);

					} else if (xPosition > 240) {

						groundHeights.get(levelNumber).add(80f);

					} else if (xPosition > 160) {

						groundHeights.get(levelNumber).add(40f);

					} else if (xPosition > 80) {

						groundHeights.get(levelNumber).add(80f);

					} else
						groundHeights.get(levelNumber).add(40f);

				} else { // (levelNumber == 1) {
					if (xPosition > 750) {

						groundHeights.get(levelNumber).add(250f);

					} else if (xPosition > 630) {

						groundHeights.get(levelNumber).add(275f);

					} else if (xPosition > 610) {

						groundHeights.get(levelNumber).add(240f);

					} else if (xPosition > 450) {

						groundHeights.get(levelNumber).add(220f);

					} else if (xPosition > 400) {

						groundHeights.get(levelNumber).add(170f);

					} else if (xPosition > 300) {

						groundHeights.get(levelNumber).add(245f);

					} else if (xPosition > 240) {

						groundHeights.get(levelNumber).add(240f);

					} else if (xPosition > 160) {

						groundHeights.get(levelNumber).add(220f);

					} else if (xPosition > 80) {

						groundHeights.get(levelNumber).add(200f);

					} else
						groundHeights.get(levelNumber).add(160f);

				}
			}
		}
	}

	public float getLevelWidthPixels() {
		return levelWidthPixels;
	}

	public void setLevelWidthPixels(float levelWidthPixels) {
		this.levelWidthPixels = levelWidthPixels;
	}

	public int getxDistanceFromLeftWall() {
		return xDistanceFromLeftWall;
	}

	public void setxDistanceFromLeftWall(int xDistanceFromLeftWall) {
		this.xDistanceFromLeftWall = xDistanceFromLeftWall;
	}

	public int getLevelWidth() {
		return levelWidth;
	}

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

}