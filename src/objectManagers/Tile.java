package objectManagers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tile {
	public int xPos = 0;
	public int yPos = 0;
	public int width = 0;
	public int height = 0;
	public int gid = -1;
	public int tileDefGid = -1;
	public int tilesetHeight = 0;
	public int tilesetWidth = 0;
	public int tileXOffset = 0;
	public int tileYOffset = 0;
	public String imagePath = "";
	public HashMap<Integer, String> properties;
	public Boolean tilesetMember = false;
	public Boolean isFloor = false;
	public Boolean isWall = false;

	private void setTilesetOffsets() {
		if(this.tilesetHeight > this.height || this.tilesetWidth > this.width) {
			int size = tilesetWidth / width;
			
			this.tileXOffset = (int)Math.floor(this.gid % size);
			this.tileYOffset = (int)Math.floor(this.gid / size);
		    
			this.tilesetMember = true;
		}
	}
	
	private void setProperties() {
		Iterator it = properties.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			Integer propGid = (Integer) pairs.getKey();
			if (propGid == this.gid) {
				switch ((String) pairs.getValue()) {
					case "floor":
						this.isFloor = true;
						break;
					case "wall":
						this.isWall = true;
						break;
					default:
						break;
				}
			}
		}
	}
	
	public void setup() {
		this.setTilesetOffsets();
		this.setProperties();
	}
}
