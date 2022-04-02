package nl.lipsum.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static nl.lipsum.Config.TILE_SIZE;

public class Bullet {

    private float startPosX;
    private float startPosY;
    private float endPosX;
    private float endPosY;
    private float bulletSpeed;

    private float currentPosX;
    private float currentPosY;

    private Texture bulletTexture;

    private float angle;

    public Bullet(float startPosX, float startPosY, float endPosX, float endPosY, float bulletSpeed) {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.endPosX = endPosX;
        this.endPosY = endPosY;
        this.bulletSpeed = bulletSpeed;

        this.currentPosX = startPosX;
        this.currentPosY = startPosY;

        this.bulletTexture = new Texture("textures/bullet.png");

        this.angle = (float) ((float) Math.atan2(endPosY - currentPosY, endPosX - currentPosX) * 180.0/Math.PI) + 90f;
    }

    public boolean step() {
        float diffX = endPosX - currentPosX;
        float diffY = endPosY - currentPosY;
        double factor = Gdx.graphics.getDeltaTime()*bulletSpeed/(Math.sqrt(diffX*diffX + diffY*diffY));
        float updateX = (float) (diffX*factor);
        float updateY = (float) (diffY*factor);

        this.currentPosX += updateX;
        this.currentPosY += updateY;

        return Math.abs(currentPosX - endPosX) < 1 || Math.abs(currentPosY - endPosY) < 1;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(bulletTexture, this.currentPosX, this.currentPosY, bulletTexture.getWidth() / 2.0f, bulletTexture.getHeight() / 2.0f, bulletTexture.getWidth(), bulletTexture.getHeight(),
                1.0f, 1.0f, angle, 0, 0, bulletTexture.getWidth(), bulletTexture.getHeight(),false, false);
    }

    public float getStartPosX() {
        return startPosX;
    }

    public void setStartPosX(float startPosX) {
        this.startPosX = startPosX;
    }

    public float getStartPosY() {
        return startPosY;
    }

    public void setStartPosY(float startPosY) {
        this.startPosY = startPosY;
    }

    public float getEndPosX() {
        return endPosX;
    }

    public void setEndPosX(float endPosX) {
        this.endPosX = endPosX;
    }

    public float getEndPosY() {
        return endPosY;
    }

    public void setEndPosY(float endPosY) {
        this.endPosY = endPosY;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public float getCurrentPosX() {
        return currentPosX;
    }

    public void setCurrentPosX(float currentPosX) {
        this.currentPosX = currentPosX;
    }

    public float getCurrentPosY() {
        return currentPosY;
    }

    public void setCurrentPosY(float currentPosY) {
        this.currentPosY = currentPosY;
    }
}
