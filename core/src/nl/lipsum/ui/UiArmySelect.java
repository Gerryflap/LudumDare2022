package nl.lipsum.ui;

import com.badlogic.gdx.graphics.Texture;

import java.util.function.Function;

public class UiArmySelect extends UiItem{
    Texture textureSelected;

    public UiArmySelect(Texture texture, Texture textureSelected, float sizeX, float sizeY, Function function){
        super(texture, sizeX, sizeY, function);
        this.textureSelected = textureSelected;
    }

    public Texture getTextureSelected() {
        return textureSelected;
    }
}
