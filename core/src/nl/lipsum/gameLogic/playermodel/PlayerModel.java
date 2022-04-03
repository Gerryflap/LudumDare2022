package nl.lipsum.gameLogic.playermodel;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.gameLogic.Army;
import nl.lipsum.gameLogic.Base;

import java.util.ArrayList;
import java.util.List;

// abstract omdat we ook ai player models hebben straks
public abstract class PlayerModel {
    private static int amountPlayers = 0;
    private float amountResources;
    public List<Army> armies;
    private Base base;
    int selectedArmy = 0;
    private final int id;

    public PlayerModel() {
        this.id = amountPlayers;
        amountPlayers++;
        this.amountResources = 50;
    }

    public void initiateArmies(Base base){
        armies = new ArrayList<>();
        armies.add(new Army(base, this));
        armies.add(new Army(base, this));
        armies.add(new Army(base, this));
        this.base = base;
    }

    public float getAmountResources() {
        return amountResources;
    }

    public void addResources(float amount){
        this.amountResources += amount;
    }

    public void setSelectedArmy(int selectedArmy){
        this.selectedArmy = selectedArmy;
    }

    public int getSelectedArmy() {
        return selectedArmy;
    }

    public Base getBase() {
        return base;
    }

    public int getId() {
        return id;
    }
}
