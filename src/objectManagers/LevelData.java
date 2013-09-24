package objectManagers;

import gameObjects.BoundingObject;
import graphics.BoundingSprite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import physics.Physics;
import processing.core.PApplet;
import utils.Utils;

public class LevelData {

	private PApplet gameScreen;
	private JSONParser parser = new JSONParser();
	
	private JSONObject level;
	private JSONArray levelLayers, levelTilesets;
	
	// Not sure why this have to be longs, but Java does't like it if they're not
	private Long levelHeight;
	private Long levelWidth2;
	private Long levelTileHeight;
	private Long levelTileWidth;
	private ArrayList<HashMap<String, Object>> levelTileDefs = new ArrayList<HashMap<String, Object>>();
	
	private int xDistanceFromLeftWall = 0;
	private int levelWidth = 800;
	private float levelWidthPixels = (float) getLevelWidth() * Utils.scaleXValue;
	
	private static Logger logger = Logger.getLogger(LevelData.class);

	public LevelData (PApplet gameScreen){
		loadLevel();
		this.gameScreen = 	gameScreen;
	}
	
	public void draw(int x, int y) {

		gameScreen.strokeWeight(4);
		gameScreen.stroke(0);

	} // end draw

	public void loadLevel() {
		
		try {
		    
		    if(logger.isInfoEnabled()) logger.info("Loading level one...");
			Object raw = parser.parse(new FileReader("levels/level1.json"));
			level = (JSONObject)raw; 
			
			levelTileHeight = (Long)level.get("tileheight");
			levelTileWidth = (Long)level.get("tilewidth");
			levelHeight = (Long)level.get("height");
			levelWidth2 = (Long)level.get("width");
			
			levelLayers = (JSONArray)level.get("layers");
			levelTilesets = (JSONArray)level.get("tilesets");
			
			for(int i = 1; i <= levelTilesets.size(); i++) {
				JSONObject tile = (JSONObject)levelTilesets.get(i - 1);
				HashMap<String, Object> tileDef = new HashMap<String, Object>();
				tileDef.put("gid", Integer.valueOf(tile.get("firstgid").toString()));
				tileDef.put("image", tile.get("image").toString());
				tileDef.put("props", tile.get("tileproperties"));
				
				levelTileDefs.add(tileDef);
			}
			
			// Iterate through layers
			for(int i = 1; i <= levelLayers.size(); i++) {
				JSONObject levelLayer = (JSONObject)levelLayers.get(i - 1);
				long tileXPos = 0;
				long tileYPos = 0;
				
				if((Boolean) levelLayer.get("visible")) {
					ArrayList<Long> tmpLevelData = (ArrayList<Long>) levelLayer.get("data");
					
					for(int j = 0; j < levelHeight; j++) {
						for(int k = 0; k < levelWidth2; k++) {
							if(tmpLevelData.get((int)((j*levelWidth2)+k)) == 0) continue;
							else {
								tileXPos = levelTileWidth * k;
								tileYPos = levelTileHeight * j;
								Long tileIdx = tmpLevelData.get((int)((j*levelWidth2)+k));
								JSONObject tmp = (JSONObject)levelTileDefs.get(tileIdx.intValue()-1).get("props");
								String imagePath = (String)levelTileDefs.get(tileIdx.intValue()-1).get("image");
								
								Iterator it = tmp.entrySet().iterator();
								while (it.hasNext()){
									Map.Entry pairs = (Map.Entry)it.next();
									JSONObject eachProp = (JSONObject)pairs.getValue();
									
									if ((boolean)eachProp.get("floor").equals("true")) {
										BoundingSprite floorSprite = new BoundingSprite(true, imagePath);
										BoundingObject floor = new BoundingObject(gameScreen, (int)tileXPos, (int)tileYPos, floorSprite);
								        Physics.addFloorEntity(floor);
									} else if ((boolean)eachProp.get("wall").equals("true")) {
										BoundingSprite floorSprite = new BoundingSprite(false, imagePath);
										BoundingObject floor = new BoundingObject(gameScreen, (int)tileXPos, (int)tileYPos, floorSprite);
								        Physics.addFloorEntity(floor);
									}
								}
							} // end if
						} // end for
					} // end for
				} // end if
			} // end for
			
			if(logger.isInfoEnabled()) logger.info("Loaded level one.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} // end try
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