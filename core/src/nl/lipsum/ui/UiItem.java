package nl.lipsum.ui;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.gameLogic.GameController;

import java.util.function.Function;

public class UiItem {
    private Texture texture;
    private float sizeX;
    private float sizeY;
    private int requiredResources;
    private Function<UiItem, Object> function;

    public UiItem(Texture texture, float sizeX, float sizeY) {
        this(texture, sizeX, sizeY, new Function<UiItem, Object>() {
            @Override
            public Object apply(UiItem uiItem) {
                return null;
            }
        });
    }

    public UiItem(Texture texture, float sizeX, float sizeY, Function function) {
        this.texture = texture;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.requiredResources = 0;
        this.function = function;
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

    public Function<UiItem, Object> getFunction() {
        return function;
    }

    public void setFunction(Function<UiItem, Object> function) {
        this.function = function;
    }
}
