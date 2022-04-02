package nl.lipsum.ui;

import com.badlogic.gdx.graphics.Texture;

public class UiItem {
    private Texture texture;
    private float sizeX;
    private float sizeY;
    private int requiredResources;

    public UiItem(Texture texture, float sizeX, float sizeY) {
        this.texture = texture;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.requiredResources = 0;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public int getRequiredResources() {
        return requiredResources;
    }

    public void setRequiredResources(int requiredResources) {
        this.requiredResources = requiredResources;
    }
}
