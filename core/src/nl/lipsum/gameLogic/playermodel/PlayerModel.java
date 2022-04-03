package nl.lipsum.gameLogic.playermodel;

import nl.lipsum.gameLogic.Army;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.GameController;

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

    private int health;

    private int coolingPower;

    private final static int TEMPERATURE_MAX_CAN_SURVIVE_WITHOUT_DAMAGE = 0;

    public PlayerStatus playerStatus;

    public PlayerModel() {
        this.id = amountPlayers;
        amountPlayers++;
        this.amountResources = 50;
        this.health = 1000;
        this.coolingPower = 0;
        this.playerStatus = PlayerStatus.ALIVE;
    }

    public void initiateArmies(Base base){
        armies = new ArrayList<>();
        armies.add(new Army(base, this));
        armies.add(new Army(base, this));
        armies.add(new Army(base, this));
        this.base = base;
    }

    public void step() {
        float tempDiff = GameController.globalTemperature + coolingPower - TEMPERATURE_MAX_CAN_SURVIVE_WITHOUT_DAMAGE;
        if (tempDiff > 0) {
            return ;
        }

        health += tempDiff;

        if (health <= 0) {
            this.playerStatus = PlayerStatus.DEAD;
        }
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
