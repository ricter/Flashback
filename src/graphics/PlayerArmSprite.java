package graphics;

import java.util.ArrayList;

import main.Flashback;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import utils.Utils;

public class PlayerArmSprite extends Sprite {

    static PImage staticImage;
    private static ArrayList<PImage> runImages;

    private static PImage idleImage;
    private static PImage firingImage;

    private float xOffset = 0;
    private float yOffset = 0;

    private boolean isFiring = false;

    private float targetAngle = 0;
    private float easing = 0.05f;

    public PlayerArmSprite() {

        super(idleImage);

    }

    public PlayerArmSprite(int width, int height, int xOffset, int yOffset) {

        super(idleImage, width, height, xOffset, yOffset);

    }
    
    public static void loadImages() {

        idleImage = gameScreen.loadImage("../images/commandoPlayerGun.png");
        firingImage = gameScreen
                .loadImage("../images/commandoPlayerGunFire.png");

    }

    @Override
    public void draw(float x, float y) {

        gameScreen.image(currentImage, x, y);

    }

    @Override
    public void draw(float x, float y, boolean flip) {

        if (isFiring) {

            this.setCurrentImage(firingImage);

        } else {

            this.setCurrentImage(idleImage);

        }

        float angle = PApplet.atan2(gameScreen.mouseY - (y + yOffset + 13),
                gameScreen.mouseX - (x + xOffset + 24));

        /**
         * This section uses a lot of "magic numbers" here in order to get the
         * arm rotation to look proper. Unfortunately there is no math behind
         * the lengths chosen, it is just about what looks right and adjusting
         * the values from there.
         */

        if (flip) {

            float rotationXOffset = x + 130;
            float rotationYOffset = y + yOffset + 13;
            gameScreen.pushMatrix();
            gameScreen.translate(rotationXOffset, rotationYOffset);
            gameScreen.scale(-1, 1);
            gameScreen.rotate(-angle + PApplet.PI);
            gameScreen.image(currentImage, -24, -13);
            updateBulletSpawnPosition(angle, rotationXOffset, rotationYOffset, true);
            gameScreen.popMatrix();

        } else {

            float rotationXOffset = x + xOffset + 28;
            float rotationYOffset = y + yOffset + 14;
            gameScreen.pushMatrix();
            gameScreen.translate(rotationXOffset, rotationYOffset);
            gameScreen.rotate(angle);
            gameScreen.image(currentImage, -24, -13);
            updateBulletSpawnPosition(angle, rotationXOffset, rotationYOffset, false);
            gameScreen.popMatrix();

        }

        setFiring(false);

    }

    @Override
    public ArrayList<PImage> getMovingImages() {
        return runImages;
    }

    @Override
    public PImage getStaticImage() {
        return staticImage;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public boolean isFiring() {
        return isFiring;
    }

    public void setFiring(boolean isFiring) {
        this.isFiring = isFiring;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    private void updateBulletSpawnPosition(float angle,
                                           float rotationXOffset,
                                           float rotationYOffset,
                                           boolean flip) {
        
        PVector bulletSpawnLocation;
        if (flip){
            bulletSpawnLocation = new PVector(125, -28);
        } else {
            bulletSpawnLocation = new PVector(125, 28);
        }
        Utils.rotatePVector2D(bulletSpawnLocation, angle);
        bulletSpawnLocation.set(bulletSpawnLocation.x + rotationXOffset, bulletSpawnLocation.y + rotationYOffset, 0);
        Flashback.getPlayer().setBulletSpawnPosition(bulletSpawnLocation);
        
    }

}