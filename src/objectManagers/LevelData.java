package objectManagers;

import gameObjects.BoundingObject;
import graphics.BoundingSprite;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import main.Flashback;

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
	private int levelHeight;
	private int levelWidth;
	private int tileHeight;
	private int tileWidth;
	
	private ArrayList<Integer> gids = new ArrayList<Integer>();
	private HashMap<Integer, HashMap<String, Object>> levelTileDefs = new HashMap<Integer, HashMap<String, Object>>();
	private ArrayList<Tile> tileSet = new ArrayList<Tile>();
	
	private int xDistanceFromLeftWall = 0;
	private float levelWidthPixels = (float) getLevelWidth() * Utils.scaleXValue;

	public LevelData (PApplet gameScreen){
		loadLevel();
		this.gameScreen = gameScreen;
	}
	
	public void draw(int x, int y) {

		gameScreen.strokeWeight(4);
		gameScreen.stroke(0);

	} // end draw
	
	public void loadLevel() {
		// Get the Level JSON file and cast it to a JSONObject
		int level = 1;
		
		this.ParseLevelJSON(level);
		this.ParseTilesets();
		this.createTileset();
		this.loadTileset();
	}
	
	private void ParseLevelJSON(int levelNumber) {
		String path = "";
		
		switch (levelNumber) {
			case 1:
				path = "levels/leveltest.json";
				break;
			default:
				break;
		}
		
		Object raw = null;

		try {
			raw = parser.parse(new FileReader(path));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		level = (JSONObject)raw; 
		
		// Extract high-level level data from JSON
		tileHeight = ((Long)level.get("tileheight")).intValue();  // Height of individual tiles
		tileWidth = ((Long)level.get("tilewidth")).intValue();    // Width of individual tiles
		levelHeight = ((Long)level.get("height")).intValue();      // Height of entire map
		levelWidth = ((Long)level.get("width")).intValue();           // Width of entire map
		levelLayers = (JSONArray)level.get("layers");     // Get JSON Array of "layers" of map
		levelTilesets = (JSONArray)level.get("tilesets"); // Get JSON Array of the tileset information
	}
	
	private void ParseTilesets() {
		// Iterate through each of the tilesets and get relevant information from them
		for(int i = 0; i < levelTilesets.size(); i++) {
			JSONObject tile = (JSONObject)levelTilesets.get(i); // Get the i-1 tile
			HashMap<String, Object> tileDef = new HashMap<String, Object>();  // Create a HashMap to put the data into
			
			// Logic to store the GIDs (for looking up tile info)
			int gid = ((Long)tile.get("firstgid")).intValue();
			gids.add(gid);

			HashMap<Integer, String> props = this.ParseTileProperties((JSONObject)tile.get("tileproperties"));
			
			// Add the relevant data from each tileset into the hashmap
			tileDef.put("image", tile.get("image").toString());
			tileDef.put("props", props);
			tileDef.put("tilesetWidth", ((Long)tile.get("imagewidth")).intValue());
			tileDef.put("tilesetHeight", ((Long)tile.get("imageheight")).intValue());
			tileDef.put("tilewidth", ((Long)tile.get("tilewidth")).intValue());
			tileDef.put("tileheight", ((Long)tile.get("tileheight")).intValue());
			
			// And add that tileset's information to list of tileset definitions
			levelTileDefs.put(gid, tileDef);
		}
	}
	
	private void createTileset() {
		// Iterate through layers
		for(int i = 0; i < levelLayers.size(); i++) {
			JSONObject levelLayer = (JSONObject) levelLayers.get(i);

			// For now, ignore background image layers
			if(((String)levelLayer.get("type")).equals(new String("imagelayer"))) {
				// TODO - Debug this Null Pointer Exception
				String path = (String)levelLayer.get("image");
				//Flashback.backgroundImg = gameScreen.loadImage(path);
				continue;
			}

			// Get the layer information
			ArrayList<Long> tilesetData = (ArrayList<Long>)levelLayer.get("data");
			
			// Iterate through the rows / columns of the level
			for(int row = 0; row < levelHeight; row++) {
				for(int col = 0; col < levelWidth; col++) {
					Tile newTile = new Tile();
					int accessor = (row * levelWidth) + col;

					int tileGid = tilesetData.get(accessor).intValue();

					if(tileGid == 0) continue; // If there is no data at the element, pass
					else {
						// Get the 2D position of the tile
						newTile.xPos = tileWidth * col;
						newTile.yPos = tileHeight * row;
						
						// Loop to determine which "firstgid" tileset this tile falls under
						int previousGid = 0;
						int newGid = -1;
						
						for (Integer gid : gids) {
							if(tileGid > gid) {
								previousGid = gid;
							}
							else if (tileGid == gid) {
								newGid = gid;
								break;
							}
							else {
								newGid = previousGid;
								break;
							}
						}
						
						if (newGid == -1) newGid = previousGid;
						newTile.gid = tileGid - newGid; // rebase the tileGid off the new index

						// .. and use it to access the the tile definition of that tile in the TileDefs JSON Object
						// .. and get the relevant data
						HashMap<String, Object> tileDef = levelTileDefs.get(newGid);
						newTile.properties = (HashMap<Integer, String>) tileDef.get("props");
						newTile.imagePath = (String) tileDef.get("image");
						newTile.tilesetHeight = (int) tileDef.get("tilesetHeight");
						newTile.tilesetWidth = (int) tileDef.get("tilesetWidth");
						newTile.width = (int) tileDef.get("tilewidth");
						newTile.height = (int) tileDef.get("tileheight");
						newTile.setup();
						
						tileSet.add(newTile);
					}
				}
			}
		}
	}

	private void loadTileset() {
		// Loop through tiles and create the bounding objects
		for (Tile t : tileSet) {
			if (t.isFloor) {
				BoundingSprite floorSprite;
				
				if (!t.tilesetMember) floorSprite = new BoundingSprite(true, t.imagePath);
				else floorSprite = new BoundingSprite(true, t.imagePath, t.tileXOffset, t.tileYOffset, t.width, t.height, t.tilesetWidth, t.tilesetHeight);
				
				BoundingObject floor = new BoundingObject(gameScreen, t.xPos, t.yPos, floorSprite);
		        Physics.addFloorEntity(floor);
			} else if (t.isWall) {
				BoundingSprite floorSprite;
				
				if (!t.tilesetMember) floorSprite = new BoundingSprite(false, t.imagePath);
				else floorSprite = new BoundingSprite(true, t.imagePath, t.tileXOffset, t.tileYOffset, t.width, t.height, t.tilesetWidth, t.tilesetHeight);
				
				BoundingObject floor = new BoundingObject(gameScreen, t.xPos, t.yPos, floorSprite);
		        Physics.addFloorEntity(floor);
			} else {
				// Add non floor-sprites, just adding as floors for now
				BoundingSprite floorSprite;
				
				if (!t.tilesetMember) floorSprite = new BoundingSprite(true, t.imagePath);
				else floorSprite = new BoundingSprite(true, t.imagePath, t.tileXOffset, t.tileYOffset, t.width, t.height, t.tilesetWidth, t.tilesetHeight);
				
				BoundingObject floor = new BoundingObject(gameScreen, t.xPos, t.yPos, floorSprite);
		        Physics.addFloorEntity(floor);
			}
		}
	}
	
	private HashMap<Integer, String> ParseTileProperties(JSONObject properties) {
		HashMap<Integer, String> parsedProperties = new HashMap<Integer, String>();
		
		Iterator it = properties.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			Integer gid = Integer.valueOf((String) pairs.getKey());
			
			JSONObject propObj = (JSONObject) pairs.getValue();
			Set<String> propertiesSet = propObj.keySet();
			
			for (String p : propertiesSet ) {
				parsedProperties.put(gid, p);
			}
		}
			
		return parsedProperties;
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
		return 800;
	}

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

}