package nl.lipsum.gameLogic.playermodel;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.gameLogic.Army;
import nl.lipsum.gameLogic.Base;

import java.util.ArrayList;
import java.util.List;

// abstract omdat we ook ai player models hebben straks
public abstract class PlayerModel {
    private int amountResources;
    public List<Army> armies;
    private Base base;

    public PlayerModel() {
        this.amountResources = 50;
    }

    public void initiateArmies(Base base){
        armies = new ArrayList<>();
        armies.add(new Army(base));
        armies.add(new Army(base));
        armies.add(new Army(base));
        this.base = base;
    }

    public int getAmountResources() {
        return amountResources;
    }

    public void addResources(int amount){
        this.amountResources += amount;
    }

    public void setTextures(Texture texture){
        this.base.setTexture(texture);
        for(Army a:armies){
            a.setTextures(texture);
        }
    }
}
