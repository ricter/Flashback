package physics;

import enumerations.CollisionType;
import gameObjects.BoundingObject;
import gameObjects.Bullet;
import gameObjects.GameObject;
import gameObjects.Player;

import java.util.ArrayList;

public class Physics {

    public static final double GRAVITY_ACCELERATION = 70;
    
	private static ArrayList<Player> playerEntities = new ArrayList<Player>();
	private static ArrayList<GameObject> gameEntities = new ArrayList<GameObject>();
	private static ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	private static ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	private static ArrayList<BoundingObject> walls = new ArrayList<BoundingObject>();
	
	public Physics() {

	}

	public static void addEnemyBullet(Bullet gameObj) {
		getEnemyBullets().add(gameObj);
	}

	public static void addGameEntity(GameObject gameObj) {
		getGameEntities().add(gameObj);
	}

	public static void addPlayerBullet(Bullet gameObj) {
		getPlayerBullets().add(gameObj);
	}

	public static void addPlayerEntity(Player gameObj) {
		getPlayerEntities().add(gameObj);
	}
	
    public static void addWallEntity(BoundingObject gameObj) {
        getWalls().add(gameObj);
    }	

	public static CollisionType checkCollision(GameObject gameObject1, GameObject gameObject2, boolean flipImage1, boolean flipImage2){
	    
	    float left1, left2;
        float right1, right2;
        float top1, top2;
        float bottom1, bottom2;

        left1 = gameObject1.getxPosition() + gameObject1.getSprite().getCollisionXOffset();
        left2 = gameObject2.getxPosition() + gameObject2.getSprite().getCollisionXOffset();
        right1 = gameObject1.getxPosition() + gameObject1.getSprite().getCollisionXOffset() + gameObject1.getSprite().getCollisionWidth();
        right2 = gameObject2.getxPosition() + gameObject2.getSprite().getCollisionXOffset() + gameObject2.getSprite().getCollisionWidth();
        top1 = gameObject1.getyPosition() + gameObject1.getSprite().getCollisionYOffset();
        top2 = gameObject2.getyPosition() + gameObject2.getSprite().getCollisionYOffset();
        bottom1 = gameObject1.getyPosition() + gameObject1.getSprite().getCollisionYOffset() + gameObject1.getSprite().getCollisionHeight();
        bottom2 = gameObject2.getyPosition() + gameObject2.getSprite().getCollisionYOffset() + gameObject2.getSprite().getCollisionHeight();

        if (gameObject2.getSprite().getCollisionWidth()==20 && left1 < 220){
            System.out.println(left1);
        }
        
        /*if (flipImage1) {
            left1 += gameObject1.getSprite().getCollisionWidth();
            right1 += gameObject1.getSprite().getCollisionWidth();
        }
        
        if (flipImage2) {
            left2 += gameObject2.getSprite().getCollisionWidth();
            right2 += gameObject2.getSprite().getCollisionWidth();
        }*/
	    
        boolean skipXCheck = gameObject1.skipXCollision() || gameObject2.skipXCollision();
        boolean skipYCheck = gameObject1.skipYCollision() || gameObject2.skipYCollision();
	    return checkOverlap(left1, bottom1, right1, top1, left2, bottom2, right2, top2, skipXCheck, skipYCheck);
	    
	}
	
	/** Checks if two rectangles overlap */
    private static CollisionType checkOverlap(float leftA, float botA, float rightA, float topA,
                                        float leftB, float botB, float rightB, float topB,
                                        boolean skipX, boolean skipY) {
	    
        CollisionType collision = CollisionType.NONE;
	   
	    if(botA <= topB || botB <= topA || rightA <= leftB || rightB <= leftA){
	        
	    } else {
	        
	        boolean xCollision = (rightA > leftB || rightB > leftA) && !skipX;
	        boolean yCollision = (botA > topB || botB > topA) && !skipY;
	        
	        if (xCollision && yCollision){
	            collision = CollisionType.XANDY;
	        } else if (xCollision) {
	            collision = CollisionType.X;
	        } else if (yCollision) {
	            collision = CollisionType.Y;
	        }
	        
	    }
	    
	    return collision;
	    
	}
	
	public static ArrayList<Bullet> getEnemyBullets() {
		return enemyBullets;
	}

	public static ArrayList<GameObject> getGameEntities() {
        return gameEntities;
    }

	public static ArrayList<Bullet> getPlayerBullets() {
		return playerBullets;
	}

	public static ArrayList<Player> getPlayerEntities() {
		return playerEntities;
	}
	
	public static ArrayList<BoundingObject> getWalls() {
        return walls;
    }

    public static void updateGameObjects(float deltaT) {

		for (int index = 0; index < getPlayerEntities().size(); index++) {
			getPlayerEntities().get(index).update(deltaT);
		}

		for (int index = 0; index < getPlayerBullets().size(); index++) {

			getPlayerBullets().get(index).update(deltaT);
			if (getPlayerBullets().get(index).shouldRemove()) {
				getPlayerBullets().remove(index);
			}

		}

		for (int index = 0; index < getEnemyBullets().size(); index++) {

			getEnemyBullets().get(index).update(deltaT);
			if (getEnemyBullets().get(index).shouldRemove()) {
				getEnemyBullets().remove(index);
			}

		}

		for (int index = 0; index < getGameEntities().size(); index++) {

			getGameEntities().get(index).update(deltaT);
			if (getGameEntities().get(index).shouldRemove()) {
				getGameEntities().remove(index);
			}

		}

		/*// check for bullet collisions
		for (GameObject m : gameEntities) {

			for (Bullet b : getPlayerBullets()) {

			    if (checkCollision(m, b, m.isFlipImage(), b.isFlipImage())) {

				    MonsterType monsterType = MonsterType.getMonsterTypeFromClassName(m.getClass());
				    playerEntities.get(0).getScorecard().addMonsterKills(monsterType, 1);
					m.animateDeath();
					b.animateDeath();

				}
			}
		}

		for (Bullet b : getEnemyBullets()) {

		    if (checkCollision(b, getPlayerEntities().get(0), b.isFlipImage(), getPlayerEntities().get(0).isFlipImage())) {

				b.animateDeath();
				getPlayerEntities().get(0).OnCollision();

			}
		}*/
	}

}