package nl.lipsum.ui;

import com.badlogic.gdx.graphics.Texture;

import java.util.function.Function;

public class UiSelectedItem extends UiItem{
    Texture textureSelected;

    public UiSelectedItem(Texture texture, Texture textureSelected, float sizeX, float sizeY, Function function){
        super(texture, sizeX, sizeY, function);
        this.textureSelected = textureSelected;
    }

    public Texture getTextureSelected() {
        return textureSelected;
    }
}
